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