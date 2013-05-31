package se.centril.atetria.framework.utils.collect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.google.common.collect.ForwardingList;

/**
 * <p>CapacitatedList is a list that has a maximum capacity of elements,<br/>
 * when more elements than the maximum capacity is added,<br/>
 * the HEAD is removed - until capacity is enforced</p>
 *
 * <p>The meaning of HEAD depends on {@link List#remove(int)}<br/>
 * with argument 0, or {@link Queue#remove()} if Queue.</p>
 *
 * <p>NOTE: Complexity when removing is O(1) for random-access lists, O(n) for linked-lists.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 8, 2013
 */
public abstract class CapacitatedList<E> extends ForwardingList<E> {
	/**
	 * Default implementation.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since May 8, 2013
	 */
	public static class Impl<E> extends CapacitatedList<E> {
		private final List<E> delegate;

		public Impl( int capacity, List<E> delegate ) {
			super( capacity );
			this.delegate = delegate;
		}

		@Override
		protected final List<E> delegate() {
			return this.delegate;
		}
	}

	/**
	 * Returns a new CapacitatedList with a new ArrayList as backing list.
	 *
	 * @param capacity the capacity of list.
	 * @return the new CapacitatedList.
	 */
	public static final <E> CapacitatedList<E> newArrayList( int capacity ) {
		return new Impl<E>( capacity, new ArrayList<E>( capacity ) );
	}

	/**
	 * Returns a new CapacitatedList with a new LinkedList as backing list.
	 *
	 * @param capacity the capacity of list.
	 * @return the new CapacitatedList.
	 */
	public static final <E> CapacitatedList<E> newLinkedList( int capacity ) {
		return new Impl<E>( capacity, new LinkedList<E>() );
	}

	/** The limit/capacity of list. */
	private int capacity;

	/**
	 * Constructs the list with a given capacity.
	 *
	 * @param capacity the initial capacity. capacity > 0.
	 */
	public CapacitatedList( int capacity ) {
		super( );
		this.setCapacity( capacity );
	}

	/**
	 * Sets the capacity of list.
	 *
	 * @param capacity the limit of list. capacity > 0.
	 */
	protected void setCapacity( int capacity ) {
		if ( capacity > 0 ) {
			this.capacity = capacity;
		} else {
			throw new IllegalArgumentException( "CapacitatedList requires a capacity > 0, given capacity: " + capacity );
		}
	}

	/**
	 * Returns the current capacity of list.
	 *
	 * @return the current capacity of list.
	 */
	public int getCapacity() {
		return this.capacity;
	}

	/**
	 * Enforces the capacity restrictions of {@link #delegate()}.
	 */
	private void enforceCapacity() {
		List<E> delegate = this.delegate();

		if ( delegate instanceof Queue ) {
			/*
			 * Queue: use faster remove() method.
			 * If e.g. we've a LinkedList, subList(0, removeSize) is costly.
			 */
			Queue<?> queue = (Queue<?>) delegate;
			while ( this.size() > this.getCapacity() ) {
				queue.remove();
			}
		} else {
			int removeSize = this.size() - this.getCapacity();
			if ( removeSize == 0 ) {
				return;
			}

			this.subList( 0, removeSize ).clear();
		}
	}

	@Override
	public boolean add( E elem ) {
		this.delegate().add( elem );
		this.enforceCapacity();
		return true;
	}

	public void add( int index, E elem ) {
		this.delegate().add( index, elem );
		this.enforceCapacity();
	}

	public boolean addAll( Collection<? extends E> c ) {
		super.addAll( c );
		this.enforceCapacity();
		return true;
	}

	public boolean addAll( int index, Collection<? extends E> c) {
		super.addAll( index, c );
		this.enforceCapacity();
		return true;
	}
}