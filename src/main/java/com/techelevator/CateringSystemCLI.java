package com.techelevator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.menu.Menu;
import com.techelevator.register.ChangeMaker;
import com.techelevator.register.Register;
import com.techelevator.writer.LogWriter;

public class CateringSystemCLI {

	private static final String MAIN_MENU_OPTIONS_DISPLAY_ITEMS = "Display Catering Items";
	private static final String MAIN_MENU_OPTIONS_DISPLAY_ORDER = "Order";
	private static final String MAIN_MENU_OPTIONS_QUIT = "Quit";
	private static final String[] MAIN_MENU_ALL_OPTIONS = { MAIN_MENU_OPTIONS_DISPLAY_ITEMS,
			MAIN_MENU_OPTIONS_DISPLAY_ORDER, MAIN_MENU_OPTIONS_QUIT };

	private static final String ORDER_MENU_ADD_MONEY = "Add Money";
	private static final String ORDER_MENU_SELECT_PRODUCT = "Select Product";
	private static final String ORDER_MENU_COMPLETE_TRANSACTION = "Complete Transaction";
	private static final String[] ORDER_MENU_ALL_OPTIONS = { ORDER_MENU_ADD_MONEY, ORDER_MENU_SELECT_PRODUCT,
			ORDER_MENU_COMPLETE_TRANSACTION };

	private static final String ADD_MONEY = "ADD MONEY: ";
	private static final String GIVE_CHANGE = "GIVE CHANGE";

	private Menu menu;
	private CateringSystem cateringSystem;
	private Cart shoppingCart = new Cart();
	private ChangeMaker change = new ChangeMaker();
	private LogWriter logWriter = new LogWriter();
	Register register = new Register();

	public CateringSystemCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		String stockFile = menu.getInventoryFileFromAdmin();

		try {
			cateringSystem = new CateringSystem(stockFile);

		} catch (FileNotFoundException e) {
			System.out.println("!----- " + stockFile + " not found. Please restart program. -----!");
			System.exit(0);

		}

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_ALL_OPTIONS, register.getAccountBalance());

			if (choice.equals(MAIN_MENU_OPTIONS_DISPLAY_ITEMS)) {
				menu.displayStock(cateringSystem.getStock());
			} else if (choice.equals(MAIN_MENU_OPTIONS_DISPLAY_ORDER)) {
				showOrderMenu();
			} else if (choice.equals(MAIN_MENU_OPTIONS_QUIT)) {
				menu.displayMessage("Thank you for your business. Please come back again!");
				System.exit(0);
			}
		}
	}

	private void showOrderMenu() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(ORDER_MENU_ALL_OPTIONS, register.getAccountBalance());

			if (choice.equals(ORDER_MENU_ADD_MONEY)) {
				addFundsToAccount();
			} else if (choice.equals(ORDER_MENU_SELECT_PRODUCT)) {
				selectProductMenu(menu.getProductCodeFromUser());
			} else if (choice.equals(ORDER_MENU_COMPLETE_TRANSACTION)) {
				menu.displayScreenReport(shoppingCart.getShoppingCart());
				change.makeChange(register);
				logWriter.log(GIVE_CHANGE, register.getAccountBalance(), new BigDecimal(0.00), "", "");

				register.resetAccountBalance();
				menu.displayChange(change.getTwentiesCount(), change.getTensCount(), change.getFivesCount(),
						change.getOnesCount(), change.getQuartersCount(), change.getDimesCount(),
						change.getNickelsCount());
				break;
			}
		}
	}

	private void addFundsToAccount() {
		BigDecimal money = new BigDecimal(0);
		while (true) {
			BigDecimal amountInput = menu.getAmountFromUser();
			register.deposit(amountInput);
			money = amountInput;
			break;
		}
		logWriter.log(ADD_MONEY, money, register.getAccountBalance(), "", "");

	}

	private void selectProductMenu(String productCode) {
		Map<String, ProductSlot> selectProductCart = cateringSystem.getStock();
		if (!cateringSystem.getStock().containsKey(productCode.toUpperCase())) {
			menu.displayMessage("!--- Sorry, this product does not exist ---!" + System.getProperty("line.separator"));
		} else {
			checkQuantityAndBalance(selectProductCart, productCode);
		}
		menu.displayMessage("Current Cart: " + shoppingCart.showCurrentCart() + System.getProperty("line.separator"));
	}

	public void checkQuantityAndBalance(Map<String, ProductSlot> selectProductCart, String productCode) {
		for (Entry<String, ProductSlot> product : selectProductCart.entrySet()) {
			if (productCode.equalsIgnoreCase(product.getKey())) {
				String productToAdd = product.getValue().getItemName();
				ProductSlot itemSlotOptions = product.getValue();
				menu.displayMessage("Insert quantity of " + productToAdd + " to add to cart >>> ");
				int quantityToAdd = menu.getQuantityToAddFromUser();
				if (product.getValue().getCount() == 0) {
					menu.displayMessage(
							"!--- Sorry, this product is sold out ---!" + System.getProperty("line.separator"));
					break;
				} else if (product.getValue().getCount() - quantityToAdd < 0) {
					menu.displayMessage("!--- Sorry, there is insufficient stock to complete your request ---!"
							+ System.getProperty("line.separator"));
					break;
				}

				product.getValue().setQuantityToMove(quantityToAdd);

				BigDecimal totalPricePerItemQuantity = new BigDecimal(
						quantityToAdd * (product.getValue().getItemPrice()));

				if (totalPricePerItemQuantity.compareTo(register.getAccountBalance()) == 1) {
					menu.displayMessage(
							"!----- Your total cost exceeds your account balance. Please add more funds and try again. -----!"
									+ System.getProperty("line.separator"));
				} else {
					addItemToCart(product, totalPricePerItemQuantity, productToAdd, quantityToAdd, itemSlotOptions,
							productCode);
				}
			}
		}

	}

	public void addItemToCart(Entry<String, ProductSlot> product, BigDecimal totalPricePerItemQuantity,
			String productToAdd, int quantityToAdd, ProductSlot itemSlotOptions, String productCode) {
		register.withdrawal(totalPricePerItemQuantity);
		menu.displayMessage("Succesfully added " + product.getValue().getQuantityToMove() + " " + productToAdd
				+ " to cart." + System.getProperty("line.separator"));
		register.addToRegister(productToAdd, itemSlotOptions);
		shoppingCart.addItem(productToAdd, itemSlotOptions);
		logWriter.log(productToAdd, totalPricePerItemQuantity, register.getAccountBalance(),
				String.valueOf(quantityToAdd).toUpperCase(), productCode);
		product.getValue().setCount(quantityToAdd);

	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		CateringSystemCLI cli = new CateringSystemCLI(menu);
		cli.run();
	}
	
	//Feedback
	//More communication between classes (cart and register ex) Avoid void methods
	//Change maker use a map instead of counter? 
	//Better encapsulation, a lot in CLI. Hide a lot in catering system
	//Negative deposit issue
}
