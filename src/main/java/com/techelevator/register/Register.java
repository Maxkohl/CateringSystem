package com.techelevator.register;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.techelevator.Cart;
import com.techelevator.CateringSystem;
import com.techelevator.inventorylist.ProductSlot;

public class Register {

	Cart cart = new Cart();
	DecimalFormat df = new DecimalFormat("#.##");

	BigDecimal accountBalance = new BigDecimal(0);
	double individualItemPrice = 0;
	BigDecimal totalPrice = new BigDecimal(0);
	String itemToAdd;

	Map<String, ProductSlot> registerCart = new LinkedHashMap<String, ProductSlot>();

	public void deposit(BigDecimal depositAmount) {
		if (accountBalance.add(depositAmount).compareTo(new BigDecimal(5000)) <= 0) {
			accountBalance = accountBalance.add(depositAmount);
		} else {
			System.out.print("You cannot exceed the max account balance of $5,000");
		}

	}

	public void withdrawal(BigDecimal withdrawalAmount) {
		accountBalance = accountBalance.subtract(withdrawalAmount);
	}

	public BigDecimal getAccountBalance() {
		return accountBalance.setScale(2, RoundingMode.CEILING);
	}

	public void addToRegister(String productName, ProductSlot productSlot) {
		registerCart.put(productName, productSlot);
	}
	
	//getTotalPrice move to shopping cart?
	public BigDecimal getTotalPrice() {
		BigDecimal totalPrice = new BigDecimal(0);
		for (Entry<String, ProductSlot> item : registerCart.entrySet()) {
			int i = 0;
			while (i < item.getValue().getQuantityToMove()) {
				totalPrice = totalPrice.add(new BigDecimal(item.getValue().getItemPrice()));
				i++;
			}
		}
		return totalPrice;
	}

	public void resetAccountBalance() {
		accountBalance = new BigDecimal(0);
	}

}
