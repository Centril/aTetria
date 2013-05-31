package se.centril.atetria.framework.utils.worker;

/**
 * <p>Interface of an object that<br/>
 * can perform arbitrary {@link #work()}.<br/>
 * If Worker is working, {@link #isWorking()}<br/>
 * must return true.</p>
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 25 mar 2013
 * @version 1.0
 */
public interface Worker {
	/**
	 * Performs some kind of arbitrary work.
	 *
	 * @param arg details of work, could be null.
	 */
	public void work( Object arg );

	/**
	 * Returns true if the worker is currently working.
	 * 
	 * @return true if the worker is currently working.
	 */
	public boolean isWorking();
}