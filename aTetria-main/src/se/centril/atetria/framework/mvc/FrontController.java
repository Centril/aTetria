package se.centril.atetria.framework.mvc;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class FrontController {
	/** The current controller. */
	protected Controller ctrl;

	/** History "stack" of controllers. Last is most recent. */
	protected List<Controller> history = Lists.newArrayList();

	/* --------------------------------------
	 * Hooks related:
	 * --------------------------------------
	 */

	/**
	 * Hook: Bootstraps the front controller.<br/>
	 *
	 * Anything that needs to be done before<br/>
	 * {@link #initController(Controller)} is called must be done here.
	 */
	public void bootstrap() {
	}

	/**
	 * Hook: Called before {@link View#render()} is called.
	 */
	public void preRender() {
	}

	/**
	 * Hook: Called after {@link View#render()} is called.
	 */
	public void postRender() {
	}

	/* --------------------------------------
	 * Controller switching/creation related:
	 * --------------------------------------
	 */

	/**
	 * Hook: Used to fetch the initial controller.
	 *
	 * @return the initial controller.
	 */
	public abstract Controller getInitController();

	public boolean isControllerFront( Controller ctrl ) {
		return this.ctrl == ctrl;
	}

	/**
	 * Changes the controller.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void change( Controller ctrl ) {
		if ( this.ctrl == ctrl ) {
			return;
		}

		this.ctrl.killed();

		this.history.add( this.ctrl );

		this.initController( ctrl );
	}

	/**
	 * Changes the controller and kills -> pauses -> disposes the previous one.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void changeDispose( Controller ctrl ) {
		if ( this.ctrl == ctrl ) {
			return;
		}

		this.disposeCtrl();

		this.history.add( this.ctrl );

		this.initController( ctrl );
	}

	/**
	 * Initializes + uses a controller.
	 *
	 * @param ctrl the controller to initialize & use.
	 */
	protected void initController( Controller ctrl ) {
		this.ctrl = Preconditions.checkNotNull( ctrl );
		this.ctrl.init();
		this.ctrl.resume();
	}

	/**
	 * Disposes controller in use.
	 */
	protected void disposeCtrl() {
		this.ctrl.killed();
		this.ctrl.pause();
		this.ctrl.dispose();
	}

	/**
	 * Changes controller. Clears the history before.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void changeClear( Controller ctrl ) {
		if ( this.ctrl == ctrl ) {
			return;
		}

		this.ctrl.killed();

		this.clearHistory();

		this.initController( ctrl );
	}

	/**
	 * Changes controller and  kills -> pauses -> disposes the previous one. Clears the history before.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void changeClearDispose( Controller ctrl ) {
		if ( this.ctrl == ctrl ) {
			return;
		}

		this.disposeCtrl();
	
		this.clearHistory();

		this.initController( ctrl );
	}

	/* --------------------------------------
	 * History related:
	 * --------------------------------------
	 */

	/**
	 * Goes back 1 controller in stored history.<br/>
	 * Equivalent of {@link #back(int)} using 0.
	 */
	public void back() {
		this.back( 0 );
	}

	/**
	 * Goes back amount of controllers in stored history.
	 *
	 * @param amount the amount of controllers to go back.
	 * @throws IndexOutOfBoundsException if history does not exist.
	 */
	public void back( int amount ) {
		int index = this.backIndex( amount );

		if ( index < 0 ) {
			throw new IndexOutOfBoundsException( "The given history is is stored." );
		}

		// Forget everything more recent than wanted index.
		if ( index < this.history.size() - 1 ) {
			this.history.subList( index + 1, this.history.size() ).clear();
		}

		Controller to = this.history.remove( index );

		this.ctrl.killed();
		this.initController( to );
	}

	/**
	 * Clears any stored history.
	 */
	public void clearHistory() {
		this.history.clear();
	}

	/**
	 * Checks if there's stored history.
	 * 
	 *
	 * @return true if there's stored history.
	 */
	public boolean hasHistory() {
		return !this.history.isEmpty();
	}

	/**
	 * Checks if there's stored history for amount of controllers back in "time".
	 *
	 * @param amount the amount of controllers of stored history to check for.
	 * @return true if there is.
	 */
	public boolean hasHistory( int amount ) {
		return this.backIndex( amount ) >= 0;
	}

	/**
	 * Returns the amount of stored history there is.
	 *
	 * @return the amount of stored history there is.
	 */
	public int historySize() {
		return this.history.size();
	}

	/**
	 * Returns how "long ago" given controller was in use.<br/>
	 * Time is not actual time in any sense.<br/>
	 * If controller has not been in use, -1 is returned.
	 *
	 * @param ctrl the controller.
	 * @return 
	 */
	public int timeSince( Controller ctrl ) {
		int index = this.history.indexOf( ctrl );
		return index < 0 ? -1 : this.backIndex( index );
	}

	/**
	 * Returns true if given controller exists in history.
	 *
	 * @param ctrl the controller to test for.
	 * @return true if in history.
	 */
	public boolean inHistory( Controller ctrl ) {
		return this.history.contains( ctrl );
	}

	/**
	 * Returns the back-index for amount of stored controllers.
	 *
	 * @param amount the amount.
	 * @return the index.
	 */
	protected int backIndex( int amount ) {
		return this.history.size() - 1 - amount;
	}

	/* ------------------------------
	 * Controller lifecycle related:
	 * ------------------------------
	 */

	/**
	 * Should be called after FrontController is constructed.
	 */
	public void create() {
		this.bootstrap();

		this.initController( this.getInitController() );
	}

	/**
	 * Should be called when resize happens.
	 *
	 * @param width the new width.
	 * @param height the new height.
	 */
	public void resize( int width, int height ) {
		this.ctrl.resize( width, height );
	}

	/**
	 * Should be called when rendering is needed.
	 */
	public void render() {
		this.ctrl.update();

		this.preRender();

		this.ctrl.view().render();

		this.postRender();
	}

	/**
	 * Should be called when controller should be paused.
	 */
	public void pause() {
		this.ctrl.pause();
	}

	/**
	 * Should be called when controller should be resumed.
	 */
	public void resume() {
		this.ctrl.resume();
	}

	/**
	 * Should be called when controller should be disposed.
	 */
	public void dispose() {
		this.ctrl.dispose();
		this.ctrl = null;
	}
}