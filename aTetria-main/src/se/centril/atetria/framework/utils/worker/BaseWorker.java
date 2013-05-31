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