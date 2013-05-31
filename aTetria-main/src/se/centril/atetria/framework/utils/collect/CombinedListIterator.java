package se.centril.atetria.framework.utils.collect;

import java.util.List;
import java.util.ListIterator;

/**
 * CombinedListIterator is a combined view of 2 list iterators.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 31, 2013
 */
public class CombinedListIterator<E> implements ListIterator<E> {
	private final ListIterator<E> start;
	private final ListIterator<E> end;

	private final int sizeStart;
	private boolean inEnd;

	public CombinedListIterator( ListIterator<E> start, ListIterator<E> end, int sizeStart ) {
		this.start = start;
		this.end = end;
		this.sizeStart = sizeStart;
	}

	public CombinedListIterator( List<E> start, List<E> end ) {
		this.sizeStart = start.size();
		this.start = start.listIterator();
		this.end = end.listIterator();
	}

	public CombinedListIterator( List<E> start, List<E> end, int startIndex ) {
		this.sizeStart = start.size();

		int endIndex = 0;
		if ( startIndex > this.sizeStart ) {
			this.inEnd = true;
			startIndex = this.sizeStart - 1;
		}

		this.start = start.listIterator( startIndex );
		this.end = end.listIterator( endIndex );
	}

	public boolean inEnd() {
		return this.inEnd;
	}

	@Override
	public boolean hasNext() {
		return this.start.hasNext() || this.end.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return this.start.hasPrevious() || this.end.hasPrevious();
	}

	@Override
	public int nextIndex() {
		return this.inEnd ? this.end.nextIndex() + sizeStart : this.start.nextIndex() ;
	}

	@Override
	public int previousIndex() {
		return this.nextIndex() - 1;
	}

	@Override
	public E next() {
		if ( this.inEnd ) {
			return this.end.next();
		} else {
			if ( this.start.hasNext() ) {
				return this.start.next();
			} else {
				this.inEnd = true;
				return this.next();
			}
		}
	}

	@Override
	public E previous() {
		if ( this.inEnd ) {
			if ( this.end.hasPrevious() ) {
				return this.end.previous();
			} else {
				this.inEnd = false;
				return this.previous();
			}
		} else {
			return this.start.previous();
		}
	}

	private ListIterator<E> currItr() {
		return this.inEnd ? this.end : this.start;
	}

	@Override
	public void remove() {
		this.currItr().remove();
	}

	@Override
	public void set( E e ) {
		this.currItr().set( e );
	}

	@Override
	public void add( E e ) {
		this.currItr().add( e );
	}
}