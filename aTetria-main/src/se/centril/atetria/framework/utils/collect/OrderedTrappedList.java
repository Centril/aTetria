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
	 * Returns a list iterator that forbids modification other than adding.
	 *
	 * @return the list iterator.
	 */
	public ListIterator<E> listIterator() {
		return ImmutableListIterator.forList( this.delegate() );
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