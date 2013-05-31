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
package se.centril.atetria.framework.utils.collect;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ForwardingListIterator;

/**
 * AddOnlyIterator is a ListIterator wrapper that only allows reading & adding elements.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 30, 2013
 */
public class AddOnlyIterator<E> extends ForwardingListIterator<E> {
	private final ListIterator<E> iter;

	/**
	 * Constructs iterator.
	 *
	 * @param iter the iterator to delegate to.
	 */
	public AddOnlyIterator( ListIterator<E> iter ) {
		this.iter = iter;
	}

	/**
	 * Create an iterator given a backing iterator.
	 *
	 * @param iter iterator to delegate to.
	 * @return the created iterator.
	 */
	public static <E> AddOnlyIterator<E> create( ListIterator<E> iter ) {
		return new AddOnlyIterator<E>( iter );
	}

	/**
	 * Create an iterator for a given list starting from index 0.
	 *
	 * @param list list to get list iterator from.
	 * @return the created iterator.
	 */
	public static <E> AddOnlyIterator<E> forList( List<E> list ) {
		return create( list.listIterator() );
	}

	/**
	 * Create an iterator for a given list starting from index.
	 *
	 * @param list list to get list iterator from.
	 * @param index starting index.
	 * @return the created iterator.
	 */
	public static <E> AddOnlyIterator<E> forList( List<E> list, int index ) {
		return create( list.listIterator( index ) );
	}

	@Override
	protected ListIterator<E> delegate() {
		return this.iter;
	}

	/**
	 * @deprecated Forbidden.
	 */
	public void remove() {
		_throw();
	}

	/**
	 * @deprecated Forbidden.
	 */
	public void set( E elem ) {
		_throw();
	}

	protected void _throw() {
		throw new UnsupportedOperationException( "Altercation other than adding is forbidden" );
	}
}