package se.centril.atetria.framework.geom;

public abstract class BasePosition3 extends BasePosition implements Position3 {
	protected int z;

	public BasePosition3( int x, int y, int z ) {
		super( x, y );
		this.z = z;
	}

	public BasePosition3( Position3 pos ) {
		this( pos.x(), pos.y(), pos.z() );
	}

	public BasePosition3( Position pos, int z ) {
		this( pos.x(), pos.y(), z );
	}

	public BasePosition3( Position pos ) {
		this( pos, 0 );
	}

	@Override
	public int z() {
		return this.z;
	}

	@Override
	public int getZ() {
		return this.z;
	}

	@Override
	public Position3 setX( int x ) {
		return this.x( x );
	}

	@Override
	public Position3 setY( int y ) {
		return this.y( y );
	}

	@Override
	public Position3 setZ( int z ) {
		return this.z( z );
	}

	@Override
	public Position3 distance( Position rhs ) {
		return this.sub( rhs );
	}

	@Override
	public Position3 distance( Position3 rhs ) {
		return this.sub( rhs );
	}

	@Override
	public Position3 scale( int factor ) {
		return this.mul( factor );
	}

	@Override
	public Position3 move( Direction direction ) {
		return (Position3) super.move( direction );
	}

	@Override
	public Position3 move( Direction direction, int scale ) {
		return (Position3) super.move( direction, scale );
	}

	@Override
	public int[] values() {
		return new int[] { this.x, this.y, this.z };
	}

	public int compareTo( Position3 rhs ) {
		int d2 = super.compareTo( rhs );
		return d2 == 0 ? this.z() - rhs.z() : d2;
	}

	public Position3 clone() {
		return this.cpy();
	}

	@Override
	public boolean contains( Position pos ) {
		return pos.containedIn( this );
	}

	public boolean contains( Position3 pos ) {
		return pos.containedIn( this );
	}

	public boolean containedIn( Position3 dim ) {
		return super.containedIn( dim ) && this.inRange( this.z(), dim.z() );
	}

	public String toString() {
		return "(" + this.x() + "," + this.y() + "," + this.z() + ")";
	}
}