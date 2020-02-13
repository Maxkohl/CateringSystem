package com.techelevator;

import java.io.*;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.reader.FileStockReader;
import com.techelevator.reader.StockReader;

public class FileStockReaderTest {

	private static final String STOCK_FILE = "cateringsystem.csv";

	private StockReader reader;

	@Before
	public void setup() {
		reader = new FileStockReader(STOCK_FILE);
	}

	@Test
	public void stock_has_accurate_items() {

		int expectedItemCount = 18;

		Map<String, ProductSlot> stock = null;
		try {
			stock = reader.load();
		} catch (FileNotFoundException e) {
			Assert.fail("Inventory File not Found");
		}
		Assert.assertNotNull(stock);
		Assert.assertEquals(expectedItemCount, stock.size());
	}

	@Test
	public void returns_expected_item() {

		Map<String, ProductSlot> stock = null;
		try {
			stock = reader.load();
		} catch (FileNotFoundException e) {
			Assert.fail("Inventory File not Found");
		}

		Assert.assertEquals("Beer", stock.get("B3").getItem().getName());

	}

}
