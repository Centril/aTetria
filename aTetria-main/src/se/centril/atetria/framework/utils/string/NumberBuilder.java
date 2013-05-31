package se.centril.atetria.framework.utils.string;


/**
 * NumberBuilder: A simple class for building and updating a String with a number.
 * 
 * @author Peter Hillerström
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 2013-04-23
 * @version 3
 */
public class NumberBuilder {
	/** The current number. */
	private int number;

	/** The internal StringBuilder */
	private final StringBuilder builder;
	
	/** The start index when updating the StringBuilder */
	private final int startOffset;
	
	private final String postString;
	
	/**
	 * Constructs the builder with the specified texts and a initial number.
	 * For example: "Money: ", 100, "$" will give the String "Money: 100$" where only the number
	 * will change when you update the Builder.
	 * 
	 * Null is interpreted as an empty string.
	 * 
	 * @param preString the String before the number
	 * @param initialNumber the initial number
	 * @param postString the String after the number
	 */
	public NumberBuilder(String preString, int initialNumber, String postString) {
		builder = new StringBuilder();
		this.postString = postString;
		
		if(preString != null) {
			builder.append(preString);
			startOffset = preString.length();
		} else {
			startOffset = 0;
		}
		builder.append(initialNumber);
		if(postString != null) {
			builder.append(postString);
		}
	}
	
	public NumberBuilder(String preString, int initalNumber) {
		this(preString, initalNumber, null);
	}
	
	public NumberBuilder(int initialNumber, String postString) {
		this(null, initialNumber, postString);
	}
	
	public NumberBuilder(int initialNumber) {
		this(null, initialNumber, null);
	}

	/**
	 * Updates/sets the new current number.
	 * 
	 * @param number the number to set
	 */
	public void update(int number) {
		if(this.number != number) {
			this.number = number;
			
			/*
			 * Unfortunately this bit of code didn't work as expected. But I'll let it remain so
			 * someone can try to fix it in the future if they ever get the time.
			 */
			//builder.delete(startOffset, startOffset + MathUtils.digitsInNumber(number));
			//builder.insert(startOffset, number);
			
			builder.delete(startOffset, builder.length()).append(number);
			if(postString != null) {
				builder.append(postString);
			}
		}
	}

	/**
	 * Returns the current number.
	 *
	 * @return the current number.
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Returns the string.
	 */
	@Override
	public String toString() {
		return this.builder.toString();
	}
}
