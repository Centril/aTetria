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

import java.util.Vector;

/**
 * Implementation of a worker delegating to other workers.
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 25 mar 2013
 * @version 1.0
 */
public class DelegatingWorker extends BaseWorker {
	/**
	 * The workers that perform actual work.<br/>
	 * Vector is motivated by non-nullary constraint & need for synchronization.
	 */
	private final Vector<Worker> workers;

	/**
	 * Constructs the delegating worker,<br/>
	 * with no initial workers to delegate to.
	 */
	public DelegatingWorker() {
		this.workers = new Vector<Worker>();
	}

	/**
	 * Adds a worker to list of workers to delegate to.<br/>
	 *
	 * @param worker worker to delegate to.
	 * @return delegating worker.
	 */
	public DelegatingWorker addWorker( Worker worker ) {
		this.workers.add( worker );
		return this;
	}

	/**
	 * Removes a worker from list of workers to delegate to.
	 *
	 * @param worker worker to no longer delegate to.
	 * @return true if worker was removed.
	 */
	public boolean removeWorker( Worker worker ) {
		return this.workers.remove( worker );
	}

	/**
	 * Returns true if the delegating worker is delegating to a given worker.
	 *
	 * @param worker the worker to check for.
	 * @return true if it is delegating to given worker.
	 */
	public boolean isDelegatingTo( Worker worker ) {
		return this.workers.contains( worker );
	}

	/**
	 * Returns the number of workers that we are delegating to.
	 *
	 * @return n(workers).
	 */
	public int getWorkerCount() {
		return this.workers.size();
	}

	/**
	 * Returns true if there are any workers to send work to.
	 *
	 * @return true if there's "anyone" to perform work.
	 */
	public boolean canWork() {
		return !this.workers.isEmpty();
	}

	/**
	 * Delegate to all workers.
	 *
	 * @param arg details of work, could be null.
	 */
	@Override
	protected void workImpl( Object arg ) {
		synchronized( this.workers ) {
			for ( Worker worker : this.workers ) {
				worker.work( arg );
			}
		}
	}
}