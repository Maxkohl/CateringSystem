package com.techelevator.inventorylist;

public class ProductSlot {

	private int count;
	private Item item;
	private double price;
	private int quantityToMove;

	public ProductSlot(Item item) {
		this.item = item;
		this.count = 50;
		this.price = item.getPrice();
	}

	public int getCount() {
		return count;

	}

	public void setCount(int quantityToTake) {
		count -= quantityToTake;
	}

	public Item getItem() {
		return item;

	}

	public String getItemName() {
		return item.getName();
	}

	public double getItemPrice() {
		return item.getPrice();
	}

	public void setQuantityToMove(int quantityToAdd) {
		quantityToMove = quantityToAdd;
	}

	public int getQuantityToMove() {
		return quantityToMove;
	}

}
