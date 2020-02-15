package com.techelevator;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.menu.Menu;
import com.techelevator.reader.FileStockReader;
import com.techelevator.reader.StockReader;
import com.techelevator.register.ChangeMaker;
import com.techelevator.register.Register;
import com.techelevator.writer.LogWriter;

public class CateringSystem {
	
	private static final String ADD_MONEY = "ADD MONEY: ";
	private static final String GIVE_CHANGE = "GIVE CHANGE";

	Register register = new Register();
	ChangeMaker change = new ChangeMaker();
	private Cart shoppingCart = new Cart();
	private LogWriter logWriter = new LogWriter();
	Menu menu;

	private Map<String, ProductSlot> stock = new LinkedHashMap<String, ProductSlot>();

	public CateringSystem(String filename, Menu menu) throws FileNotFoundException {
		StockReader reader = new FileStockReader(filename);
		stock = reader.load();
		this.menu = menu;
	}

	public Map<String, ProductSlot> getStock() {
		return stock;
	}

	public void completeTransaction() {
		menu.displayScreenReport(shoppingCart.getShoppingCart());
		change.makeChange(register);
		logWriter.log(GIVE_CHANGE, register.getAccountBalance(), new BigDecimal(0.00), "", "");
		register.resetAccountBalance();
		menu.displayChange(change.makeChange(register));
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

	public BigDecimal getAccountBalance() {
		return register.getAccountBalance();
	}

	public void addFundsToAccount() {
		BigDecimal money = new BigDecimal(0);
		while (true) {
			BigDecimal amountInput = menu.getAmountFromUser();
			if (amountInput.compareTo(new BigDecimal(0)) == 1) {
				register.deposit(amountInput);
				money = amountInput;
				logWriter.log(ADD_MONEY, money, register.getAccountBalance(), "", "");
				break;
			} else {
				menu.displayMessage("!--- Negative balance was inputted. Please deposit a positive amount ---!"
						+ System.getProperty("line.separator"));
			}
		}
	}

	public void selectProductMenu(String productCode) {
		Map<String, ProductSlot> selectProductCart = stock;
		if (!stock.containsKey(productCode.toUpperCase())) {
			menu.displayMessage("!--- Sorry, this product does not exist ---!" + System.getProperty("line.separator"));
		} else {
			checkQuantityAndBalance(selectProductCart, productCode);
		}
		menu.displayMessage("Current Cart: " + shoppingCart.showCurrentCart() + System.getProperty("line.separator"));
	}

}
