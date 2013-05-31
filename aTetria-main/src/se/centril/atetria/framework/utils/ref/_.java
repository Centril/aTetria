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

import com.google.common.base.Objects;

/**
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
public final class _<E> {
	private E ref;

	/**
	 * Constructs the reference with null as value.
	 */
	public _() {
	}

	/**
	 * Constructs the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 */
	public _( E value ) {
		this.ref = value;
	}

	/**
	 * Creates reference with null as value.
	 *
	 * @return the reference.
	 */
	public static <E> _<E> $() {
		return new _<E>();
	}

	/**
	 * Creates the reference with initial value.<br/>
	 * Null is allowed.
	 *
	 * @param value the value to set.
	 * @return the reference.
	 */
	public static <E> _<E> $( E value ) {
		return new _<E>( value );
	}

	/**
	 * Returns the value of the reference.
	 *
	 * @return the value of the reference.
	 */
	public E r() {
		return this.ref;
	}

	/**
	 * Sets the value of the reference.
	 *
	 * @param value the value to set.
	 */
	public void r( E value ) {
		this.ref = value;
	}

	/**
	 * The given obj is equal to this iff one of the following:<br/>
	 * this == obj, the {@link #r()} equals obj.r() if obj instanceof {@link _},<br/>
	 * or if {@link #r()} equals obj.
	 */
	public boolean equals( Object obj ) {
		return	this == obj || (obj != null && obj instanceof _
			?	Objects.equal( this.r(), ((_<?>) obj).r() )
			:	Objects.equal( this.r(), obj ));
	}

	/**
	 * If {@link #r()} == null => 0, otherwise the hashcode of {@link #r()}.<br/>
	 * Behaves like {@link java.util.Objects#hashCode(Object)}
	 */
	@Override
	public int hashCode() {
		return this.r() == null ? 0 : this.r().hashCode();
	}

	/**
	 * Relays to the values toString method.<br/>
	 * Returns "null" if {@link #r()} == null.<br/>
	 * Behaves like {@link java.util.Objects#toString(Object)}.
	 *
	 * @return the value as string.
	 */
	public String toString() {
		return ref == null ? "null" : ref.toString();
	}
}