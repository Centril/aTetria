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
package se.centril.atetria;

import se.centril.atetria.controller.GameController;
import se.centril.atetria.framework.gdx.GdxFrontController;
import se.centril.atetria.framework.mvc.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.utils.Logger;

public class ATetriaGame extends GdxFrontController {
	private static final String TITLE = "ATetria";
	private static final int LOG_LEVEL = Logger.DEBUG;


	public ATetriaGame() {
		super();
	}

	@Override
	public Controller getInitController() {
		return new GameController();
	}

	@Override
	public void init() {
		// Set logging.
		this.app().setLogLevel( LOG_LEVEL );
		this.logger( new Logger( TITLE, LOG_LEVEL ) );

		Gdx.graphics.setTitle( TITLE );

		// Fine tuning.
		Gdx.graphics.setVSync( true );
	}

	@Override
	public void preRender() {
		GLCommon gl = this.app().getGraphics().getGLCommon();
		gl.glClearColor( 1, 1, 1, 1 );
		gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
	}

	@Override
	public void postRender() {
	}
}