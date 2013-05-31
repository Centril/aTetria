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
 * <p>Position3 models a three-dimensional position using integers.<br/>
 * Whether or not it is immutable is up to the implementing class.</p>
 *
 * <p><strong>NOTE:</strong> Position3D is <strong>NOT</strong> a Vector,<br/>
 * but some operations common to vector classes are provided such as add, mul, etc.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 26, 2013
 */
public interface Position3 extends Position {
	/**
	 * Returns the z-axis-component of the coordinate.
	 *
	 * @return The z-axis-component of the coordinate.
	 */
	public int z();

	/**
	 * Sets the z-axis-component of the coordinate.
	 *
	 * @param z the new z value.
	 * @return the Position2D with the new z-axis-component.
	 */
	public Position3 z( int z );

	/**
	 * Alias of {@link #z()}.
	 *
	 * @see #z()
	 */
	public int getZ();

	/**
	 * Sets both the x- and y-axis-component of the coordinate.
	 *
	 * @param x the new x value.
	 * @param y the new x value.
	 * @param z the new z value.
	 * @return the Position3D with the new x, y & z-axis-component.
	 */
	public Position3 set( int x, int y, int z );

	/**
	 * Adds x, z & y to the x and y-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @param y the y value to add to y-axis-component.
	 * @param z the z value to add to z-axis-component.
	 * @return the Position3D with x, y & z added.
	 */
	public Position3 add( int x, int y, int z );

	/**
	 * Adds z to the z-axis-component of the coordinate.
	 *
	 * @param z the z value to add to x-axis-component.
	 * @return the Position3D with x added.
	 */
	public Position3 addZ( int z );

	/**
	 * Subtracts x, z & y to the x and y-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @param y the y value to add to y-axis-component.
	 * @return the Position3D with x, y, z subtracted.
	 */
	public Position3 sub( int x, int y, int z );

	/**
	 * Adds x to the x-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @return the Position2D with x added.
	 */
	public Position3 subZ( int x );

	/**
	 * Returns all values as an array: [{@link #x()}, {@link #y()}, {@link #z()}].
	 *
	 * @return all values as an array.
	 */
	public int[] values();

	/**
	 * Returns a string describing this position on the form "(x,y,z)".
	 *
	 * @return a string describing this position.
	 */
	public String toString();

	/* --------------------------------------------
	 * Changed type + Aliases:
	 * --------------------------------------------
	 */

	/**
	 * Alias of {@link #z(int)}.
	 *
	 * @see #z(int)
	 */
	public Position3 setZ( int z );

	public Position3 x( int x );
	public Position3 y( int y );

	public Position3 set( int x, int y );
	public Position3 setX( int x );
	public Position3 setY( int y );

	public Position3 add( int x, int y );
	public Position3 addX( int x );
	public Position3 addY( int y );

	public Position3 sub( int x, int y );
	public Position3 subX( int x );
	public Position3 subY( int y );

	public Position3 set( Position pos );
	public Position3 add( Position pos );
	public Position3 sub( Position pos );

	/**
	 * Adds the coordinates of another position object to this position.
	 *
	 * @param pos the position to use values from.
	 * @return the Position3D with the added x, y & z-values.
	 */
	public Position3 add( Position3 pos );

	/**
	 * Subtracts the coordinates of another position object to this position.
	 *
	 * @param pos the position to use values from.
	 * @return the Position3D with the subtracted x, y & z-values.
	 */
	public Position3 sub( Position3 pos );

	/**
	 * Copies the coordinate of another position object.
	 *
	 * @param pos the position to copy values from.
	 * @return the Position3D with the new x, y & z-axis-component.
	 */
	public Position3 set( Position3 pos );

	public Position3 mul( int factor );
	public Position3 distance( Position rhs );
	public Position3 distance( Position3 rhs );
	public Position3 scale( int factor );
	public Position3 move( Direction direction );
	public Position3 move( Direction direction, int scale );
	public Position3 cpy();
	public Position3 clone();

	/**
	 * The method <tt>compareTo</tt> first compares {@link #x()} of the<br/>
	 * position to decide if the given position is less or greater than this.<br/>
	 * If they have the same {@link #x()}, {@link #y()} decides, and so on...
	 * 
	 * @param	rhs The position to compare with.
	 * @return	An integer smaller than 0 if this position < rhs,<br/>
	 * 			0 position == rhs, and a positive integer otherwise.
	 */
	public int compareTo( Position3 rhs );

	public boolean contains( Position3 pos );

	public boolean containedIn( Position3 dim );
}