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
package se.centril.atetria.framework.gdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Logger;

/**
 * <p>GdxAppSource provides utility methods for a context<br/>
 * for dealing with libgdx in a non-singleton manner.</p>
 *
 * <p>Not every method in Application has a shortcut - only the most common do.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public interface GdxAppSource {
	/**
	 * Returns the front-controller of controller.
	 *
	 * @return the front-controller.
	 */
	public GdxFrontController front();

	/**
	 * Returns the Application object.
	 *
	 * @return the Application object.
	 */
	public Application app();

	/**
	 * Returns the Input object.
	 *
	 * @return the Input object.
	 */
	public Input input();

	/**
	 * Returns the Audio object.
	 *
	 * @return the Audio object.
	 */
	public Audio audio();

	/**
	 * Returns the Files object.
	 *
	 * @return the Files object.
	 */
	public Files files();

	/**
	 * Returns the Net object.
	 *
	 * @return the Net object.
	 */
	public Net net();

	/**
	 * Returns the Graphics object.
	 *
	 * @return the Graphics object.
	 */
	public Graphics graphics();

	/**
	 * Returns a Preferences object given a name.
	 *
	 * @return the Preferences object for given name.
	 */
	public Preferences preferences( String name );

	/**
	 * Returns the type of the Application.
	 *
	 * @return the type of the Application.
	 */
	public ApplicationType appType();

	/**
	 * Returns the central application logger.
	 *
	 * @return the logger.
	 */
	public Logger logger();
}