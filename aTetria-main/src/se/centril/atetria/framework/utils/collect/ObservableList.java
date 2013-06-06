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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingList;
import com.google.common.collect.ForwardingListIterator;

/**
 * ObservableList is a list that notifies observes of changes.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 5, 2013
 */
public class ObservableList<E> extends ForwardingList<E> {
	/**
	 * Operation enumerates the type of change operation in list that was observed.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since Jun 5, 2013
	 */
	public enum Operation {
		ADD, REMOVE, CLEAR, UPDATE;

		public boolean isRemove() {
			return this == REMOVE || this == CLEAR;
		}
	}

	/**
	 * Definition of an observer for change events in list.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since Jun 5, 2013
	 */
	public static interface Observer<E> {
		/**
		 * Called when a change in list occurred.
		 *
		 * @param e the event.
		 */
		public void listChangeObserved( ObservableList<E>.Event e );
	}

	/**
	 * Event models events for changes in list.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since Jun 5, 2013
	 */
	public class Event {
		private Operation operation;

		private int index;
		private Collection<?> elements;

		protected Event( Operation op, int index, Collection<?> elements ) {
			this.operation = op;
			this.index = index;
			this.elements = elements;
		}

		/**
		 * The index that the {@link #operation()} was run for,<br/>
		 * or -1 index is unknown (e.g adding to end of list.
		 *
		 * @return the index.
		 */
		public int index() {
			return this.index;
		}

		/**
		 * The affected elements, or null.
		 *
		 * @return affected elements.
		 */
		public Collection<?> elements() {
			return this.elements;
		}

		/**
		 * The type of change operation.
		 *
		 * @return the operation.
		 */
		public Operation operation() {
			return this.operation;
		}

		/**
		 * The source ObservableList.
		 *
		 * @return the source.
		 */
		public ObservableList<E> source() {
			return ObservableList.this;
		}
	}

	private final List<E> delegate;

	private final List<Observer<E>> observers;

	@Override
	protected List<E> delegate() {
		return this.delegate;
	}

	/**
	 * Adds an observer to list of observers.
	 *
	 * @param o the observer to add.
	 */
	public void addObserver( Observer<E> o ) {
		this.observers.add( o );
	}

	/**
	 * Removes an observer from list of observers.
	 *
	 * @param o the observer to remove.
	 * @return true if any observer was removed.
	 */
	public boolean removeObserver( Observer<E> o ) {
		return this.observers.remove( o );
	}

	/**
	 * Tests if given observer has been registered.
	 *
	 * @param o the observer to test.
	 * @return true if observer is registered.
	 */
	public boolean hasObserver( Observer<E> o ) {
		return this.observers.contains( o );
	}

	/**
	 * Constructs the observable list given a list to delegate to.
	 *
	 * @param delegate the list to delegate to.
	 */
	public ObservableList( List<E> delegate ) {
		this( delegate, new ArrayList<Observer<E>>() );
	}

	private ObservableList( List<E> delegate, List<Observer<E>> listeners ) {
		this.delegate = delegate;
		this.observers = listeners;
	}

	@Override
	public boolean add( E element ) {
		boolean retr = super.add( element );
		this.fireEvent( new Event( Operation.ADD, -1, Collections.singleton( element ) ) );
		return retr;
	}

	@Override
	public boolean addAll( Collection<? extends E> collection ) {
		boolean retr = super.addAll( collection );
		this.fireEvent( new Event( Operation.ADD, -1, collection ) );
		return retr;
	}

	@Override
	public void add( int index, E element ) {
		super.add( index, element );
		this.fireEvent( new Event( Operation.ADD, index, Collections.singleton( element ) ) );
	}

	@Override
	public boolean addAll( int index, Collection<? extends E> elements ) {
		boolean retr = super.addAll( index, elements );
		this.fireEvent( new Event( Operation.ADD, index, elements ) );
		return retr;
	}

	@Override
	public void clear() {
		super.clear();
		this.fireEvent( new Event( Operation.CLEAR, -1, null ) );
	}

	@Override
	public boolean remove( Object object ) {
		if ( super.remove( object ) ) {
			this.fireEvent( new Event( Operation.REMOVE, -1, Collections.singleton( object ) ) );
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeAll( Collection<?> collection ) {
		if ( super.remove( collection ) ) {
			this.fireEvent( new Event( Operation.REMOVE, -1, collection ) );
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean retainAll( Collection<?> collection ) {
		if ( super.retainAll( collection ) ) {
			this.fireEvent( new Event( Operation.REMOVE, -1, collection ) );
			return true;
		} else {
			return false;
		}
	}

	private void fireEvent( Event e ) {
		for ( Observer<E> listener : this.observers ) {
			listener.listChangeObserved( e );
		}
	}

	@Override
	public E remove( int index ) {
		E elem = super.remove( index );
		this.fireEvent( new Event( Operation.REMOVE, index, Collections.singleton( elem ) ) );
		return elem;
	}

	@Override
	public E set( int index, E element ) {
		E old = super.set( index, element );
		this.fireEvent( new Event( Operation.UPDATE, index, Collections.singleton( element ) ) );
		return old;
	}

	@Override
	public List<E> subList( int fromIndex, int toIndex ) {
		return new ObservableList<E>( super.subList( fromIndex, toIndex ), this.observers );
	}

	@Override
	public Iterator<E> iterator() {
		return new ForwardingIterator<E>() {
			private E curr;

			private final Iterator<E> delegate = ObservableList.this.iterator();

			@Override
			protected Iterator<E> delegate() {
				return this.delegate;
			}

			@Override
			public E next() {
				this.curr = super.next();
				return this.curr;
			}

			@Override
			public void remove() {
				super.remove();
				fireEvent( new Event( Operation.REMOVE, -1, Collections.singleton( this.curr ) ) );
			}
		};
	}

	private class ListIter extends ForwardingListIterator<E> {

		private int currIndex;

		private E curr;

		private final ListIterator<E> delegate;

		@Override
		protected ListIterator<E> delegate() {
			return this.delegate;
		}

		public ListIter( ListIterator<E> delegate ) {
			this.delegate = delegate;
		}

		@Override
		public E next() {
			this.currIndex = this.nextIndex();
			this.curr = super.next();
			return this.curr;
		}

		@Override
		public E previous() {
			this.currIndex = this.previousIndex();
			this.curr = super.previous();
			return this.curr;
		}

		@Override
		public void remove() {
			super.remove();
			fireEvent( new Event( Operation.REMOVE, this.currIndex, Collections.singleton( this.curr ) ) );
		}

		@Override
		public void add( E element ) {
			super.add( element );
			fireEvent( new Event( Operation.ADD, this.currIndex, Collections.singleton( element ) ) );
		}

		@Override
		public void set( E element ) {
			super.set( element );
			fireEvent( new Event( Operation.UPDATE, this.currIndex, Collections.singleton( element ) ) );
			this.curr = element;
		}
	}
	
	@Override
	public ListIterator<E> listIterator() {
		return new ListIter( super.listIterator() );
	}
	
	@Override
	public ListIterator<E> listIterator( int index ) {
		return new ListIter( super.listIterator( index ) );
	}
}