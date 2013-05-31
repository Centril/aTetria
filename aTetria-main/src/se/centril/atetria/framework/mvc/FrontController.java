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

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

/**
 * <p>FrontController is a special type of Controller,<br/>
 * that delegates to another controller and has concept of history.<br/>
 * FrontController may be root, or leaf.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 31, 2013
 */
public abstract class FrontController implements Screen {
	protected final FrontController frontCtrl;

	/** The current controller. */
	protected Controller ctrl;

	/** History "stack" of controllers. Last is most recent. */
	protected List<Controller> history = Lists.newArrayList();

	/** true = identity is used instead of equality when checking if controller is same. */
	private boolean useIdentity;

	/* --------------------------------------
	 * Traversal/Hierarchy related:
	 * --------------------------------------
	 */

	/**
	 * Constructs FrontController as root level.
	 */
	public FrontController() {
		this( null );
	}

	/**
	 * Constructs FrontController as non-root level.
	 *
	 * @param ctrl the owner of this FrontController.
	 */
	public FrontController( FrontController ctrl ) {
		this.frontCtrl = ctrl;
	}

	/**
	 * Returns true if this FrontController has no FrontController above it.
	 *
	 * @return true if FrontController is top/root.
	 */
	public boolean isRoot() {
		return this.frontCtrl == null;
	}

	/**
	 * Returns true if this FrontController is the last-in-line FrontController before the real controllers.
	 *
	 * @return true if FrontController is leaf.
	 */
	public boolean isLeaf() {
		return !(this.ctrl instanceof FrontController);
	}

	/**
	 * Returns the level of this FrontController in chain.<br/>
	 * 0 => {@link #isRoot()} == true.
	 *
	 * @return the level.
	 */
	public int level() {
		int level = 0;
		FrontController ctrl = this;

		while ( ctrl.isLeaf() ) {
			ctrl = ctrl.frontCtrl;
			level++;
		}

		return level;
	}

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
	@Override
	public void init() {
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

	/**
	 * true = identity is used instead of equality when checking if controller is the same/front.
	 *
	 * @param flag whether or not to use identity.
	 */
	public void useIdentity( boolean flag ) {
		this.useIdentity = flag;
	}

	/**
	 * If true, identity is used instead of equality when checking if controller is same/front.
	 *
	 * @return whether or not identity is used.
	 */
	public boolean isUsingIdentity() {
		return this.useIdentity;
	}

	/**
	 * Checks if controller is the current one/front.
	 *
	 * @param ctrl the controller to test for.
	 * @return true if front/current.
	 */
	public boolean isControllerFront( Controller ctrl ) {
		return this.useIdentity ? this.ctrl == ctrl : this.ctrl.equals( ctrl );
	}

	/**
	 * Changes the controller.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void change( Controller ctrl ) {
		if ( this.isControllerFront( ctrl ) ) {
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
		if ( this.isControllerFront( ctrl ) ) {
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
		this.ctrl.pause();
		this.ctrl.killed();
		this.ctrl.dispose();
	}

	/**
	 * Changes controller. Clears the history before.
	 *
	 * @param ctrl the controller to change to.
	 */
	public void changeClear( Controller ctrl ) {
		if ( this.isControllerFront( ctrl ) ) {
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
		if ( this.isControllerFront( ctrl ) ) {
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
			throw new IndexOutOfBoundsException( "There's no stored history for " + amount + " of controllers back in time." );
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
		this.init();

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
		this.preRender();

		this.ctrl.view().render();

		this.postRender();
	}

	/**
	 * Called first in {@link #render()}.
	 */
	@Override
	public void update() {
		this.ctrl.update();
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
		this.ctrl.killed();
		this.ctrl.dispose();
		this.ctrl = null;
	}

	@Override
	public View view() {
		return this.ctrl.view();
	}

	@Override
	public void toFront() {
		if ( this.isLeaf() ) {
			this.frontCtrl.change( this );
		}
	}

	@Override
	public boolean isFront() {
		return this.isRoot() ? true : this.frontCtrl.isControllerFront( this );
	}

	@Override
	public void killed() {
	}
}