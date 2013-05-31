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
package se.centril.atetria.model;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import se.centril.atetria.framework.geom.FinalPosition;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.model.command.RotationCommand;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Piece represents a single piece in the game.
 *
 * <p>The following piece-types exist in order:
 * Line, L, L-Mirrored, Z, S, Square, T.</p>
 *
 * <p>It is not possible to directly construct a piece.
 * Instead all users must utilize {@link Piece#getPieces()}
 * which returns a list of predefined Pieces with all
 * rotations pre-computed and linked as a chain.<br />
 * Rotations are accessible with either:
 * {@link #nextRotation()} or {@link #prevRotation()}</p>
 *
 * <p>The basic make-up of a piece is a set of points
 * (a tetromino uses 4 of them).</p>
 *
 * <p>Each piece has a width and height
 * measured in blocks calculated at construction.</p>
 *
 * <p>Finally, it has a skirt which is defined as:
 * For each x value across the piece,
 * the skirt gives the lowest y value in the body.
 * This useful for computing where the piece will land.</p>
 */
public final class Piece {
	/** The preferred number of position/block elements used. */
	public final static int MAX_POSITION_COUNT = 4;

	/** The Tetromino type of piece. */
	private final Tetromino type;

	/** The body of the piece containing all positions of the component blocks that it is made of. */
	private final FinalPosition[] body;

	/**
	 * For each x value across the piece,
	 * the skirt gives the lowest y value in the body.
	 */
	private final int[] skirt;

	/** The dimensions of the piece. */
	private final FinalPosition dim;

	/** The next rotation in line (CCW, 90 deg left). */
	private Piece next;

	/** The previous rotation in line (CW, 90 deg right) */
	private Piece prev;

	/** Stores the maximum orientation value. */
	private MaxOrientation maxOrientation;

	/**
	 * Stores the maximum orientation value
	 * (number of times a piece can be rotated).
	 *
	 * The max orientation value can't be higher than 4
	 * due to 4 being the number of quadrants in unit-circle.
	 */
	private final class MaxOrientation {
		/** Holds the value */
		private int max;

		/**
		 * Returns the max orientation value.
		 *
		 * @return The max orientation value.
		 */
		public int max() {
			return this.max;
		}

		/**
		 * Sets the max orientation value.
		 *
		 * @param max The max orientation value to set.
		 */
		public void max( final int max ) {
			this.max = max;
		}
	}

	/* --------------------------------
	 * Constructors.
	 * --------------------------------
	 */

	/**
	 * Constructs a Piece given the tetromino it represents.
	 *
	 * @param tetromino the tetromino.
	 */
	public Piece( Tetromino tetromino ) {
		this( tetromino, tetromino.getBody(), tetromino != Tetromino.SQUARE );
	}

	/**
	 * Constructs a Piece.
	 * linkRotations must only be true for first only.
	 *
	 * @param body The points of this piece.
	 * @param constructRotations Whether or not to construct rotations.
	 */
	protected Piece( final Tetromino tetromino, final FinalPosition[] body, final boolean constructRotations ) {
		this.type = tetromino;
		this.body = body;

		{
			// Sort in first-hand on x, second-hand y.
			Arrays.sort( this.body );

			// Min & Max y-values used for later computing of height.
			int maxY = Integer.MIN_VALUE,
				minY = Integer.MAX_VALUE;

			List<Integer> skirt = Lists.newArrayListWithCapacity( MAX_POSITION_COUNT );

			for ( Position pos : this.body ) {
				// Set new max-y if needed.
				maxY = Math.max( maxY, pos.y() );

				// Since sorted: size() - 1 < x <=> first occurrence of x => we're at minY(x) = skirt(x).
				if ( skirt.size() - 1 < pos.x() ) {
					// Set min-y for all x.
					minY = Math.min( minY, pos.y() );

					// Set skirt(x).
					skirt.add( pos.y() );
				}
			}

			// Save skirt. toArray doesn't work.
			this.skirt = new int[skirt.size()];
			for ( int x = 0; x < skirt.size(); x++ ) {
				this.skirt[x] = skirt.get( x );
			}

			/*
			 * Compute width & height
			 * Since body is sorted in first-hand on x the first & last always has min/max-X.
			 * This procedure however does not work for y since it is sorted on y in second-hand.
			 */
			this.dim = new FinalPosition(
				this.computeLength( this.body[0].getX(), this.body[this.body.length - 1].getX() ),
				this.computeLength( minY, maxY )
			);
		}

		if ( constructRotations ) {
			this.linkRotations( this );
		} else {
			this.next = this.prev = this;
		}
	}

	/* ---------------------------------
	 * Dimensions & Body & misc-getters.
	 * ---------------------------------
	 */

	/**
	 * Given a min & max value, computes & returns a length (e.g: height, width).
	 *
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @return The length.
	 */
	protected int computeLength( final int min, final int max ) {
		return 1 + Math.abs( min - max );
	}

	/**
	 * Returns the dimensions of the piece.
	 *
	 * @return the dimensions/size.
	 */
	public FinalPosition getSize() {
		return this.dim;
	}

	/**
	 * Returns the width of the piece measured in blocks.
	 *
	 * @return Width of the piece.
	 */
	public int getWidth() {
		return this.dim.x();
	}

	/**
	 * Returns the height of the piece measured in blocks.
	 *
	 * @return Height of the peace.
	 */
	public int getHeight() {
		return this.dim.y();
	}

	/**
	 * Returns a reference to the piece:s skirt.
	 * For each x value across the piece,
	 * the skirt gives the lowest y value in the body.
	 * This useful for computing where the piece will land.
	 *
	 * In other words: list of minY(x) for all x.
	 *
	 * Do not modify the contents of the returned array.
	 *
	 * @return Piece:s skirt.
	 */
	public int[] getSkirt() {
		return this.skirt;
	}

	/**
	 * Returns a reference to the piece:s body.
	 * Do not modify the contents of the returned array.
	 *
	 * @return Piece:s body.
	 */
	public Position[] getBody() {
		return this.body;
	}

	public Position[] getFixedBody( Position start, Position bounds ) {
		Position[] fixedBody = new FinalPosition[this.body.length];

		for ( int i = 0; i < this.body.length; i++ ) {
			fixedBody[i] = this.body[i].add( start );

			if ( !bounds.contains( fixedBody[i] ) ) {
				return null;
			}
		}

		return fixedBody;
	}

	public Position[] getFixedBody( final int x, final int y, Position bounds ) {
		return this.getFixedBody( new FinalPosition( x, y ), bounds );
	}

	/**
	 * Returns the Tetromino type of this Piece.
	 *
	 * @return The Tetromino type.
	 */
	public Tetromino getType() {
		return this.type;
	}

	/* --------------------------------
	 * Rotation access.
	 * --------------------------------
	 */

	/**
	 * Returns a piece that is CCW (90 degrees left) rotated from receiver.
	 *
	 * <p>Implementation:
	 * The class pre-computes all the rotations once.
	 * This method just hops from one pre-computed rotation
	 * to the next in O(1) constant time.</p>
	 */
	public Piece nextRotation() {
		return this.next;
	}

	/**
	 * Returns a piece that is CW (90 degrees right) rotated from receiver.
	 *
	 * <p>Implementation:
	 * The class pre-computes all the rotations once.
	 * This method just hops from one pre-computed rotation
	 * to the prev in O(1) constant time.</p>
	 */
	public Piece prevRotation() {
		return this.prev;
	}

	/**
	 * Returns a piece that is rotated according to direction.
	 *
	 * <p>Implementation:
	 * The class pre-computes all the rotations once.
	 * This method just hops from one pre-computed rotation
	 * to the next in O(1) constant time.</p>
	 *
	 * @param direction the direction to rotate in.
	 * @return the rotated piece.
	 */
	public Piece rotate( RotationCommand direction ) {
		switch( direction ) {
		case CLOCKWISE:
			return this.prevRotation();

		case COUNTER_CLOCKWISE:
			return this.nextRotation();

		default:
			throw new AssertionError( "Should not happen" );
		}
	}

	/**
	 * Returns the maximum orientation value,
	 * AKA the number of unique rotations a piece has.
	 *
	 * @return Maxium rotation value for piece.
	 */
	public int getMaxOrientation() {
		/*
		 * Internal note: This method is not to be called before getPieces()
		 * has been called for its first time.
		 */
		return this.maxOrientation.max();
	}

	/**
	 * Returns an iterator over the rotations for
	 * this piece starting from this piece.
	 * CCW rotation is used.
	 *
	 * <p>This implementation relies on {@link #getMaxOrientation()}
	 * for retrieving size, the rest is pretty much
	 * like the implementation for a LinkedList.</p>
	 *
	 * <p>The {@code remove} method is not implemented and will
	 * respond by throwing {@link UnsupportedOperationException} if used</p>
	 *
	 * @return An iterator over the CCW rotations for this piece starting from this piece.
	 */
	public Iterator<Piece> iterator() {
		return new Itr();
	}

	/**
	 * Returns an iterator over the rotations for
	 * this piece starting from this piece.
	 * CW rotation is used.
	 *
	 * @see #iterator()
	 *
	 * @return An iterator over the CW rotations for this piece starting from this piece.
	 */
	public Iterator<Piece> descendingIterator() {
		return new BackwardItr();
	}

	private class BackwardItr extends Itr implements Iterator<Piece> {
		protected Piece selectNextElem() {
			return this.next.prev;
		}
	}

	private class Itr implements Iterator<Piece> {
		/** Index of element to be returned by subsequent call to next. */
		int nextIndex = 0;

		/** Stores the next element in line. */
		Piece next = Piece.this;

		/**
		 * Implementation of next rotation retrieval
		 * goes here. CCW as standard.
		 *
		 * @return The next rotation.
		 */
		protected Piece selectNextElem() {
			return this.next.next;
		}

		/**
		 * Checks if there's a next rotation.
		 *
		 * @return true if there's a next rotation, else false.
		 */
		public final boolean hasNext() {
			return this.nextIndex < getMaxOrientation();
		}

		/**
		 * Retrieves the next rotation.
		 *
		 * @return The next rotation.
		 */
		public final Piece next() {
			if ( !this.hasNext() ) {
				throw new NoSuchElementException();
			}

			Piece curr = this.next;
			this.next = this.selectNextElem();
			nextIndex++;
			return curr;
		}

		/** Removing is not supported. */
		public final void remove() { throw new UnsupportedOperationException(); }
	}

	/* --------------------------------
	 * Rotation computing/linking.
	 * --------------------------------
	 */

	/**
	 * Adds rotations to the first rotation
	 * next-chain & prev-chain in line.
	 *
	 * @param first The first piece to link from.
	 */
	protected Piece linkRotations( Piece first ) {
		Piece current = first;

		// Make a reference max orientation value holder.
		first.maxOrientation = first.new MaxOrientation();

		/*
		 * Counter i is used as safety against infinite loop.
		 * The constant 4 comes from 4 quadrants in unit-circle,
		 * it can never be more than 4. Hard-coding is legitimate.
		 */
		for ( int i = 0; ; i++ ) {
			Piece next = this.makeRotation( current );
			next.maxOrientation = first.maxOrientation;

			// first == next -> no more rotations -> current refers back to first & vice versa.
			if ( i == 3 || first.equals( next ) ) {
				current.next = first;
				first.prev = current;

				// Set the max orientation value.
				first.maxOrientation.max( i + 1 );

				break;
			}

			current.next = next;
			next.prev = current;
			current = next;
		}

		return first;
	}

	/**
	 * Returns CCW rotation ( 90 deg left ) from piece.
	 *
	 * @param origin The origin piece to rotate from.
	 * @return The new rotated piece.
	 */
	protected Piece makeRotation( final Piece origin ) {
		return new Piece( this.type, this.computeRotationBody( origin ), false );
	}

	/**
	 * Computes and returns CCW rotation body ( 90 degrees left ) from piece.
	 *
	 * @param origin The origin piece to compute from.
	 * @return The body of rotated piece.
	 */
	protected FinalPosition[] computeRotationBody( final Piece origin ) {
		/*
		 * Runs in O(n) linear time where n = n positions.
		 * Height is subtracted by 1 so as to never have
		 * an empty line in x-axis or y-axis at start.
		 * Otherwise it would have spun CCW around the piece:s center.
		 */
		FinalPosition[] rotatedBody = new FinalPosition[origin.body.length];
		for ( int i = 0; i < origin.body.length; i++ ) {
			rotatedBody[i] = new FinalPosition( origin.getHeight() - 1 - origin.body[i].y(), origin.body[i].x() );
		}

		return rotatedBody;
	}

	/* --------------------------------
	 * Equality related.
	 * --------------------------------
	 */

	/**
	 * Returns true if two pieces are the same which,
	 * that is: their bodies contain the same points.
	 *
	 * @return true if they are considered equal, false otherwise.
	 */
	@Override
	public final boolean equals( Object obj ) {
		// We know that it is sorted, so equals works, otherwise we must use containsAll(...)
		return this == obj || obj instanceof Piece && this.body.equals( ((Piece) obj).body );
		//return this == obj || obj instanceof Piece && Arrays.asList( this.body ).containsAll( Arrays.asList( ((Piece) obj).body ) );
	}

	/**
	 * Uses hashCode() of each Position in {@link #getBody()}
	 * as a component to compute hashCode.
	 *
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		return Objects.hashCode( (Object) this.body );
	}
}