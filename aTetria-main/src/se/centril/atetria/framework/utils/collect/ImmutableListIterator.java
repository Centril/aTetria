package se.centril.atetria.framework.utils.collect;

import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.UnmodifiableListIterator;

/**
 * ImmutableListIterator wraps a ListIterator and forbids modification.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 30, 2013
 */
public class ImmutableListIterator<E> extends UnmodifiableListIterator<E> {
	private ListIterator<E> delegate;

	/**
	 * Constructs an ImmutableListIterator wrapping a list iterator.
	 *
	 * @param iter the list iterator to wrap/delegate to.
	 */
	public ImmutableListIterator( ListIterator<E> iter ) {
		this.delegate = iter;
	}

	/**
	 * Creates an ImmutableListIterator wrapping a list iterator.
	 *
	 * @param iter the list iterator to wrap/delegate to.
	 * @return the new ImmutableListIterator.
	 */
	public static <E> ImmutableListIterator<E> create( ListIterator<E> iter ) {
		return new ImmutableListIterator<E>( iter );
	}

	/**
	 * Create an iterator for a given list starting from index 0.
	 *
	 * @param list list to get list iterator from.
	 * @return the created iterator.
	 */
	public static <E> ImmutableListIterator<E> forList( List<E> list ) {
		return create( list.listIterator() );
	}

	/**
	 * Create an iterator for a given list starting from index.
	 *
	 * @param list list to get list iterator from.
	 * @param index starting index.
	 * @return the created iterator.
	 */
	public static <E> ImmutableListIterator<E> forList( List<E> list, int index ) {
		return create( list.listIterator( index ) );
	}

	@Override
	public boolean hasNext() {
		return this.delegate.hasNext();
	}

	@Override
	public E next() {
		return this.delegate.next();
	}

	@Override
	public boolean hasPrevious() {
		return this.delegate.hasPrevious();
	}

	@Override
	public E previous() {
		return this.delegate.previous();
	}

	@Override
	public int nextIndex() {
		return this.delegate.nextIndex();
	}

	@Override
	public int previousIndex() {
		return this.delegate.previousIndex();
	}
}