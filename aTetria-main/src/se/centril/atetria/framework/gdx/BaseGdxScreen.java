package se.centril.atetria.framework.gdx;

/**
 * BaseGdxScreen is a union of BaseGdxController & GdxView.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public abstract class BaseGdxScreen extends BaseGdxController implements GdxScreen {
	public BaseGdxScreen() {
		super();
		this.view( this );
	}

	public BaseGdxScreen( GdxFrontController frontCtrl ) {
		super( frontCtrl );
		this.view( this );
	}
}