package com.techelevator;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.techelevator.register.ChangeMaker;
import com.techelevator.register.Register;

public class ChangeMakerTest {

	private ChangeMaker makeChange;
	
	@Before
	public void setup() {
		makeChange = new ChangeMaker();
	}
	@Test
	public void successfully_runs_with_expected_outcome() {
		Register register = new Register();
		register.deposit(new BigDecimal(87));
		makeChange.makeChange(register);
		Assert.assertEquals(makeChange.getTwentiesCount(),4);
		Assert.assertEquals(makeChange.getFivesCount(),1);
		Assert.assertEquals(makeChange.getOnesCount(),2);
	}
	
	@Test
	public void successfully_returns_expected_change() {
		Register register = new Register();
		register.deposit(new BigDecimal(40.95));
		makeChange.makeChange(register);
		Assert.assertEquals(makeChange.getTwentiesCount(),2);
		Assert.assertEquals(makeChange.getQuartersCount(),3);
		Assert.assertEquals(makeChange.getDimesCount(),2);
	}
	
	@Test
	public void successfully_returns_large_expected_change() {
		Register register = new Register();
		register.deposit(new BigDecimal(255.55));
		makeChange.makeChange(register);
		Assert.assertEquals(makeChange.getTwentiesCount(),12);
		Assert.assertEquals(makeChange.getTensCount(),1);
		Assert.assertEquals(makeChange.getFivesCount(),1);
		Assert.assertEquals(makeChange.getQuartersCount(),2);
		Assert.assertEquals(makeChange.getNickelsCount(),1);
	}
}
