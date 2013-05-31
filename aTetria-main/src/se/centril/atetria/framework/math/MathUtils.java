package se.centril.atetria.framework.math;

/**
 * A class with common math related algorithms.
 * 
 * @author Peter Hillerström
 * @since 2013-04-22
 * @version 1
 */
public final class MathUtils {
	private MathUtils() {
		throw new AssertionError();
	}

	/**
	 * Fast method for calculating the amounts of digits in an integer.
	 * Counts the minus-sign in negative numbers as a digit.
	 * 
	 * Uses divide and conquer, see 
	 * http://stackoverflow.com/questions/1306727/way-to-get-number-of-digits-in-an-int
	 * 
	 * @param number the integer to count digits in
	 * @return amounts of digits in an integer
	 */
	public static int digitsInNumber( int number ) {
		if ( number == Integer.MIN_VALUE ) { // Special case.
			return 11;
		}

		int n = Math.abs( number );
		int digits;

		if ( n < 100000 ) { // 5 or less digits
			if ( n < 100 ) { // 2 or less digits
				if ( n < 10 ) {
					digits = 1;
				} else {
					digits = 2;
				}
			} else { // 3 to 5 digits
				if ( n < 1000 ) {
					digits = 3;
				} else {
					if ( n < 10000 ) {
						digits = 4;
					} else {
						digits = 5;
					}
				}
			}
		} else { // 6 or more digits
			if ( n < 10000000 ) { // More than 5, less than 8
				if ( n < 1000000 ) {
					digits = 6;
				} else {
					digits = 7;
				}
			} else { // More than 7, less than 11.
				if ( n < 100000000 ) {
					digits = 8;
				} else {
					if ( n < 1000000000 ) {
						digits = 9;
					} else {
						digits = 10;
					}
				}
			}
		}

		// Adds 1 if number is negative
		if ( number < 0 ) {
			digits++;
		}

		return digits;
	}
}