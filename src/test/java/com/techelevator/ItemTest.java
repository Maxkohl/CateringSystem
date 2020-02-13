package com.techelevator;

import org.junit.*;

import com.techelevator.inventorylist.Item;

public class ItemTest {

	private Item item;
	private String name;
	private double price;
	private String itemType;

	@Before
	public void setup() {
		item = new Item(name, price, itemType);
	}

	@Test
	public void succesfully_creates_items_with_expected_values() {
		Item testItem = new Item("Beer", 3.00, "Beverage");

		Assert.assertEquals("Beer", testItem.getName());
		Assert.assertTrue(testItem.getPrice() == 3);
		Assert.assertEquals("Beverage", testItem.getItemType());

	}

	@Test
	public void succesfully_creates_items_with_expected_entree_values() {
		Item testItem = new Item("Baked Chicken", 8.75, "Entree");

		Assert.assertEquals("Baked Chicken", testItem.getName());
		Assert.assertTrue(testItem.getPrice() == 8.75);
		Assert.assertEquals("Entree", testItem.getItemType());

	}

}
