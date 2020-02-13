package com.techelevator.inventorylist;

public class Item {

	private String name;
	private double price;
	private String itemType;

	public Item(String name, double price, String itemType) {
		this.name = name;
		this.itemType = itemType;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getItemType() {
		return itemType;
	}

}
