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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * <p>TrappedList is a trapped list of objects to which you may<br/>
 * add to, and read from, but never remove or otherwise modify.<br/>
 * The list can perform a {@link #lock()} which stops addition indefinitely.</p>
 *
 * <p>The list is backed by a list of your choosing.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 23 mar 2013
 * @version 1.0
 */
public class TrappedList<E> extends ForwardingList<E> {
	/**
	 * Thrown whenever you try to add to a locked TrappedList.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @since 23 mar 2013
	 * @version 1.0
	 */
	public static class LockedException extends RuntimeException {
		private static final long serialVersionUID = 143742157479194666L;
	}

	/** The list of added elems. */
	private List<E> delegate;

	@Override
	protected List<E> delegate() {
		return this.delegate;
	}

	/** Flag: is the list locked? */
	private boolean isLocked;

	/* --------------------------------------
	 * Construction related.
	 * --------------------------------------
	 */

	/**
	 * Constructs list with an empty array-list as backing list.
	 */
	public TrappedList() {
		this.delegate = Lists.newArrayList();
	}

	/**
	 * Constructs list given a backing list.
	 *
	 * @param list the backing list.
	 */
	public TrappedList( List<E> list ) {
		this.delegate = list;
	}

	/**
	 * Creates a new trapped list backed by an empty array-list.
	 *
	 * @return the trapped list.
	 */
	public static <E> TrappedList<E> create() {
		return new TrappedList<E>();
	}

	/**
	 * Creates a new trapped list given a backing list.
	 *
	 * @param list the backing list.
	 * @return the trapped list.
	 */
	public static <E> TrappedList<E> create( List<E> list ) {
		return new TrappedList<E>( list );
	}

	/* --------------------------------------
	 * Locking related.
	 * --------------------------------------
	 */

	/**
	 * Returns true if the list is locked.
	 *
	 * @return true if the list is locked.
	 */
	public boolean isLocked() {
		return this.isLocked;
	}

	/**
	 * Locks the list, addition is then forbidden.
	 *
	 * @return this trapped list.
	 */
	public TrappedList<E> lock() {
		this.isLocked = true;
		return this;
	}

	/* --------------------------------------
	 * General public interface.
	 * --------------------------------------
	 */

	/**
	 * Returns an unmodifiable view of the list.
	 *
	 * @return the list.
	 */
	public List<E> get() {
		return Collections.unmodifiableList( this.delegate() );
	}

	/**
	 * Adds to the list, it is then trapped inside.
	 *
	 * @param elem the object to add.
	 * @throws LockedException When {@link #isLocked()} == true.
	 */
	@Override
	public boolean add( E elem ) {
		if ( this.isLocked ) {
			throw new LockedException();
		}

		return super.add( elem );
	}

	/**
	 * Adds to the list, it is then trapped inside.
	 *
	 * @param index the index to add at.
	 * @param elem the object to add.
	 * @throws LockedException When {@link #isLocked()} == true.
	 */
	public void add( int index, E elem ) {
		if ( this.isLocked ) {
			throw new LockedException();
		}

		super.add( index, elem );
	}

	/**
	 * Adds to the list, elements are then trapped inside.
	 *
	 * @param c the collection to add.
	 * @throws LockedException When {@link #isLocked()} == true.
	 */
	@Override
	public boolean addAll( Collection<? extends E> c ) {
		if ( this.isLocked ) {
			throw new LockedException();
		}

		return super.addAll( c );
	}

	/**
	 * Adds to the list, elements are then trapped inside.
	 *
	 * @param index the index to add at.
	 * @param c the collection to add.
	 * @throws LockedException When {@link #isLocked()} == true.
	 */
	@Override
	public boolean addAll( int index, Collection<? extends E> c ) {
		if ( this.isLocked ) {
			throw new LockedException();
		}

		return super.addAll( index, c );
	}

	/**
	 * Returns an iterator that forbids modification.
	 *
	 * @return the iterator.
	 */
	public Iterator<E> iterator() {
		return Iterators.unmodifiableIterator( this.delegate().iterator() );
	}

	/**
	 * Returns a list iterator that forbids modification other than adding.
	 *
	 * @return the list iterator.
	 */
	public ListIterator<E> listIterator() {
		return AddOnlyIterator.forList( this.delegate() );
	}

	/**
	 * Returns a list iterator starting from index that forbids modification other than adding.
	 *
	 * @param index the starting index.
	 * @return the list iterator.
	 */
	public ListIterator<E> listIterator( int index ) {
		return AddOnlyIterator.forList( this.delegate(), index );
	}

	/**
	 * Returns a sublist for [fromIndex, toIndex).<br/>
	 * If this list {@link #isLocked()}, so will the sublist.
	 *
	 * @param fromIndex the starting index, inclusive.
	 * @param toIndex the ending index, exclusive.
	 * @return the sublist as a TrappedList.
	 */
	public TrappedList<E> subList( int fromIndex, int toIndex ) {
		TrappedList<E> list = create( super.subList( fromIndex, toIndex ) );
		return this.isLocked() ? list : list.lock();
	}

	/* --------------------------------------
	 * Unsupported public interface.
	 * --------------------------------------
	 */

	/**
	 * @deprecated Forbidden.
	 */
	@Override
	public void clear() {
		_throw();
	}

	/**
	 * @deprecated Forbidden.
	 */
	public E remove( int index ) {
		_throw();
		return null;
	}

	/**
	 * @deprecated Forbidden.
	 */
	public boolean remove( Object obj ) {
		_throw();
		return false;
	}

	/**
	 * @deprecated Forbidden.
	 */
	public boolean removeAll( Collection<?> c ) {
		_throw();
		return false;
	}

	/**
	 * @deprecated Forbidden.
	 */
	public boolean retainAll( Collection<?> c ) {
		_throw();
		return false;
	}

	/**
	 * @deprecated Forbidden.
	 */
	public E set( int index, Object obj ) {
		_throw();
		return null;
	}

	protected void _throw() {
		throw new UnsupportedOperationException( "Altercation other than adding is forbidden" );
	}
}