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