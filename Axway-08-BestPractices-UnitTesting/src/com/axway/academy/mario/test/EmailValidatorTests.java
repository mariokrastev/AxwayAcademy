package com.axway.academy.mario.test;

import org.junit.Test;
import org.junit.Assert;

import com.axway.academy.mario.EmailValidatorUtils;

/**
 * A class that tests our validation method. 
 * Here we have 6 different tests - 3 with valid emails and 3 with invalid emails.
 * 
 * @author MarioKrastev
 *
 */

public class EmailValidatorTests {

	// initializing our object - the validator
	private EmailValidatorUtils validator = new EmailValidatorUtils();

	// test with only one name
	@Test
	public void email_oneName_shouldPass() {
		boolean validation = this.validator.isValid("mario@abv.bg");
		Assert.assertTrue(validation);
	}

	// test with two names separated with _
	@Test
	public void email_twoNames_shouldPass() {
		boolean validation = this.validator.isValid("peter_peter@gmail.com");
		Assert.assertTrue(validation);
	}

	// test with two names and digits after every name, separated with _
	@Test
	public void email_numbersAndNames_shouldPass() {
		boolean validation = this.validator.isValid("mario123_krastev567@gmail.com");
		Assert.assertTrue(validation);
	}

	// test with null
	@Test
	public void email_nullValue_shouldNotPass() {
		boolean validation = this.validator.isValid("null");
		Assert.assertFalse(validation);
	}

	// test with empty string
	@Test
	public void email_emptyString_shouldNotPass() {
		boolean validation = this.validator.isValid("");
		Assert.assertFalse(validation);
	}

	// test without @ symbol
	@Test
	public void email_withoutATSymbol_shouldNotPass() {
		boolean validation = this.validator.isValid("mario123gmail.com");
		Assert.assertFalse(validation);
	}

}
