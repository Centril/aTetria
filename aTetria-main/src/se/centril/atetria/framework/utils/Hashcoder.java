package se.centril.atetria.framework.utils;

/**
 * <p>Hashcoder provides methods which allow easy implementation of <code>hashCode</code>.
 * Each class which overrides equals() <em>must have exactly one unique and <strong>static</strong></em> instance of HashCodeMaker.
 * Being static is a requirement in order to make the client hashCode() method deterministic, which it must be.</p>
 *
 * <p>Each instance of HashCodeMaker has a unique factor f. The class has a constant PRIME.
 * Given a unique factor f, a constant prime p, a quantity Q of contributing properties,
 * the following algorithm is used:
 * <pre>
 * result = f
 * result = result * fp + Q0
 * ...
 * result = result * fp + QN
 * </pre></p>
 *
 * Inspired by: http://www.javapractices.com/topic/TopicAction.do?Id=28
 *
 * <p>Hashcoder also provides static methods for hashcoding primitives and objects.</p>
 *
 * @since 2012-11-22
 * @author Mazdak Farrokhzad <mazdakf@student.chalmers.se>
 * @version 1.1.0
 * @example <pre>
 * public final static HashCodeMaker hashCodeMaker = new HashCodeMaker();
 * public int hashCode() {
 *	return hashCodeMaker.getHash( Integer.valueOf( this.x ), Integer.valueOf( this.y ), ... );
 * }
 * </pre>
 * @example <pre>
 * public final static HashCodeMaker hashCodeMaker = new HashCodeMaker();
 * public int hashCode() {
 *	return hashCodeMaker.getHash( this.userId );
 * }
 * </pre>
 */
public final class Hashcoder {
	/**
	 * Returns hash-code for a primitive boolean.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( boolean val ) {
		return val ? 1231 : 1237;
	}

	/**
	 * Returns hash-code for a primitive integer.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( int val ) {
		return val;
	}

	/**
	 * Returns hash-code for a primitive char.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( char val ) {
		return (int) val;
	}

	/**
	 * Returns hash-code for a primitive float.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( float val ) {
		return Float.floatToIntBits( val );
	}

	/**
	 * Returns hash-code for a primitive long.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( long val ) {
		return (int) (val ^ (val >>> 32));
	}

	/**
	 * Returns hash-code for a primitive long.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( double val ) {
		return hash( Double.doubleToLongBits( val ) );
	}

	/**
	 * Returns hash-code for an object.
	 *
	 * @param val value to hash.
	 * @return the hash code.
	 */
	public static int hash( Object val ) {
		return val == null ? 0 : val.hashCode();
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * When many values contribute to hash this signature should be used.
	 *
	 * @param objects The object to make hashCode from.
	 * @return The resulting hash.
	 */
	public int get( Object...objects ) {
		return this.get( (Object) objects );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param obj The object to make hashCode from.
	 * @return The resulting hash.
	 */
	public int get( Object obj ) {
		int result = this.uniqueFactor;

		// Object could be null or array, these need special cases.
		if ( obj == null ) {
			result = this.get( 0 );
		} else if ( !isArray( obj ) ) {
			result = this.get( hash( obj ) );
		} else {
			// Decided against Arrays.deepHashCode(...) due to this.uniqueFactor.
			// If multidimensional: recursive call; else it is plain so we don't want: (result + 1)fp + hash.
			for ( Object element : (Object[]) obj ) {
				result = this.get( result, isArray( element ) ? this.get( element ) : hash( obj ) );
			}
		}

		return result;
	}

	/**
	 * Returns a hashCode first multiplying (fp) with a previous result.
	 * Should be used in an overridden hashCode() method if more fine-tuning is required.
	 *
	 * @param i An integer value to hash.
	 * @param prev Previous hash-component to sub-part into hash.
	 * @return The resulting hash.
	 * @see Integer#hashCode()
	 */
	public int get( int i, int prev ) {
		return prev * this.firstTerm() + hash( i );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * This signature is also is applicable to shorts, bytes via implicit conversion.
	 *
	 * @param i An integer value to hash.
	 * @return The resulting hash.
	 * @see Integer#hashCode()
	 */
	public int get( int i ) {
		return this.get( i, 1 );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param b A boolean value to hash.
	 * @return The resulting hash.
	 * @see #hash(boolean)
	 */
	public int get( boolean b ) {
		return this.get( hash( b ) );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param c A char value to hash.
	 * @return The resulting hash.
	 * @see #hash(char)
	 */
	public int get( char c ) {
		return this.get( hash( c ) );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param l A long value to hash.
	 * @return The resulting hash.
	 * @see #hash(long)
	 */
	public int get( long l ) {
		return this.get( hash( l ) );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param f A long value to hash.
	 * @return The resulting hash.
	 * @see #hash(float)
	 */
	public int get( float f ) {
		return this.get( hash( f ) );
	}

	/**
	 * Returns a hashCode.
	 * Should be used in an overridden hashCode() method.
	 *
	 * @param d A long value to hash.
	 * @return The resulting hash.
	 * @see #hash(double)
	 */
	public int get( double d ) {
		return this.get( hash( d ) );
	}

	/**
	 * An initial value for factor.
	 */
	private static int factorCount = 0;

	/**
	 * Unique factor for every HashCodeMaker.
	 *
	 * An initial value for a <code>hashCode</code>, to which is added contributions
	 * from fields. Using a non-zero value decreases collisions of <code>hashCode</code> values.
	 */
	private final int uniqueFactor = ++factorCount;

	/**
	 * Prime number to use, doesn't really matter which one,
	 * but 31 is used in Java:s standard library and thus we use that for consistency.
	 *
	 * "The value 31 was chosen because it is an odd prime.
	 * If it were even and the multiplication overflowed, information would be lost,
	 * as multiplication by 2 is equivalent to shifting.
	 * The advantage of using a prime is less clear, but it is traditional.
	 * A nice property of 31 is that the multiplication can be replaced by a shift and a subtraction for better performance:
	 * 31 * i == (i << 5) - i. Modern VMs do this sort of optimization automatically."
	 * - Joshua Bloch's Effective Java (Chapter 3, Item 9: Always override hashcode when you override equals, page 48)
	 *
	 * @link http://stackoverflow.com/questions/299304/why-does-javas-hashcode-in-string-use-31-as-a-multiplier
	 */
	private final static int PRIME = 31;

	/**
	 * Make the "first-term" of the hash.
	 *
	 * @return
	 */
	private int firstTerm() {
		return PRIME * this.uniqueFactor;
	}

	/**
	 * Tests whether or not an object is an array.
	 *
	 * @param obj Object to test.
	 * @return True if it is an array.
	 */
	private static boolean isArray( Object obj ) {
		return obj.getClass().isArray();
	}
} 