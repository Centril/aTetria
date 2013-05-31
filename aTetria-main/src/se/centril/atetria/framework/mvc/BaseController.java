package se.centril.atetria.framework.mvc;

/**
 * BaseController is the base class of controllers.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public abstract class BaseController implements Controller {
	private View view;

	private final FrontController frontCtrl;

	public BaseController( View view, FrontController frontCtrl ) {
		this( frontCtrl );
		this.view = view;
	}

	public BaseController( FrontController frontCtrl ) {
		this.frontCtrl = frontCtrl;
	}

	@Override
	public View view() {
		return this.view;
	}

	/**
	 * Sets the current view.
	 *
	 * @param view the current view to set.
	 * @return the controller.
	 */
	public BaseController view( View view ) {
		this.view = view;
		return this;
	}

	@Override
	public void toFront() {
		this.front().change( this );
	}

	@Override
	public boolean isFront() {
		return this.front().isControllerFront( this );
	}

	/**
	 * Returns the front-controller of controller.
	 *
	 * @return the front-controller.
	 */
	public FrontController front() {
		return this.frontCtrl;
	}

	@Override
	public void init() {
	}

	@Override
	public void killed() {
	}

	@Override
	public void resize( int width, int height ) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}