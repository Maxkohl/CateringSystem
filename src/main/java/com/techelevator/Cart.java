package com.techelevator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.register.Register;

public class Cart {

	Map<String, ProductSlot> shoppingCart = new LinkedHashMap<String, ProductSlot>();

	Integer quantityInCart = 0;
	String itemNameToAdd;
	double itemPrice;

	public void addItem(String productName, ProductSlot productSlot) {
		quantityInCart = new Integer(productSlot.getQuantityToMove());
		itemPrice = productSlot.getItemPrice();
		itemNameToAdd = productName;
		shoppingCart.put(itemNameToAdd, productSlot);
	}

	public Map getShoppingCart() {
		return shoppingCart;
	}

	public Map showCurrentCart() {
		Map<String, Integer> cart = new LinkedHashMap<String, Integer>();
		for (Entry<String, ProductSlot> cartItem : shoppingCart.entrySet()) {
			cart.put(cartItem.getKey(), (int) cartItem.getValue().getQuantityToMove());
		}
		return cart;
	}

	public Integer getItemQuantity() {
		return quantityInCart;
	}

}
