package com.techelevator.reader;

import java.io.FileNotFoundException;
import java.util.Map;

import com.techelevator.inventorylist.ProductSlot;

public interface StockReader {
	
	Map<String, ProductSlot> load() throws FileNotFoundException;

}
