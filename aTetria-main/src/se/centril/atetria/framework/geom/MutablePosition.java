package se.centril.atetria.framework.geom;

/**
 * Mutable implementation of Position.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class MutablePosition extends BasePosition {
	/**
	 * Constructs position with given x & y coordinates.
	 *
	 * @param x the x coordinate of new position.
	 * @param y the y coordinate of new position.
	 */
	public MutablePosition( int x, int y ) {
		super( x, y );
	}

	/**
	 * Constructs position copying values from another position.
	 *
	 * @param pos the position to copy from.
	 */
	public MutablePosition( Position pos ) {
		super( pos );
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public Position x( int x ) {
		this.x = x;
		return this;
	}

	@Override
	public Position y( int y ) {
		this.y = y;
		return this;
	}

	@Override
	public Position set( int x, int y ) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public Position set( Position pos ) {
		this.x = pos.x();
		this.y = pos.y();
		return this;
	}

	@Override
	public Position add( int x, int y ) {
		this.x += x;
		this.y += y;
		return this;
	}

	@Override
	public Position add( Position pos ) {
		this.x += pos.x();
		this.y += pos.y();
		return this;
	}

	@Override
	public Position addX( int x ) {
		this.x += x;
		return this;
	}

	@Override
	public Position addY( int y ) {
		this.y = y;
		return this;
	}

	@Override
	public Position sub( int x, int y ) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	@Override
	public Position sub( Position pos ) {
		this.x -= pos.x();
		this.y -= pos.y();
		return this;
	}

	@Override
	public Position subX( int x ) {
		this.x -= x;
		return this;
	}

	@Override
	public Position subY( int y ) {
		this.y -= y;
		return this;
	}

	@Override
	public Position mul( int factor ) {
		this.x *= factor;
		this.y *= factor;
		return this;
	}

	@Override
	public Position cpy() {
		return new MutablePosition( this );
	}
}