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
 * Enumeration representing a direction.
 * Has EAST, SOUTHEAST, WEST, SOUTHWEST, NORTHWEST, NORTH, SOUTH, NORTHEAST and NONE directions.
 */
public enum Direction {
	NONE	( 0, 0 ) {
		public Direction getOpposite() {
			return NONE;
		}
	},
	WEST	(-1, 0) {
		public Direction getOpposite() {
			return EAST;
		}
	},
	NORTH_WEST	(-1, -1) {
		public Direction getOpposite() {
			return SOUTH_EAST;
		}
	},
	NORTH	(0, -1) {
		public Direction getOpposite() {
			return SOUTH;
		}
	},
	NORTH_EAST	(1, -1) {
		public Direction getOpposite() {
			return SOUTH_WEST;
		}
	},
	EAST	(1, 0) {
		public Direction getOpposite() {
			return WEST;
		}
	},
	SOUTH_EAST	(1, 1) {
		public Direction getOpposite() {
			return NORTH_WEST;
		}
	},
	SOUTH	(0, 1) {
		public Direction getOpposite() {
			return NORTH;
		}
	},
	SOUTH_WEST	(-1, 1) {
		public Direction getOpposite() {
			return NORTH_EAST;
		}
	};

	public static final Direction[] DEG_90 = { Direction.WEST, Direction.NORTH, Direction.EAST, Direction.SOUTH };
	public static final Direction[] DEG_45 = { Direction.NORTH_WEST, Direction.NORTH_EAST, Direction.SOUTH_EAST, Direction.SOUTH_WEST };

	/**
	 * Returns all directions that make up all quadrants circle starting from {@link #WEST}.
	 *
	 * @return the directions.
	 */
	public static Direction[] deg90() {
		return DEG_90;
	}

	/**
	 * Returns all directions that make up all quadrants circle starting from {@link #NORTHWEST}.
	 *
	 * @return the directions.
	 */
	public static Direction[] deg45() {
		return DEG_45;
	}

	/**
	 * Tests if this direction is opposite to another one.
	 *
	 * @param rhs right-hand-side direction.
	 * @return true if it is opposite.
	 */
	public boolean isOpposite( Direction rhs ) {
		return this.getOpposite() == rhs;
	}

	/**
	 * Returns the opposite direction of this one.
	 *
	 * @return The opposite direction.
	 */
	public Direction getOpposite() {
		return this;
	}

	/**
	 * Returns the delta that this direction implies.
	 *
	 * @return the offset.
	 */
	public FinalPosition delta() {
		return this.delta;
	}

	/**
	 * Returns the delta-x that this direction implies.
	 *
	 * @return the delta-x.
	 */
	public int dx() {
		return this.delta().x();
	}

	/**
	 * Returns the delta-y that this direction implies.
	 *
	 * @return the delta-y.
	 */
	public int dy() {
		return this.delta().y();
	}

	/**
	 * "Constructor" taking delta in x-axis & y-axis.
	 *
	 * @param dx delta-x.
	 * @param dx delta-y.
	 */
	Direction(final int dx, final int dy ) {
		this.delta = new FinalPosition( dx, dy );
	}

	private final FinalPosition delta;
}