package se.centril.atetria.framework.utils.collect;

import java.util.List;

import com.google.common.collect.ForwardingList;


/**
 * <p>PaginatedList<E> implements the model of a paginated list.<br/>
 * It is setup by a backend list given by {@link #backend()} and set by {@link #backend(List)},<br/>
 * a per-page number given by {@link #perPage()} and set by {@link #perPage(int)},<br/>
 * and a current page given by {@link #currentPage()} and set by {@link #currentPage(int)}.</p>
 *
 * The number of pages is retrievable by {@link #pageCount()}.<br/>
 * The list of items for the current page is retrievable by {@link #page()}.<br/>
 * 
 * @param <E> The type of the list items.
 * @author Centril <twingoow@gmail.com>
 * @since 2013-02-23
 * @version 1.1
 */
public class PaginatedList<E> extends ForwardingList<E> {
	private List<E> backend;

	@Override
	protected List<E> delegate() {
		return this.backend;
	}

	private int currentPage = 0;
	private int perPage;

	private int pageCount;

	/**
	 * Creates a new model of a paginated list.
	 * @param allItems the items to paginate.
	 * @param perPage the number of items per page.
	 */
	public PaginatedList( List<E> items, int perPage ) {
		this( items, perPage, 0 );
	}

	/**
	 * Creates a new model of a paginated list.
	 * @param allItems the items to paginate.
	 * @param perPage the number of items per page.	 * 
	 * @param startPage the initial page of the paginated list.
	 */
	public PaginatedList( List<E> backend, int perPage, int startPage ) {
		this.backend = backend;
		this.perPage = perPage;

		this.updatePageCount();

		this.currentPage( startPage );
	}

	/**
	 * Returns the elements for the current page.
	 *
	 * @return the elements for the current page.
	 */
	public List<E> page() {
		return this.delegate().subList( this.startIndex(), this.endIndex() );
	}

	/**
	 * Returns the start index of {@link #currentPage()}.
	 *
	 * @return the start index of {@link #currentPage()}.
	 */
	public int startIndex() {
		return this.currentPage() * this.perPage();
	}

	/**
	 * Returns the end index of {@link #currentPage()}.
	 *
	 * @return the end index of {@link #currentPage()}.
	 */
	public int endIndex() {
		int end = this.startIndex() + this.perPage();
		return end > this.size() ? this.size() : end;
	}

	/**
	 * Calculates & saves how many pages there are.
	 */
	public void updatePageCount() {
		this.pageCount = this.calculatePageCount();
	}

	/**
	 * Calculates how many pages there are.
	 *
	 * @return Amount of pages.
	 */
	protected int calculatePageCount() {
		return (this.size() + this.perPage() - 1) / this.perPage();
	}

	/**
	 * Returns the list of elements to be paginated.
	 *
	 * @return the list of elements to be paginated.
	 */
	public List<E> backend() {
		return this.delegate();
	}

	/**
	 * Sets the list of items to be paginated.
	 *
	 * @param allItems the list of items to be paginated.
	 */
	public PaginatedList<E> backend( List<E> backend ) {
		this.backend = backend;
		this.updatePageCount();
		return this;
	}

	/**
	 * Returns how many pages there are.
	 *
	 * @return how many pages there are.
	 */
	public int pageCount() {
		return this.pageCount;
	}

	/**
	 * Returns how many items there are per page.
	 *
	 * @return how many items there are per page.
	 */
	public int perPage() {
		return this.perPage;
	}

	/**
	 * Sets how many items there are per page.
	 *
	 * @param perPage amount of items per page.
	 */
	public PaginatedList<E> perPage( final int perPage ) {
		this.perPage = perPage;
		this.updatePageCount();
		return this;
	}

	/**
	 * Returns the current page.
	 
	 * @return the current page
	 */
	public int currentPage() {
		return this.currentPage;
	}

	/**
	 * Sets the current page.
	 *
	 * @param currentPage the new current page.
	 */
	public PaginatedList<E> currentPage( int currentPage ) {
		this.currentPage = currentPage;
		return this;
	}
}