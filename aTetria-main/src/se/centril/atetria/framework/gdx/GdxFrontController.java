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

import se.centril.atetria.framework.mvc.FrontController;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Logger;
import com.google.common.base.Preconditions;

/**
 * FrontController is somewhat a Front Controller. 
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public abstract class GdxFrontController extends FrontController implements ApplicationListener {
	private final Application app;

	private Logger logger;

	private boolean logFps = false;

	/**
	 * Static getter.
	 *
	 * @return the current GdxFrontController.
	 */
	public static GdxFrontController get() {
		return (GdxFrontController) Gdx.app.getApplicationListener();
	}

	/**
	 * Constructs the front controller.
	 */
	public GdxFrontController() {
		this.app = Gdx.app;
		this.logger( new Logger( "none", Logger.ERROR ) );
	}

	/* ------------------------------
	 * Application shortcuts/related:
	 * ------------------------------
	 */

	/**
	 * Returns the Application object.
	 *
	 * @return the Application object.
	 */
	public Application app() {
		return this.app;
	}

	/* --------------------------------------
	 * History related:
	 * --------------------------------------
	 */

	/**
	 * {@inheritDoc}<br/>
	 * Back key will not be catched.
	 */
	public void clearHistory() {
		super.clearHistory();
		this.app().getInput().setCatchBackKey( false );
	}

	/**
	 * {@inheritDoc}<br/>
	 * If {@link #hasHistory()} is false afterwards, back key will not be catched.
	 */
	public void back( int amount ) {
		super.back( amount );

		if ( !this.hasHistory() ) {
			this.app().getInput().setCatchBackKey( false );
		}
	}

	/**
	 * Instructs libgdx to catch the back key.<br/>
	 * If {@link #hasHistory()} == false nothing happens.
	 */
	public void catchBackKey() {
		if ( this.hasHistory() ) {
			this.app().getInput().setCatchBackKey( true );
		}
	}

	/* ------------------------------
	 * Controller lifecycle related:
	 * ------------------------------
	 */

	/**
	 * Sets the logger used.
	 *
	 * @param logger the logger.
	 */
	public void logger( Logger logger ) {
		this.logger = Preconditions.checkNotNull( logger );
	}

	/**
	 * Returns the logger used.
	 *
	 * @return the logger used.
	 */
	public Logger logger() {
		return this.logger;
	}

	/**
	 * Sets whether or not to log FPS.
	 *
	 * @param flag true if FPS should be logged.
	 */
	public void logFps( boolean flag ) {
		this.logFps = flag;
	}

	/**
	 * Logs the current FPS.
	 */
	protected void logFPS() {
		if ( this.logFps ) {
			this.logger().debug( "FPS: " + Gdx.graphics.getFramesPerSecond() );
		}
	}

	@Override
	public void render() {
		this.logFPS();

		super.render();
	}
}