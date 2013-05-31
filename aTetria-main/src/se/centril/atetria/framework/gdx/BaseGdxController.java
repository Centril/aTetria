package se.centril.atetria.framework.gdx;

import se.centril.atetria.framework.mvc.BaseController;

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
 * BaseGdxController is the base class for controllers when dealing with libgdx.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public abstract class BaseGdxController extends BaseController implements GdxController {
	public BaseGdxController() {
		this( GdxFrontController.get() );
	}

	public BaseGdxController( GdxFrontController frontCtrl ) {
		super( frontCtrl );
	}

	public BaseGdxController( BaseGdxView view ) {
		this( view, GdxFrontController.get() );
	}

	public BaseGdxController( BaseGdxView view, GdxFrontController frontCtrl ) {
		super( view, frontCtrl );
	}

	@Override
	public GdxView view() {
		return (BaseGdxView) super.view();
	}

	public BaseGdxController view( BaseGdxView view ) {
		return (BaseGdxController) super.view( view );
	}

	/* --------------------------------------
	 * (Front)Controller + History related:
	 * --------------------------------------
	 */

	/**
	 * Instructs libgdx to catch the back key.
	 * @see GdxFrontController#catchBackKey()
	 */
	protected void catchBackKey() {
		this.front().catchBackKey();
	}

	@Override
	public GdxFrontController front() {
		return (GdxFrontController) super.front();
	}

	/* ------------------------------
	 * Application shortcuts/related:
	 * ------------------------------
	 */

	@Override
	public Application app() {
		return this.front().app();
	}

	@Override
	public Input input() {
		return this.app().getInput();
	}

	@Override
	public Audio audio() {
		return this.app().getAudio();
	}

	@Override
	public Files files() {
		return this.app().getFiles();
	}

	@Override
	public Net net() {
		return this.app().getNet();
	}

	@Override
	public Graphics graphics() {
		return this.app().getGraphics();
	}

	@Override
	public Preferences preferences( String name ) {
		return this.app().getPreferences( name );
	}

	@Override
	public ApplicationType appType() {
		return this.app().getType();
	}

	@Override
	public Logger logger() {
		return this.front().logger();
	}
}