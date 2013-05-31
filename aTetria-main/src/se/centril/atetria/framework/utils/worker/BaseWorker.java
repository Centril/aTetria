package se.centril.atetria.framework.utils.worker;

/**
 * An abstract/base implementation of Worker.
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 25 mar 2013
 * @version 1.0
 */
public abstract class BaseWorker implements Worker {
	/** Whether the worker is working or not. */
	private boolean isWorking = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isWorking() {
		return this.isWorking;
	}

	/**
	 * {@inheritDoc}
	 */
	public void work( Object arg ) {
		this.setWorking( true );

		this.workImpl( arg );

		this.setWorking( false );
	}

	/**
	 * Implementation of work.
	 *
	 * @see #work(Object)
	 * @param arg
	 */
	protected abstract void workImpl( Object arg );

	/**
	 * Sets whether or not worker is working.<br/>
	 * Visibility should not be changed.
	 *
	 * @param state true if worker is working, otherwise false.
	 */
	private void setWorking( boolean state ) {
		this.isWorking = state;
	}
}