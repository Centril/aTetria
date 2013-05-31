package se.centril.atetria.framework.geom;

/**
 * Immutable implementation of Position3.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class FinalPosition3 extends BasePosition3 {
	public FinalPosition3( int x, int y, int z ) {
		super( x, y, z );
	}

	public FinalPosition3( Position3 pos ) {
		super( pos );
	}

	public FinalPosition3( Position pos, int z ) {
		super( pos, z );
	}

	public FinalPosition3( Position pos ) {
		super( pos );
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public FinalPosition3 x( int x ) {
		return new FinalPosition3( x, this.y(), this.z() );
	}

	@Override
	public FinalPosition3 y( int y ) {
		return new FinalPosition3( this.x(), y, this.z() );
	}

	@Override
	public FinalPosition3 z( int z ) {
		return new FinalPosition3( this.x(), this.y(), z );
	}

	@Override
	public FinalPosition3 set( int x, int y, int z ) {
		return new FinalPosition3( this.x(), this.y(), this.z() );
	}

	@Override
	public FinalPosition3 add( int x, int y, int z ) {
		return new FinalPosition3( this.x() + x, this.y() + y, this.z() + z );
	}

	@Override
	public FinalPosition3 addX( int x ) {
		return new FinalPosition3( this.x() + x, this.y(), this.z() );
	}

	@Override
	public FinalPosition3 addY( int y ) {
		return new FinalPosition3( this.x(), this.y() + y, this.z() );
	}

	@Override
	public FinalPosition3 addZ( int z ) {
		return new FinalPosition3( this.x(), this.y(), this.z() + z );
	}

	@Override
	public FinalPosition3 sub( int x, int y, int z ) {
		return new FinalPosition3( this.x() - x, this.y() - y, this.z() - z );
	}

	@Override
	public FinalPosition3 subX( int x ) {
		return new FinalPosition3( this.x() - x, this.y(), this.z() );
	}

	@Override
	public FinalPosition3 subY( int y ) {
		return new FinalPosition3( this.x(), this.y() - y, this.z() );
	}

	@Override
	public FinalPosition3 subZ( int z ) {
		return new FinalPosition3( this.x(), this.y(), this.z() - z );
	}

	@Override
	public FinalPosition3 set( int x, int y ) {
		return new FinalPosition3( x, y, this.z() );
	}

	@Override
	public FinalPosition3 add( int x, int y ) {
		return new FinalPosition3( this.x() + x, this.y() + y, this.z() );
	}

	@Override
	public FinalPosition3 sub( int x, int y ) {
		return new FinalPosition3( this.x() - x, this.y() - y, this.z() );
	}

	@Override
	public FinalPosition3 set( Position pos ) {
		return new FinalPosition3( pos.x(), pos.y(), this.z() );
	}

	@Override
	public FinalPosition3 add( Position pos ) {
		return new FinalPosition3( this.x() + pos.x(), this.y() + pos.y(), this.z() );
	}

	@Override
	public FinalPosition3 sub( Position pos ) {
		return new FinalPosition3( this.x() - pos.x(), this.y() - pos.y(), this.z() );
	}

	@Override
	public FinalPosition3 mul( int factor ) {
		return new FinalPosition3( this.x() * factor, this.y() * factor, this.z() * factor );
	}

	@Override
	public FinalPosition3 cpy() {
		return new FinalPosition3( this );
	}

	@Override
	public FinalPosition3 set( Position3 pos ) {
		return new FinalPosition3( pos );
	}

	@Override
	public FinalPosition3 add( Position3 pos ) {
		return new FinalPosition3( this.x() + pos.x(), this.y() + pos.y(), this.z() + pos.z() );
	}

	@Override
	public FinalPosition3 sub( Position3 pos ) {
		return new FinalPosition3( this.x() - pos.x(), this.y() - pos.y(), this.z() - pos.z() );
	}
}