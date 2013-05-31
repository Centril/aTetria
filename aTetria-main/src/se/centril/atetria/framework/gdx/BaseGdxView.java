package se.centril.atetria.framework.gdx;

import se.centril.atetria.framework.mvc.View;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;

/**
 * BaseGdxView is the base class for views when dealing with libgdx.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public abstract class BaseGdxView implements View, GdxView {
	private GdxFrontController frontCtrl;

	public BaseGdxView() {
		this( GdxFrontController.get() );
	}

	public BaseGdxView( GdxFrontController frontListener ) {
		this.frontCtrl = frontListener;
	}

	@Override
	public GdxFrontController front() {
		return this.frontCtrl;
	}

	/* ------------------------------
	 * Application shortcuts/related:
	 * ------------------------------
	 */

	public Application app() {
		return this.front().app();
	}

	public Input input() {
		return this.app().getInput();
	}

	public Audio audio() {
		return this.app().getAudio();
	}

	public Files files() {
		return this.app().getFiles();
	}

	public Net net() {
		return this.app().getNet();
	}

	public Graphics graphics() {
		return this.app().getGraphics();
	}

	public Preferences preferences( String name ) {
		return this.app().getPreferences( name );
	}

	public ApplicationType appType() {
		return this.app().getType();
	}

	@Override
	public Logger logger() {
		return this.front().logger();
	}
}