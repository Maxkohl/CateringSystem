package com.techelevator;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import com.techelevator.inventorylist.Appetizer;
import com.techelevator.inventorylist.Beverage;
import com.techelevator.inventorylist.Dessert;
import com.techelevator.inventorylist.Entree;
import com.techelevator.inventorylist.ProductSlot;
import com.techelevator.register.Register;

import junit.framework.Assert;

public class RegisterTest {

	private Register register;

	@Before
	public void setup() {
		register = new Register();
	}

	@Test
	public void returns_true_if_succesfully_deposits_funds_to_balance() {
		BigDecimal expected = new BigDecimal(1543);
		register.deposit(expected);

		Assert.assertTrue(expected.compareTo(register.getAccountBalance()) == 0);
	}

	@Test
	public void returns_true_large_funds_added_to_balance() {
		register.resetAccountBalance();
		BigDecimal expected = new BigDecimal(3629);
		register.deposit(expected);

		Assert.assertTrue(expected.compareTo(register.getAccountBalance()) == 0);
	}

	@Test
	public void returns_true_if_successful_withdraw() {
		register.resetAccountBalance();
		BigDecimal deposit = new BigDecimal(4000);
		register.deposit(deposit);
		BigDecimal withdrawal = new BigDecimal(1500);
		register.withdrawal(withdrawal);
		BigDecimal balance = register.getAccountBalance();
		BigDecimal expected = new BigDecimal(2500);
		Assert.assertTrue(balance.compareTo(expected) == 0);
	}

	@Test
	public void returns_true_if_balance_reset_passes() {
		register.resetAccountBalance();
		BigDecimal deposit = new BigDecimal(4000);
		register.deposit(deposit);
		register.resetAccountBalance();
		BigDecimal expected = new BigDecimal(0);
		Assert.assertTrue(register.getAccountBalance().compareTo(expected) == 0);
	}
	
	//negative balance
	

}
