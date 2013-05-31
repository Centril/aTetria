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
 * Mutable implementation of Position3.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class MutablePosition3 extends BasePosition3 {
	public MutablePosition3( int x, int y, int z ) {
		super( x, y, z );
	}

	public MutablePosition3( Position3 pos ) {
		super( pos );
	}

	public MutablePosition3( Position pos, int z ) {
		super( pos, z );
	}

	public MutablePosition3( Position pos ) {
		super( pos );
	}

	@Override
	public boolean isMutable() {
		return true;
	}

	@Override
	public MutablePosition3 x( int x ) {
		this.x = x;
		return this;
	}

	@Override
	public MutablePosition3 y( int y ) {
		this.y = y;
		return this;
	}

	@Override
	public MutablePosition3 z( int z ) {
		this.z = z;
		return this;
	}

	@Override
	public MutablePosition3 set( int x, int y, int z ) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public MutablePosition3 add( int x, int y, int z ) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public MutablePosition3 addX( int x ) {
		this.x += x;
		return this;
	}

	@Override
	public MutablePosition3 addY( int y ) {
		this.y += y;
		return this;
	}

	@Override
	public MutablePosition3 addZ( int z ) {
		this.z = z;
		return this;
	}

	@Override
	public MutablePosition3 sub( int x, int y, int z ) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	@Override
	public MutablePosition3 subX( int x ) {
		this.x -= x;
		return this;
	}

	@Override
	public MutablePosition3 subY( int y ) {
		this.y -= y;
		return this;
	}

	@Override
	public MutablePosition3 subZ( int x ) {
		this.z -= z;
		return this;
	}

	@Override
	public MutablePosition3 set( int x, int y ) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public MutablePosition3 add( int x, int y ) {
		this.x += x;
		this.y += y;
		return this;
	}

	@Override
	public MutablePosition3 sub( int x, int y ) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	@Override
	public MutablePosition3 set( Position pos ) {
		this.x = pos.x();
		this.y = pos.y();
		return this;
	}

	@Override
	public MutablePosition3 add( Position pos ) {
		this.x += pos.x();
		this.y += pos.y();
		return this;
	}

	@Override
	public MutablePosition3 sub( Position pos ) {
		this.x -= pos.x();
		this.y -= pos.y();
		return this;
	}

	@Override
	public MutablePosition3 set( Position3 pos ) {
		this.x = pos.x();
		this.y = pos.y();
		this.z = pos.z();
		return this;
	}

	@Override
	public MutablePosition3 add( Position3 pos ) {
		this.x += pos.x();
		this.y += pos.y();
		this.z += pos.z();
		return this;
	}

	@Override
	public MutablePosition3 sub( Position3 pos ) {
		this.x -= pos.x();
		this.y -= pos.y();
		this.z -= pos.z();
		return this;
	}

	@Override
	public MutablePosition3 mul( int factor ) {
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	}

	@Override
	public MutablePosition3 cpy() {
		return new MutablePosition3( this );
	}
}