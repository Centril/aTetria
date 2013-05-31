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
package se.centril.atetria.framework.geom;

import com.badlogic.gdx.math.Vector2;

/**
 * Insets is an immutable collection of insets for: top, right, bottom, left.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 26 apr 2013
 * @version 1.0
 */
public class Insets implements Cloneable {
	/** Stores the values. */
	private final float[] values;

	/**
	 * Copy constructor.
	 *
	 * @param insets the insets object to copy from.
	 */
	public Insets( Insets insets ) {
		this( insets.top(), insets.right(), insets.bottom(), insets.left() );
	}

	/**
	 * Constructs a new Insets object given a topRight vector & bottomLeft vector:<br/>
	 * this is the equivalent of: <code>new Insets( topRight.getY(), topRight.getX(), bottomLeft.getY(), bottomLeft.getX() ).</code>
	 *
	 * @param topRight top-right vector.
	 * @param bottomLeft bottom-left vector.
	 */
	public Insets( Vector2 topRight, Vector2 bottomLeft ) {
		this( topRight.y, topRight.x, bottomLeft.y, bottomLeft.x );
	}

	/**
	 * Constructs a new Insets object given a value for all insets.
	 *
	 * @param all the top, right, bottom, left insets - all in one.
	 */
	public Insets( float all ) {
		this( all, all, all, all );
	}

	/**
	 * Constructs a new Insets object given vertical, horizontal insets.
	 *
	 * @param vertical the vertical insets (top & bottom).
	 * @param horizontal the horizontal insets (right & left).
	 */
	public Insets( float vertical, float horizontal ) {
		this( vertical, horizontal, vertical, horizontal );
	}

	/**
	 * Constructs a new Insets object given top, right, bottom, left insets.
	 *
	 * @param top top inset.
	 * @param right right inset.
	 * @param bottom bottom inset.
	 * @param left left inset.
	 */
	public Insets( float top, float right, float bottom, float left ) {
		this.values = new float[] { top, right, bottom, left };
		for ( float val : this.values ) {
			if ( val < 0 ) {
				throw new IllegalArgumentException( "Inset can't be negative" );
			}
		}
	}

	/**
	 * Makes & returns a new Insets object given top, right, bottom, left insets.
	 * 
	 * @param top top inset.
	 * @param right right inset.
	 * @param bottom bottom inset.
	 * @param left left inset.
	 * @return
	 */
	public static Insets make( float top, float right, float bottom, float left ) {
		return new Insets( top, right, bottom, left );
	}

	/**
	 * Returns the top inset.
	 *
	 * @return the top inset.
	 */
	public float top() {
		return this.values[0];
	}

	/**
	 * Returns the right inset.
	 *
	 * @return the right inset.
	 */
	public float right() {
		return this.values[1];
	}

	/**
	 * Returns the bottom inset.
	 *
	 * @return the bottom inset.
	 */
	public float bottom() {
		return this.values[2];
	}

	/**
	 * Returns the left inset.
	 *
	 * @return the left inset.
	 */
	public float left() {
		return this.values[3];
	}

	/**
	 * Returns the dimensions of insets object.
	 *
	 * @return the dimensions of insets object.
	 */
	public Vector2 dimension() {
		return new Vector2( this.left() + this.right(), this.top() + this.bottom() );
	}

	/**
	 * Returns the area that insets claims.
	 *
	 * @return the area of insets.
	 */
	public float area() {
		return (this.top() + this.bottom()) * (this.left() + this.right());
	}

	/**
	 * Computes and returns the circumference of insets object.
	 *
	 * @return the circumference.
	 */
	public float circumference() {
		float circumference = 0;
		for ( float side : this.values ) {
			circumference += side;
		}
		return circumference;
	}

	/**
	 * Returns an "immutable" array of all the inset values.
	 *
	 * @return an array of all the inset values.
	 */
	public float[] getAll() {
		return this.values.clone();
	}

	@Override
	public Insets clone() {
		return new Insets( this );
	}
}