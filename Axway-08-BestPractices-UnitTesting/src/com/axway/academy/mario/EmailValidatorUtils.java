package com.axway.academy.mario;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for validation some email address by the specific pattern.
 * 
 * @author MarioKrastev
 *
 */

public final class EmailValidatorUtils {

	/**
	 * Initializing email pattern - a regular expression.
	 */

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	// initializing Pattern and Matcher - we need them to check the email by our regular expression.
	private Pattern pattern;
	private Matcher matcher;

	/**
	 * A constructor to compile the email pattern.
	 */
	
	public EmailValidatorUtils() {
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	/**
	 * Boolean method for checking the email by the pattern.
	 * 
	 * @param email - email address
	 * @return true or false
	 * 
	 */
	
	public boolean isValid(String email) {
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
