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

/**
 * Immutable implementation of Position.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class FinalPosition extends BasePosition {
	/**
	 * Constructs position with given x & y coordinates.
	 *
	 * @param x the x coordinate of new position.
	 * @param y the y coordinate of new position.
	 */
	public FinalPosition( int x, int y ) {
		super( x, y );
	}

	/**
	 * Constructs position copying values from another position.
	 *
	 * @param pos the position to copy from.
	 */
	public FinalPosition( Position pos ) {
		super( pos );
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public FinalPosition x( int x ) {
		return new FinalPosition( x, this.y() );
	}

	@Override
	public FinalPosition y( int y ) {
		return new FinalPosition( this.x(), y );
	}

	@Override
	public FinalPosition set( int x, int y ) {
		return new FinalPosition( x, y );
	}

	@Override
	public FinalPosition set( Position pos ) {
		return new FinalPosition( pos );
	}

	@Override
	public FinalPosition add( int x, int y ) {
		return new FinalPosition( this.x() + x, this.y() + y );
	}

	@Override
	public FinalPosition add( Position pos ) {
		return new FinalPosition( this.x() + pos.x(), this.y() + pos.y() );
	}

	@Override
	public FinalPosition addX( int x ) {
		return new FinalPosition( this.x() + x, this.y() );
	}

	@Override
	public FinalPosition addY( int y ) {
		return new FinalPosition( this.x(), this.y() + y );
	}

	@Override
	public FinalPosition sub( int x, int y ) {
		return new FinalPosition( this.x() - x, this.y() - y );
	}

	@Override
	public FinalPosition sub( Position pos ) {
		return new FinalPosition( this.x() - pos.x(), this.y() - pos.y() );
	}

	@Override
	public FinalPosition subX( int x ) {
		return new FinalPosition( this.x() - x, this.y() );
	}

	@Override
	public FinalPosition subY( int y ) {
		return new FinalPosition( this.x(), this.y() - y );
	}

	@Override
	public FinalPosition mul( int factor ) {
		return new FinalPosition( this.x() * factor, this.y() * factor );
	}

	@Override
	public FinalPosition cpy() {
		return new FinalPosition( this );
	}
}