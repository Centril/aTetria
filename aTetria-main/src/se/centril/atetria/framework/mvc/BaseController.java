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