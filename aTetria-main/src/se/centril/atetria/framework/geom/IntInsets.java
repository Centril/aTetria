package se.centril.atetria.framework.geom;

import com.badlogic.gdx.math.Vector2;

/**
 * InsetsNatural is an immutable collection of insets for: top, right, bottom, left.<br/>
 * It uses int as value type instead of double.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 26 apr 2013
 * @version 1.0
 */
public class IntInsets implements Cloneable {
	/** Stores the values. */
	private final int[] values;

	/**
	 * Copy constructor.
	 *
	 * @param insets the insets object to copy from.
	 */
	public IntInsets( IntInsets insets ) {
		this( insets.top(), insets.right(), insets.bottom(), insets.left() );
	}

	/**
	 * "Copy" constructor - casts all values to ints.
	 *
	 * @param insets the insets object to copy from.
	 */
	public IntInsets( Insets insets ) {
		this( (int) insets.top(), (int) insets.right(), (int) insets.bottom(), (int) insets.left() );
	}

	/**
	 * Constructs a new Insets object given a topRight vector & bottomLeft vector:<br/>
	 * this is the equivalent of: <code>new Insets( topRight.getY(), topRight.getX(), bottomLeft.getY(), bottomLeft.getX() ).</code><br/>
	 * All values are casted to ints.
	 *
	 * @param topRight top-right vector.
	 * @param bottomLeft bottom-left vector.
	 */
	public IntInsets( Vector2 topRight, Vector2 bottomLeft ) {
		this( (int) topRight.y, (int) topRight.x, (int) bottomLeft.y, (int) bottomLeft.x );
	}

	/**
	 * Constructs a new Insets object given a topRight "position" & bottomLeft "position":<br/>
	 * this is the equivalent of: <code>new Insets( topRight.y(), topRight.x(), bottomLeft.y(), bottomLeft.x() ).</code>
	 *
	 * @param topRight top-right "position".
	 * @param bottomLeft bottom-left "position".
	 */
	public IntInsets( Position topRight, Position bottomLeft ) {
		this( topRight.y(), topRight.x(), bottomLeft.y(), bottomLeft.x() );
	}

	/**
	 * Constructs a new Insets object given a value for all insets.
	 *
	 * @param all the top, right, bottom, left insets - all in one.
	 */
	public IntInsets( int all ) {
		this( all, all, all, all );
	}

	/**
	 * Constructs a new Insets object given vertical, horizontal insets.
	 *
	 * @param vertical the vertical insets (top & bottom).
	 * @param horizontal the horizontal insets (right & left).
	 */
	public IntInsets( int vertical, int horizontal ) {
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
	public IntInsets( int top, int right, int bottom, int left ) {
		this.values = new int[] { top, right, bottom, left };
		for ( int val : this.values ) {
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
	public static IntInsets make( int top, int right, int bottom, int left ) {
		return new IntInsets( top, right, bottom, left );
	}

	/**
	 * Returns the "double-version" of insets object.
	 *
	 * @return the "double-version" of insets object. 
	 */
	public Insets asDouble() {
		return new Insets( this.top(), this.right(), this.bottom(), this.left() );
	}

	/**
	 * Returns the top inset.
	 *
	 * @return the top inset.
	 */
	public int top() {
		return this.values[0];
	}

	/**
	 * Returns the right inset.
	 *
	 * @return the right inset.
	 */
	public int right() {
		return this.values[1];
	}

	/**
	 * Returns the bottom inset.
	 *
	 * @return the bottom inset.
	 */
	public int bottom() {
		return this.values[2];
	}

	/**
	 * Returns the left inset.
	 *
	 * @return the left inset.
	 */
	public int left() {
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
	public int area() {
		return (this.top() + this.bottom()) * (this.left() + this.right());
	}

	/**
	 * Computes and returns the circumference of insets object.
	 *
	 * @return the circumference.
	 */
	public int circumference() {
		int circumference = 0;
		for ( int side : this.values ) {
			circumference += side;
		}
		return circumference;
	}

	/**
	 * Returns an "immutable" array of all the inset values.
	 *
	 * @return an array of all the inset values.
	 */
	public int[] getAll() {
		return this.values.clone();
	}

	@Override
	public IntInsets clone() {
		return new IntInsets( this );
	}
}