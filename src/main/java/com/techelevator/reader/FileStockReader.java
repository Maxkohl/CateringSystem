package com.techelevator.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.techelevator.inventorylist.Appetizer;
import com.techelevator.inventorylist.Beverage;
import com.techelevator.inventorylist.Dessert;
import com.techelevator.inventorylist.Entree;
import com.techelevator.inventorylist.Item;
import com.techelevator.inventorylist.ProductSlot;

public class FileStockReader implements StockReader {

	private String filename;

	public FileStockReader(String filename) {
		this.filename = filename;
	}

	@Override
	public Map<String, ProductSlot> load() throws FileNotFoundException {
		List<String> lines = getLinesFromStockFile();
		return createInventoryMapFromLines(lines);
	}

	private List<String> getLinesFromStockFile() throws FileNotFoundException {
		List<String> lines = new ArrayList<String>();

		File stockFile = new File(filename);

		try (Scanner fileScanner = new Scanner(stockFile)) {

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				lines.add(line);
			}

		}
		return lines;
	}

	private ProductSlot createProductSlotFromSplitStockArray(String[] parts) {
		ProductSlot newProductSlot = null;
		String name = parts[1];
		double price = Double.parseDouble(parts[2]);
		String type = parts[3];
		Item item = null;
		String itemType = "";

		if (type.equalsIgnoreCase("b")) {
			itemType = "Beverage";
			item = new Beverage(name, price, itemType);
		}
		if (type.equalsIgnoreCase("a")) {
			itemType = "Appetizer";
			item = new Appetizer(name, price, itemType);
		}
		if (type.equalsIgnoreCase("d")) {
			itemType = "Dessert";
			item = new Dessert(name, price, itemType);
		}
		if (type.equalsIgnoreCase("e")) {
			itemType = "Entree";
			item = new Entree(name, price, itemType);
		}

		newProductSlot = new ProductSlot(item);
		return newProductSlot;

	}

	private Map<String, ProductSlot> createInventoryMapFromLines(List<String> lines) {
		Map<String, ProductSlot> inventoryMap = new LinkedHashMap<String, ProductSlot>();

		for (String line : lines) {
			String[] parts = line.split("\\|");
			ProductSlot productSlot = createProductSlotFromSplitStockArray(parts);
			inventoryMap.put(parts[0], productSlot);

		}
		return inventoryMap;
	}

}
