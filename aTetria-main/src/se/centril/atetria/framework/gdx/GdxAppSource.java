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