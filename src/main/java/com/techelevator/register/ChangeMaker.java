package com.techelevator.register;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class ChangeMaker {


	public Map<BigDecimal, BigDecimal> makeChange(Register register) {
		BigDecimal balance = register.getAccountBalance();
		Map<BigDecimal, BigDecimal> changeMap = new HashMap <BigDecimal, BigDecimal> ();
			changeMap.put(new BigDecimal(20), new BigDecimal(0));
			changeMap.put(new BigDecimal(10), new BigDecimal(0));
			changeMap.put(new BigDecimal(5), new BigDecimal(0));
			changeMap.put(new BigDecimal(1), new BigDecimal(0));
			changeMap.put(new BigDecimal(.25), new BigDecimal(0));
			changeMap.put(new BigDecimal(.10), new BigDecimal(0));
			changeMap.put(new BigDecimal(.05), new BigDecimal(0));
			
		while (balance.setScale(2, RoundingMode.CEILING)
				.compareTo(new BigDecimal(0).setScale(2, RoundingMode.FLOOR)) == 1) {
			if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(20).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(20), changeMap.get(20).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(20));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(10).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(10), changeMap.get(10).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(10));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(5).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(5), changeMap.get(5).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(5));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(1).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(1), changeMap.get(1).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(1));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.25).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(.25), changeMap.get(.25).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(.25));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.10).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(.10), changeMap.get(.10).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(.10));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.05).setScale(2, RoundingMode.FLOOR)) >= 0) {
				changeMap.put(new BigDecimal(.05), changeMap.get(.05).add(new BigDecimal(1)));
				balance = balance.subtract(new BigDecimal(.05));
			} else {
				break;
			}

		}
		return changeMap;

	}

}