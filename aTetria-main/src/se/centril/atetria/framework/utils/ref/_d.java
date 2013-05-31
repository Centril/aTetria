package se.centril.atetria.framework.utils.ref;

import se.centril.atetria.framework.utils.Hashcoder;

import com.google.common.base.Objects;

/**
 * <p>Special version of _ for doubles.</p>
 *
 * <p>Oh Oracle, why do thy smite me with lack of pass-by-reference?<br/>
 * I shallt redeem this injustice with providing me with C++ goodies.</p>
 *
 * <p><b>Use of this class NOT advised. Use if you've no other choice.</b></p>
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 28 apr 2013
 * @version 1.0
 * @see http://stackoverflow.com/questions/430479/how-do-i-use-an-equivalent-to-c-reference-parameters-in-java
 */
public final class _d {
	private double ref;

	/**
	 * Constructs the reference with null as value.
	 */
	public _d() {
	}

	/**
	 * Constructs the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 */
	public _d( double value ) {
		this.ref = value;
	}

	/**
	 * Creates reference with null as value.
	 *
	 * @return the reference.
	 */
	public static _d $() {
		return new _d();
	}

	/**
	 * Creates the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 * @return the reference.
	 */
	public static _d $( double value ) {
		return new _d( value );
	}

	/**
	 * Returns the value of the reference.
	 *
	 * @return the value of the reference.
	 */
	public double r() {
		return this.ref;
	}

	/**
	 * Sets the value of the reference.
	 *
	 * @param value the value to set.
	 */
	public void r( double value ) {
		this.ref = value;
	}

	/**
	 * The given obj is equal to this iff one of the following:<br/>
	 * this == obj, the {@link #r()} equals obj.r() if obj instanceof {@link _d},<br/>
	 * or if {@link #r()} equals obj.
	 */
	public boolean equals( Object obj ) {
		return	this == obj || (obj != null && obj instanceof _d
			?	Objects.equal( this.r(), ((_d) obj).r() )
			:	Objects.equal( this.r(), obj ));
	}

	/**
	 * Returns {@link #r()}.
	 */
	@Override
	public int hashCode() {
		return Hashcoder.hash( this.ref );
	}

	/**
	 * Relays to the values toString method.<br/>
	 *
	 * @return the value as string.
	 */
	public String toString() {
		return Double.toString( this.ref );
	}
}