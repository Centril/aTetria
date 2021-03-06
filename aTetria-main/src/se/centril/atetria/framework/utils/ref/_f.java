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
 * <p>Special version of _ for floats.</p>
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
public final class _f {
	private float ref;

	/**
	 * Constructs the reference with null as value.
	 */
	public _f() {
	}

	/**
	 * Constructs the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 */
	public _f( float value ) {
		this.ref = value;
	}

	/**
	 * Creates reference with null as value.
	 *
	 * @return the reference.
	 */
	public static _f $() {
		return new _f();
	}

	/**
	 * Creates the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 * @return the reference.
	 */
	public static _f $( float value ) {
		return new _f( value );
	}

	/**
	 * Returns the value of the reference.
	 *
	 * @return the value of the reference.
	 */
	public float r() {
		return this.ref;
	}

	/**
	 * Sets the value of the reference.
	 *
	 * @param value the value to set.
	 */
	public void r( float value ) {
		this.ref = value;
	}

	/**
	 * The given obj is equal to this iff one of the following:<br/>
	 * this == obj, the {@link #r()} equals obj.r() if obj instanceof {@link _f},<br/>
	 * or if {@link #r()} equals obj.
	 */
	public boolean equals( Object obj ) {
		return	this == obj || (obj != null && obj instanceof _f
			?	Objects.equal( this.r(), ((_f) obj).r() )
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
		return Float.toString( this.ref );
	}
}