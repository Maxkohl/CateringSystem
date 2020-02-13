package com.techelevator.writer;

import java.math.BigDecimal;

public interface TextFileWriter {
	
	void log(String string, BigDecimal price, BigDecimal accountBalance, String productCode, String quantity);

}
