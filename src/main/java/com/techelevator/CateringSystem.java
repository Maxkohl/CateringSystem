package com.techelevator;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.reader.FileStockReader;
import com.techelevator.reader.StockReader;

public class CateringSystem {

	private Map<String, ProductSlot> stock = new LinkedHashMap<String, ProductSlot>();

	public CateringSystem(String filename) throws FileNotFoundException {
		StockReader reader = new FileStockReader(filename);
		stock = reader.load();
	}

	public Map<String, ProductSlot> getStock() {
		return stock;
	}

}
