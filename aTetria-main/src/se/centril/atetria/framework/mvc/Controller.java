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
package se.centril.atetria.framework.mvc;

import com.badlogic.gdx.Application;

/**
 * Controller interface is the controller in MVC.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public interface Controller {
	/**
	 * Returns the current view.
	 *
	 * @return the current view.
	 */
	public View view();

	/**
	 * Brings this controller to front.
	 */
	public void toFront();

	/**
	 * Returns true if controller is front ("has focus").
	 *
	 * @return true if controller is front.
	 */
	public boolean isFront();

	/**
	 * Called when controller is brought to front.
	 */
	public void init();

	/**
	 * Called when controller is removed front front.
	 */
	public void killed();

	/**
	 * Called when controller is to be updated.
	 *
	 * @see com.badlogic.gdx.ApplicationListener#render()
	 */
	public void update();

	/**
	 * Called when the {@link Application} is resized. This can happen at any
	 * point during a non-paused state but will never happen before a call to
	 * {@link #create()}.
	 *
	 * @see com.badlogic.gdx.ApplicationListener#resize(int, int)
	 * @param width the new width in pixels
	 * @param height the new height in pixels
	 */
	public void resize( int width, int height );

	/**
	 * Called when the {@link Application} is paused.
	 *
	 * @see com.badlogic.gdx.ApplicationListener#pause()
	 */
	public void pause();

	/**
	 * Called when the {@link Application} is resumed from a paused state.
	 *
	 * @see com.badlogic.gdx.ApplicationListener#resume()
	 */
	public void resume();

	/**
	 * Called when the {@link Application} or controller is destroyed.
	 * Preceded by a call to {@link #pause()}.
	 *
	 * @see com.badlogic.gdx.ApplicationListener#dispose()
	 */
	public void dispose();
}