package com.techelevator.writer;

import java.io.BufferedWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter implements TextFileWriter {

	private static final String FILE_NAME = "log.txt";
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss a");

	String productCodeString = "";

	@Override
	public void log(String string, BigDecimal price, BigDecimal accountBalance, String productCode, String quantity) {
		LocalDateTime now = LocalDateTime.now();
		if (productCode != "") {
			productCode = productCode.substring(0, 1).toUpperCase() + productCode.substring(1);
		}
		try (BufferedWriter buffer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
			buffer.write(dtf.format(now) + " " + productCode + " " + string + " " + quantity + " " + " $"
					+ price.setScale(2, RoundingMode.CEILING) + " $" + accountBalance.setScale(2, RoundingMode.CEILING)
					+ System.getProperty("line.separator"));
		} catch (IOException e) {
			System.out.println("An IO Exception error has occured in the log writer");
		}
	}

}
