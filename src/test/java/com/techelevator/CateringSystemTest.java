package com.techelevator;

import java.io.*;
import org.junit.*;

public class CateringSystemTest {

		private static final String STOCK_FILENAME = "cateringsystem.csv";
		private CateringSystem cs;
		
		@Before
		public void setup() throws FileNotFoundException {
			cs = new CateringSystem(STOCK_FILENAME);
			
		}
		
		@Test
		public void catering_system_has_stock() {
			Assert.assertEquals(18,  cs.getStock().size());
		}
	
}
