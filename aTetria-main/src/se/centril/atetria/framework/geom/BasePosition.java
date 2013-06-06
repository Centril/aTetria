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

import java.util.Objects;

/**
 * Position is a two-dimensional position class.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 26, 2013
 */
public abstract class BasePosition implements Position  {
	protected int x, y;

	/**
	 * Constructs position with given x & y coordinates.
	 *
	 * @param x the x coordinate of new position.
	 * @param y the y coordinate of new position.
	 */
	public BasePosition( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructs position copying values from another position.
	 *
	 * @param pos the position to copy from.
	 */
	public BasePosition( Position pos ) {
		this( pos.x(), pos.y() );
	}

	@Override
	public int x() {
		return this.x;
	}

	@Override
	public int y() {
		return this.y;
	}
	
	@Override
	public int getX() {
		return this.x();
	}

	@Override
	public int getY() {
		return this.y();
	}

	@Override
	public Position setX( int x ) {
		return this.x( x );
	}

	@Override
	public Position setY( int y ) {
		return this.y( y );
	}

	@Override
	public Position distance( Position rhs ) {
		return this.sub( rhs );
	}

	@Override
	public Position scale( int factor ) {
		return this.mul( factor );
	}

	@Override
	public Position move( Direction direction ) {
		return this.add( direction.delta() );
	}

	@Override
	public Position move( Direction direction, int scale ) {
		return this.add( direction.delta().mul( scale ) );
	}

	@Override
	public int[] values() {
		return new int[] { this.x, this.y };
	}

	public int compareTo( Position rhs ) {
		int dx = this.x() - rhs.x();
		return dx == 0 ? this.y() - rhs.y() : dx;
	}

    @Override
	public boolean contains( Position pos ) {
    	return pos.containedIn( this );
	}

	@Override
	public boolean containedIn( Position dim ) {
		return this.inRange( this.x(), dim.x() ) && this.inRange( this.y(), dim.y() );
	}

	protected boolean inRange( int val, int max ) {
		return val >= 0 && val < max;
	}

	public Position clone() {
		return this.cpy();
	}

	public String toString() {
		return "(" + this.x() + "," + this.y() + ")";
	}

	public boolean equals( Object obj ) {
		if ( this == obj ) {
			return true;
		}

		if ( obj.getClass() == this.getClass() ) {
			Position rhs = (Position) obj;
			return this.x() == rhs.x() && this.y() == rhs.y();
		}

		return false;
	}

	public int hashCode() {
		return Objects.hash( this.x(), this.y() );
	}
}