/*
 * This file is part of aTetria.
 *
 * aTetria is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aTetria is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aTetria. If not, see <http://www.gnu.org/licenses/>.
 */
package se.centril.atetria.framework.utils.ref;

import se.centril.atetria.framework.utils.Hashcoder;

import com.google.common.base.Objects;

/**
 * <p>Special version of _ for chars.</p>
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
public final class _c {
	private char ref;

	/**
	 * Constructs the reference with null as value.
	 */
	public _c() {
	}

	/**
	 * Constructs the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 */
	public _c( char value ) {
		this.ref = value;
	}

	/**
	 * Creates reference with null as value.
	 *
	 * @return the reference.
	 */
	public static _c $() {
		return new _c();
	}

	/**
	 * Creates the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 * @return the reference.
	 */
	public static _c $( char value ) {
		return new _c( value );
	}

	/**
	 * Returns the value of the reference.
	 *
	 * @return the value of the reference.
	 */
	public char r() {
		return this.ref;
	}

	/**
	 * Sets the value of the reference.
	 *
	 * @param value the value to set.
	 */
	public void r( char value ) {
		this.ref = value;
	}

	/**
	 * The given obj is equal to this iff one of the following:<br/>
	 * this == obj, the {@link #r()} equals obj.r() if obj instanceof {@link _c},<br/>
	 * or if {@link #r()} equals obj.
	 */
	public boolean equals( Object obj ) {
		return	this == obj || (obj != null && obj instanceof _c
			?	Objects.equal( this.r(), ((_c) obj).r() )
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
		return Character.toString( this.ref );
	}
}