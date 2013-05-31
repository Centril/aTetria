package se.centril.atetria.framework.geom;

/**
 * <p>Position models a two-dimensional position using integers.<br/>
 * Whether or not it is immutable is up to the implementing class.</p>
 *
 * <p><strong>NOTE:</strong> Position2D is <strong>NOT</strong> a Vector,<br/>
 * but some operations common to vector classes are provided such as add, mul, etc.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 26, 2013
 */
public interface Position extends Cloneable, Comparable<Position> {
	/**
	 * Whether or not the implementation has mutable or immutable "setters".
	 *
	 * @return true if mutable.
	 */
	public boolean isMutable();

	/**
	 * Returns the x-axis-component of the coordinate.
	 *
	 * @return The x-axis-component of the coordinate.
	 */
	public int x();

	/**
	 * Returns the y-axis-component of the coordinate.
	 *
	 * @return The y-axis-component of the coordinate.
	 */
	public int y();

	/**
	 * Alias of {@link #x()}.
	 *
	 * @see #x()
	 */
	public int getX();

	/**
	 * Alias of {@link #y()}.
	 *
	 * @see #y()
	 */
	public int getY();

	/**
	 * Sets the x-axis-component of the coordinate.
	 *
	 * @param x the new x value.
	 * @return the Position2D with the new x-axis-component.
	 */
	public Position x( int x );

	/**
	 * Sets the y-axis-component of the coordinate.
	 *
	 * @param y the new x value.
	 * @return the Position2D with the new y-axis-component.
	 */
	public Position y( int y );

	/**
	 * Sets both the x- and y-axis-component of the coordinate.
	 *
	 * @param x the new x value.
	 * @param y the new x value.
	 * @return the Position2D with the new x & y-axis-component.
	 */
	public Position set( int x, int y );

	/**
	 * Copies the coordinate of another position object.
	 *
	 * @param pos the position to copy values from.
	 * @return the Position2D with the new x & y-axis-component.
	 */
	public Position set( Position pos );

	/**
	 * Alias of {@link #x(int)}.
	 *
	 * @see #x(int)
	 */
	public Position setX( int x );

	/**
	 * Alias of {@link #y(int)}.
	 *
	 * @see #y(int)
	 */
	public Position setY( int y );

	/**
	 * Adds x & y to the x and y-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @param y the y value to add to y-axis-component.
	 * @return the Position2D with x & y added.
	 */
	public Position add( int x, int y );

	/**
	 * Adds the coordinates of another position object to this position.
	 *
	 * @param pos the position to use values from.
	 * @return the Position2D with the added x & y-values.
	 */
	public Position add( Position pos );

	/**
	 * Adds x to the x-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @return the Position2D with x added.
	 */
	public Position addX( int x );

	/**
	 * Adds y to the y-axis-component of the coordinate.
	 *
	 * @param y the y value to add to y-axis-component.
	 * @return the Position2D with y added.
	 */
	public Position addY( int y );

	/**
	 * Subtracts x & y to the x and y-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @param y the y value to add to y-axis-component.
	 * @return the Position2D with x & y subtracted.
	 */
	public Position sub( int x, int y );

	/**
	 * Subtracts the coordinates of another position object to this position.
	 *
	 * @param pos the position to use values from.
	 * @return the Position2D with the subtracted x & y-values.
	 */
	public Position sub( Position pos );

	/**
	 * Subtracts x to the x-axis-component of the coordinate.
	 *
	 * @param x the x value to add to x-axis-component.
	 * @return the Position2D with x added.
	 */
	public Position subX( int x );

	/**
	 * Subtracts y to the y-axis-component of the coordinate.
	 *
	 * @param y the y value to add to y-axis-component.
	 * @return the Position2D with y added.
	 */
	public Position subY( int y );

	/**
	 * Multiplies the coordinates of this position by factor.
	 *
	 * @param factor the factor to multiply with.
	 * @return the Position2D with coordinates multiplied with factor.
	 */
	public Position mul( int factor );

	/**
	 * Alias of {@link #sub(Position)}.
	 *
	 * @see #sub(Position2D).
	 */
	public Position distance( Position rhs );

	/**
	 * Alias of {@link #mul(int)}.
	 *
	 * @see #mul(int)
	 */
	public Position scale( int factor );

	/**
	 * Moves the position to direction.<br/>
	 * This has the same effect as calling {@link #move(Direction, int)} with 1 as scale.
	 * 
	 * @param direction the direction to move to.
	 * @return the Position2D with moved coordinates.
	 */
	public Position move( Direction direction );

	/**
	 * Moves the position to direction by scale amount.
	 * 
	 * @param dir the direction to move to.
	 * @param scale the scale to move by.
	 * @return the Position2D with moved coordinates.
	 */
	public Position move( Direction direction, int scale );

	/**
	 * Returns all values as an array: [{@link #x()}, {@link #y()}].
	 *
	 * @return all values as an array.
	 */
	public int[] values();

	/**
	 * Clones this position.
	 *
	 * @return the new position.
	 */
	public Position cpy();

	/**
	 * The method <tt>compareTo</tt> first compares {@link #x()} of the<br/>
	 * position to decide if the given position is less or greater than this.<br/>
	 * If they have the same {@link #x()}, {@link #y()} decides.
	 * 
	 * @param	rhs The position to compare with.
	 * @return	An integer smaller than 0 if this position < rhs,<br/>
	 * 			0 position == rhs, and a positive integer otherwise.
	 */
	public int compareTo( Position rhs );

	/**
	 * Semantically treats this as a dimension rather than a position.<br/>
	 * Returns true if pos is contained in this.
	 *
	 * @param pos the position object.
	 * @return true if this position is contained in dim.
	 */
	public boolean contains( Position pos );

	/**
	 * Semantically treats dim as a dimension rather than a position.<br/>
	 * Returns true if this position is contained in given dim.
	 *
	 * @param dim the dimension object.
	 * @return true if this position is contained in given dim.
	 */
	public boolean containedIn( Position dim );

	/**
	 * Returns a string describing this position on the form "(x,y)".
	 *
	 * @return a string describing this position.
	 */
	public String toString();
}