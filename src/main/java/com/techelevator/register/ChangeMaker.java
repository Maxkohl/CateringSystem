package com.techelevator.register;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ChangeMaker {

	int twentiesCount = 0;
	int tensCount = 0;
	int fivesCount = 0;
	int onesCount = 0;
	int quartersCount = 0;
	int dimesCount = 0;
	int nickelsCount = 0;

	public void makeChange(Register register) {
		BigDecimal balance = register.getAccountBalance();

		while (balance.setScale(2, RoundingMode.CEILING)
				.compareTo(new BigDecimal(0).setScale(2, RoundingMode.FLOOR)) == 1) {

			if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(20).setScale(2, RoundingMode.FLOOR)) >= 0) {
				twentiesCount++;
				balance = balance.subtract(new BigDecimal(20));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(10).setScale(2, RoundingMode.FLOOR)) >= 0) {
				tensCount++;
				balance = balance.subtract(new BigDecimal(10));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(5).setScale(2, RoundingMode.FLOOR)) >= 0) {
				fivesCount++;
				balance = balance.subtract(new BigDecimal(5));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(1).setScale(2, RoundingMode.FLOOR)) >= 0) {
				onesCount++;
				balance = balance.subtract(new BigDecimal(1));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.25).setScale(2, RoundingMode.FLOOR)) >= 0) {
				quartersCount++;
				balance = balance.subtract(new BigDecimal(.25));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.10).setScale(2, RoundingMode.FLOOR)) >= 0) {
				dimesCount++;
				balance = balance.subtract(new BigDecimal(.10));
			} else if (balance.setScale(2, RoundingMode.CEILING)
					.compareTo(new BigDecimal(.05).setScale(2, RoundingMode.FLOOR)) >= 0) {
				fivesCount++;
				balance = balance.subtract(new BigDecimal(.05));
			} else {
				break;
			}

		}

	}

	public int getTwentiesCount() {
		return twentiesCount;
	}

	public int getTensCount() {
		return tensCount;
	}

	public int getFivesCount() {
		return fivesCount;
	}

	public int getOnesCount() {
		return onesCount;
	}

	public int getQuartersCount() {
		return quartersCount;
	}

	public int getDimesCount() {
		return dimesCount;
	}

	public int getNickelsCount() {
		return nickelsCount;
	}

}
