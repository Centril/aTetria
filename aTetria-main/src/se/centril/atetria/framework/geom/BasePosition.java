package se.centril.atetria.framework.geom;

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
		return val >= 0 && val <= max;
	}

	public Position clone() {
		return this.cpy();
	}

	public String toString() {
		return "(" + this.x() + "," + this.y() + ")";
	}
}