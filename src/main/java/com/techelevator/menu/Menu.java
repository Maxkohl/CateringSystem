package com.techelevator.menu;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.register.Register;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private DecimalFormat df = new DecimalFormat("#.##");

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public String getInventoryFileFromAdmin() {
		System.out.print("Admin: Please enter product inventory file path >>> ");
		out.flush();
		return in.nextLine();
	}

	public Object getChoiceFromOptions(Object[] options, BigDecimal balance) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options, balance);
			choice = getChoiceFromUser(options);
		}
		return choice;
	}

	public BigDecimal getAmountFromUser() {
		System.out.println("Please enter the amount to deposit >>> ");
		BigDecimal amountInput = new BigDecimal(0);
		while (amountInput.compareTo(new BigDecimal(0)) == 0 && in.hasNextInt()) {
			amountInput = amountInput.add(new BigDecimal(in.nextInt()));
			in.nextLine();
		}
		return amountInput;
	}

	public void displayMenuOptions(Object[] options, BigDecimal balance) {
		out.println("\n");
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println("(" + optionNum + ") " + options[i]);
		}
		out.println("Account Balance: $" + balance);
		out.println("\n Insert option choice here >>> ");
		out.flush();
	}

	public Object getChoiceFromUser(Object[] options) {
		Object choice = null;
		String userChoice = in.nextLine();
		try {
			int userSelection = Integer.valueOf(userChoice);
			if (userSelection > 0 && userSelection <= options.length) {
				choice = options[userSelection - 1];
			}
		} catch (NumberFormatException e) {
			System.out.println("number format exception occurred");
		}
		if (choice == null) {
			out.println(System.getProperty("line.separator") + "!------ " + userChoice + " is not a valid option. Please try again. ------!");
		}
		return choice;
	}

	public String getProductCodeFromUser() {
		System.out.print("Please insert product code to add product to cart >>> ");
		return in.nextLine().toUpperCase();

	}

	public int getQuantityToAddFromUser() {
		out.println("Please insert quantity of product to purchase >>> ");
		int quantity = in.nextInt();
		in.nextLine();
		return quantity;
	}

	public void displayStock(Map<String, ProductSlot> stock) {
		System.out.printf("%-5s %-25s $%5s %-13s %n", "Item #", "Product Type", "Price", "Quantity");
		System.out.println("-------------------------------------------------");

		for (Entry<String, ProductSlot> entry : stock.entrySet()) {
			String quantity = entry.getValue().getCount() > 0 ? String.valueOf(entry.getValue().getCount())
					: "Sold Out";
			System.out.printf("%-7s %-25s $%5.2f %-13s %n", entry.getKey(), entry.getValue().getItem().getName(),
					entry.getValue().getItem().getPrice(), quantity);
		}
	}

	public void displayMessage(String message) {
		out.print(message);
		out.flush();
	}

	public void displayScreenReport(Map<String, ProductSlot> shoppingCart) {
		System.out.printf("%-10s %-10s %-15s $%-10s %s %n", "Quantity", "Type", "Item Name", "Price",
				"Total $ Per Item");
		System.out.println("-------------------------------------------------------------------");
		BigDecimal totalCost = new BigDecimal(0);
		for (Entry<String, ProductSlot> entry : shoppingCart.entrySet()) {
			float itemTotal = (float) (entry.getValue().getQuantityToMove() * entry.getValue().getItemPrice());
			System.out.printf("%-10s %-10s %-15s $%-10.2f $%.2f %n", entry.getValue().getQuantityToMove(),
					entry.getValue().getItem().getItemType(), entry.getValue().getItem().getName(),
					entry.getValue().getItem().getPrice(), itemTotal);
			totalCost = totalCost
					.add(new BigDecimal(entry.getValue().getQuantityToMove() * entry.getValue().getItemPrice()));
		}
		System.out.println("Total Cost: $" + totalCost.setScale(2, RoundingMode.CEILING));
	}

	public void displayChange(int twentiesCount, int tensCount, int fivesCount, int onesCount, int quartersCount,
			int dimesCount, int nickelsCount) {
		String newLine = System.getProperty("line.separator");
		System.out.println("Your change: ");
		System.out.println("$20: " + twentiesCount + newLine + "$10: " + tensCount + newLine + "$5: " + fivesCount
				+ newLine + "$1: " + onesCount + newLine + "$.25: " + quartersCount + newLine + "$.10: " + dimesCount
				+ newLine + "$.05: " + nickelsCount);

	}

}
