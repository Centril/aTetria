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

import se.centril.atetria.framework.utils.ref._i;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.google.common.collect.ObjectArrays;

/**
 * <p><b>UNTESTED!</b></p>
 *
 * <p>CombinedList is a view of a combination of 2 lists.<br/>
 * Indexes are treated as if the list is really one.<br/>
 * If you've a end index that needs converting, use {@link #convertEndIndex(int)}.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 20 apr 2013
 * @version 1.0
 */
public class CombinedList<E> implements List<E> {
	private final List<E> start;
	private final List<E> end;

	/**
	 * Constructs list view given starting list and ending list.
	 *
	 * @param start start list.
	 * @param end ending list.
	 */
	public CombinedList( List<E> start, List<E> end ) {
		this.start = Preconditions.checkNotNull( start );
		this.end = Preconditions.checkNotNull( end );
	}

	/**
	 * Creates list view given starting list and ending list.
	 *
	 * @param start start list.
	 * @param end ending list.
	 * @return the combined list.
	 */
	public static <E> CombinedList<E> create( List<E> start, List<E> end ) {
		return new CombinedList<E>( start, end );
	}

	/**
	 * Returns an unmodifiable view of this list.
	 *
	 * @return the unmodifiable view.
	 */
	public List<E> unmodifiable() {
		return Collections.unmodifiableList( this );
	}

	@Override
	public int size() {
		return this.start.size() + this.end.size();
	}

	/**
	 * Converts an index for start list for use in combined view.<br/>
	 * It really just returns the index given.
	 *
	 * @param index index
	 * @return index.
	 */
	public int convertStartIndex( int index ) {
		return index;
	}

	/**
	 * Converts an index for end list for use in combined view.
	 *
	 * @param index index in end list.
	 * @return the index in combined view.
	 */
	public int convertEndIndex( int index ) {
		return index + this.start.size();
	}

	@Override
	public boolean contains( Object object ) {
		return this.start.contains( object ) || this.end.contains( object );
	}

	@Override
	public boolean containsAll( Collection<?> c ) {
		return c.containsAll( this.start ) && c.containsAll( this.end );
	}

	private List<E> listWithLocation( _i location ) {
		if ( location.r() < 0  ) {
			throw new IndexOutOfBoundsException( "given location(" + location.r() + ") < 0, size = " + this.size() );
		}

		int startSize = this.start.size();

		if ( location.r() < startSize ) {
			return this.start;
		}

		location.r( location.r() - startSize );

		if ( location.r() < this.end.size() ) {
			return this.end;
		} else {
			throw new IndexOutOfBoundsException( "given location(" + location.r() + ") > size(" + this.size() + ")" );
		}
	}

	@Override
	public E get( int location ) {
		_i i = _i.$( location );
		return this.listWithLocation( i ).get( i.r() );
	}

	@Override
	public void add( int location, E elem ) {
		_i i = _i.$( location );
		this.listWithLocation( i ).add( i.r(), elem );
	}

	@Override
	public boolean addAll( int location, Collection<? extends E> c ) {
		_i i = _i.$( location );
		return this.listWithLocation( i ).addAll( i.r(), c );
	}

	@Override
	public E remove( int location ) {
		_i i = _i.$( location );
		return this.listWithLocation( i ).remove( i.r() );
	}

	@Override
	public E set( int location, E elem ) {
		_i i = _i.$( location );
		return this.listWithLocation( i ).set( i.r(), elem );
	}

	@Override
	public int indexOf( Object object ) {
		int index = this.start.indexOf( object );

		if ( index == -1 ) {
			index = this.end.indexOf( object );

			if ( index != -1 ) {
				index += this.start.size();
			}
		}

		return index;
	}

	@Override
	public int lastIndexOf( Object object ) {
		int index = this.start.lastIndexOf( object );

		if ( index == -1 ) {
			index = this.end.lastIndexOf( object );

			if ( index != -1 ) {
				index += this.start.size();
			}
		}

		return index;
	}

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@Override
	public Iterator<E> iterator() {
		return Iterators.concat( this.start.iterator(), this.end.iterator() );
	}

	@Override
	public ListIterator<E> listIterator() {
		return new CombinedListIterator<E>( this.start, this.end );
	}

	@Override
	public ListIterator<E> listIterator( int location ) {
		return new CombinedListIterator<E>( this.start, this.end, location );
	}

	@Override
	public List<E> subList( int start, int end ) {
		int startSize = this.start.size();

		if ( end <= startSize ) {
			// All elements in start list.
			return this.start.subList( start, end );
		} else if ( start >= startSize ) {
			// All elements in end list.
			return this.end.subList( start - startSize, end - startSize );
		} else {
			// In both lists.
			return new CombinedList<E>( this.start.subList( start, startSize ), this.end.subList( 0, end - startSize ) );
		}
	}

	@Override
	public Object[] toArray() {
		return ObjectArrays.concat( this.start.toArray(), this.end.toArray(), Object.class );
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public <T> T[] toArray( T[] array ) {
		T[] arr1 = this.start.toArray( array );
		T[] arr2 =  this.end.toArray( array );
		return ObjectArrays.concat( arr1, arr2, (Class<T>) array.getClass() );
	}

	/**
	 * Adds to the end of end list.
	 *
	 * @param elem
	 */
	@Override
	public boolean add( E elem ) {
		return this.end.add( elem );
	}

	/**
	 * Adds to the end of end list.
	 *
	 * @param elem
	 */
	@Override
	public boolean addAll( Collection<? extends E> c ) {
		return this.end.addAll( c );
	}

	@Override
	public void clear() {
		this.start.clear();
		this.end.clear();
	}

	@Override
	public boolean remove( Object object ) {
		return this.start.remove( object ) || this.end.remove( object );
	}

	@Override
	public boolean removeAll( Collection<?> c ) {
		boolean removed = this.start.removeAll( c );

		if ( this.end.removeAll( c ) ) {
			removed = true;
		}

		return removed;
	}

	@Override
	public boolean retainAll( Collection<?> c ) {
		boolean changed = this.start.retainAll( c );

		if ( this.end.retainAll( c ) ) {
			changed = true;
		}

		return changed;
	}
}