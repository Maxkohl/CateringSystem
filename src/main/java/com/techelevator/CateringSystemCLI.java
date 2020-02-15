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

	private Menu menu;
	private CateringSystem cateringSystem;
	private LogWriter logWriter = new LogWriter();


	public CateringSystemCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		String stockFile = menu.getInventoryFileFromAdmin();

		try {
			cateringSystem = new CateringSystem(stockFile, menu);

		} catch (FileNotFoundException e) {
			System.out.println("!----- " + stockFile + " not found. Please restart program. -----!");
			System.exit(0);

		}

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_ALL_OPTIONS, cateringSystem.getAccountBalance());

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
			String choice = (String) menu.getChoiceFromOptions(ORDER_MENU_ALL_OPTIONS, cateringSystem.getAccountBalance());

			if (choice.equals(ORDER_MENU_ADD_MONEY)) {
				cateringSystem.addFundsToAccount();
			} else if (choice.equals(ORDER_MENU_SELECT_PRODUCT)) {
				cateringSystem.selectProductMenu(menu.getProductCodeFromUser());
			} else if (choice.equals(ORDER_MENU_COMPLETE_TRANSACTION)) {
				cateringSystem.completeTransaction();
				break;
			}
		}
	}


	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		CateringSystemCLI cli = new CateringSystemCLI(menu);
		cli.run();
	}

	// Feedback
	// More communication between classes (cart and register ex) Avoid void methods
	// Change maker use a map instead of counter?
}
