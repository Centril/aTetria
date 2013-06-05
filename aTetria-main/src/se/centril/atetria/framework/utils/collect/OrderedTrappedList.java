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

import java.util.Collection;
import java.util.ListIterator;

/**
 * <p>OrderedTrappedList is a TrappedList that only allows addition to the end of list.<br/>
 * Methods {@link #add(int, Object)} and {@link #addAll(java.util.Collection)},<br/>
 * and adding via {@link #listIterator()} is thus forbidden, as is via {@link #subList(int, int)}.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 30, 2013
 */
public class OrderedTrappedList<E> extends TrappedList<E> {
	/**
	 * @deprecated Forbidden.
	 */
	@Override
	public void add( int index, E elem ) {
		_throw();
	}

	/**
	 * @deprecated Forbidden.
	 */
	@Override
	public boolean addAll( int index, Collection<? extends E> c ) {
		_throw();
		return false;
	}

	/**
	 * Returns a list iterator that forbids modification.
	 *
	 * @return the list iterator.
	 */
	public ListIterator<E> listIterator() {
		return ImmutableListIterator.forList( this.delegate() );
	}

	/**
	 * Returns a list iterator starting from index that forbids modification.
	 *
	 * @param index the starting index.
	 * @return the list iterator.
	 */
	public ListIterator<E> listIterator( int index ) {
		return ImmutableListIterator.forList( this.delegate(), index );
	}

	/**
	 * Returns a sublist for [fromIndex, toIndex).<br/>
	 * The sublist will be locked.
	 *
	 * @param fromIndex the starting index, inclusive.
	 * @param toIndex the ending index, exclusive.
	 * @return the sublist as a TrappedList.
	 */
	public TrappedList<E> subList( int fromIndex, int toIndex ) {
		return create( super.subList( fromIndex, toIndex ) ).lock();
	}
}