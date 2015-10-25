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

import java.util.List;

import se.centril.atetria.framework.geom.FinalPosition;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.model.segmenter.Segment;
import se.centril.atetria.model.segmenter.StickySegmentFinder;

/**
 * Board represents the grid-board of the game.
 *
 * <p>The grid-board is implemented as a matrix of boolean states
 * which can be either {@link #FILLED} or {@link #EMPTY}
 * The state of each element can be accessed with either
 * with a Position by {@link #getState(Position)} or
 * with x, y coordinates by {@link #getState(int, int)}.</p>
 *
 * <p>The board is constructed with a width & height
 * which also decides how many elements there are in the matrix.
 * The with and height can be retrieved with
 * {@link #getWidth()} and {@link #getHeight()}</p>
 * 
 * <p>At all times, committed version of the grid is kept,
 * so even if bad modifications are made, they can be
 * reverted with {@link #undo()} to last committed state.
 * When {@link #commit()} is called reverting back is impossible.</p>
 *
 * <p>A placement in the board can be made
 * with {@link #place(Piece, Position)}
 * and clearing of filled rows can be made with:
 * {@link #clearRows()}</p>
 */
public final class Board {
	public static boolean DEBUG = true;

	/** Indicates that a state in the {@link #grid} is empty (null). */
	public final static Piece EMPTY = null;

	/** Stores the state of each position on grid of board. */
	private Piece[][] grid;
	private int[] widths;
	private int[] heights;
	private int maxHeight;

	/** Holds a deep copy of grid used for undoing/reverting back to committed state. */
	private Piece[][] gridCopy;
	private int[] widthsCopy;
	private int[] heightsCopy;
	private int maxHeightCopy;

	/** The dimensions of board. */
	private final FinalPosition dim;

	private final int topSpace;

	private ClearMode clearMode = ClearMode.STANDARD;

	/**
	 * Constructs a Board with given width & height.
	 *
	 * @param width The width of board.
	 * @param height The height of board.
	 */
	public Board( final int width, final int height, final int topSpace ) {
		this.dim = new FinalPosition( width, height );
		this.topSpace = topSpace;
		this.initGrid();
	}

	/**
	 * Constructs a Board with given dimensions.
	 *
	 * @param dim the dimensions of board.
	 */
	public Board( Position dim, final int topSpace ) {
		this.dim = new FinalPosition( dim );
		this.topSpace = topSpace;
	}

	/**
	 * Returns true if the board has overflowed in height (game-over condition).
	 *
	 * @return true if the board has overflowed.
	 */
	public boolean hasOverflow() {
		return this.getMaxHeight() > this.getHeight() - this.getTopSpace();
	}

	/**
	 * Returns the space reserved for the top.
	 *
	 * @return the reserved top space.
	 */
	public int getTopSpace() {
		return this.topSpace;
	}

	/**
	 * Initializes the grid.<br/>
	 * Constructor helper.
	 */
	private void initGrid() {
		this.grid = this.initializedGrid();
		this.gridCopy = null;

		this.widths = new int[this.getHeight()];
		this.widthsCopy = null;

		this.heights = new int[this.getWidth()];
		this.heightsCopy = null;

		this.maxHeight = 0;
		this.maxHeightCopy = 0;
	}

	/**
	 * Returns the width of board.
	 *
	 * @return The width of board.
	 */
	public int getWidth() {
		return this.dim.x();
	}

	/**
	 * Returns the height of board.
	 *
	 * @return The height of board.
	 */
	public int getHeight() {
		return this.dim.y();
	}

	/**
	 * Returns dimensions of board.
	 *
	 * @return The boards dimensions.
	 */
	public FinalPosition getSize() {
		return this.dim;
	}

	/**
	 * Gets the state of a position on board.<br/>
	 * Valid states are {@link Tetromino} or {@link #EMPTY} (null).
	 *
	 * @param pos Position to get state of.
	 * @return Retrieved state.
	 */
	public final Piece getState( final Position pos ) {
		return this.getState( pos.x(), pos.y() );
	}

	/**
	 * Gets the state of a position on board.<br/>
	 * Valid states are {@link Tetromino} or {@link #EMPTY} (null).
	 *
	 * @param x x-axis-component of position.
	 * @param y y-axis-component of position.
	 * @return Retrieved state.
	 */
	public final Piece getState( final int x, final int y ) {
		return this.grid[x][y];
	}

	/**
	 * Checks if a state is filled.
	 *
	 * @param pos Position to get state of.
	 * @return true if state is filled, false otherwise.
	 */
	public final boolean isFilled( final Position pos ) {
		return this.isFilled( pos.x(), pos.y() );
	}

	/**
	 * Checks if a state is filled.
	 *
	 * @param x x-axis-component of position.
	 * @param y y-axis-component of position.
	 * @return true if state is filled, false otherwise.
	 */
	public final boolean isFilled( final int x, final int y ) {
		return this.getState( x, y ) != EMPTY;
	}

	/**
	 * Sets the state of a position on board to a given state.
	 *
	 * @param state The state to set position to.
	 * @param pos The position to set.
	 */
	private final void setState( Piece state, final Position pos ) {
		this.setState( state, pos.x(), pos.y() );
	}

	/**
	 * Sets the state of a position on board to a given state.
	 *
	 * @param state The state to set position to.
	 * @param x x-coordinate of the position to set.
	 * @param y y-coordinate of the position to set.
	 */
	private final void setState( Piece state, final int x, final int y ) {
		this.grid[x][y] = state;
	}

	/** An enumeration of all success-states a placement can yield. */
	public enum PlacementState {
		OK, ROW_FILLED, OUT_OF_BOUNDS, BAD;

		/**
		 * Returns if the placement failed.
		 *
		 * @return true = failure, false = success.
		 */
		public boolean isFailed() {
			return this == OUT_OF_BOUNDS || this == BAD;
		}

		/**
		 * Returns if placement filled a row.
		 * 
		 * @return True if ROW_FILLED, otherwise false.
		 */
		public boolean isRowFilled() {
			return this == ROW_FILLED;
		}
	}

	/**
	 * <p>Attempts to add the body of a piece to the board.<br/>
	 * Copies the piece blocks into the board grid.<br/>
	 * Returns {@link PlacementState#OK} for a regular placement, or {@link PlacementState#ROW_FILLED}<br/>
	 * for a regular placement that causes at least one row to be filled.</p>
	 *
	 * <p>Error cases:
	 * If part of the piece would fall out of bounds, the placement<br/>
	 * does not change the board at all, and {@link PlacementState#OUT_OF_BOUNDS} is returned.<br/>
	 * If the placement is "bad" --interfering with existing blocks in the grid --<br/>
	 * then the placement is halted partially complete and {@link PlacementState#BAD} is returned.<br/>
	 * An {@link #undo()} will remove the bad placement.</p>
	 *
	 * @param piece The piece to place.
	 * @param Start position of placement.
	 */
	public PlacementState place( final Piece piece, final int x, final int y ) {
		return this.place( piece, new FinalPosition( x, y ) );
	}

	/**
	 * <p>Attempts to add the body of a piece to the board.<br/>
	 * Copies the piece blocks into the board grid.<br/>
	 * Returns {@link PlacementState#OK} for a regular placement, or {@link PlacementState#ROW_FILLED}<br/>
	 * for a regular placement that causes at least one row to be filled.</p>
	 *
	 * <p>Error cases:
	 * If part of the piece would fall out of bounds, the placement<br/>
	 * does not change the board at all, and {@link PlacementState#OUT_OF_BOUNDS} is returned.<br/>
	 * If the placement is "bad" --interfering with existing blocks in the grid --<br/>
	 * then the placement is halted partially complete and {@link PlacementState#BAD} is returned.<br/>
	 * An {@link #undo()} will remove the bad placement.</p>
	 *
	 * @param piece The piece to place.
	 * @param x x-axis component of start position of placement.
	 * @param y y-axis component of start position of placement.
	 */
	public PlacementState place( final Piece piece, final Position startPos ) {
		/*
		 * 1) Do a check of all calculated/fixated positions
		 * to see if they are out of bounds, if they are quit early.
		 * While doing this we also save these computed positions
		 * to a list that can be used directly after.
		 */
		Position[] fixedPos = piece.getFixedBody( startPos, this.getSize() );

		if ( fixedPos == null ) {
			return PlacementState.OUT_OF_BOUNDS;
		}

		/*
		 * To avoid point of no return we save
		 * a copy of grid to revert back to.
		 */
		this.copy();

		/*
		 * Now we can safely do operations on grid
		 * and fall back on gridCopy.
		 */
		PlacementState currState = PlacementState.OK;

		// Place all computed positions.
		for ( final Position pos : fixedPos ) {
			if ( this.isFilled( pos ) ) {
				// Placement is bad, partially complete.
				currState = PlacementState.BAD;
				break;
			}

			this.setState( piece, pos );
			this.updateMax( pos );

			if ( this.canFillRow( pos.y() ) ) {
				// Placement results in a filled row, yay!
				currState = PlacementState.ROW_FILLED;
			}
		}

		if ( currState != PlacementState.BAD ) {
			this.sanityCheck();
		}

		return currState;
	}

	/**
	 * Updates max heights & widths n stuff when a position gets filled.
	 *
	 * @param pos the position that will be filled.
	 */
	private void updateMax( Position pos ) {
		this.updateMax( pos.x(), pos.y() );
	}

	/**
	 * Updates max heights & widths n stuff when a position gets filled.
	 *
	 * @param x the x that will be filled.
	 * @param y the y that will be filled.
	 */
	private void updateMax( int x, int y ) {
		this.widths[y] += 1;

		if ( ++y > this.heights[x] ) {
			this.heights[x] = this.tryMaxHeight( y );
		}
	}

	/**
	 * Tries a new max height if more than current max height.
	 *
	 * @param height the new height max to try.
	 */
	private int tryMaxHeight( int height ) {
		if ( height > this.maxHeight ) {
			this.maxHeight = height;
		}

		return height;
	}

	/**
	 * Returns an initialized to {@link #EMPTY} grid.
	 *
	 * @return The initialized grid.
	 */
	private Piece[][] initializedGrid() {
		return new Piece[this.getWidth()][this.getHeight()];
	}

	/**
	 * Saves a copy of grid.
	 */
	private void copy() {
		this.gridCopy = this.initializedGrid();
		for ( int i = 0; i < this.getWidth(); i++ ) {
			this.gridCopy[i] = this.grid[i].clone();
		}

		this.widthsCopy = new int[this.widths.length];
		this.heightsCopy = new int[this.heights.length];

		this.copyArr( this.widths, this.widthsCopy );
		this.copyArr( this.heights, this.heightsCopy );

		this.maxHeightCopy = this.maxHeight;
	}

	/**
	 * Does a copy from src to dest.
	 *
	 * @param src source array.
	 * @param dest destination array.
	 */
	private void copyArr( int[] src, int[] dest ) {
		System.arraycopy( src, 0, dest, 0, src.length );
	}

	/**
	 * Can row given by y-coordinate be filled?
	 *
	 * @param y the row no. (y-coordinate)
	 * @return true if it can be cleared, false otherwise.
	 */
	private boolean canFillRow( final int y ) {
		return this.getRowWidth( y ) == this.getWidth();
	}

	/**
	 * Deletes rows that are filled all the
	 * way across, moving things above down.
	 *
	 * @return Amount of rows cleared, 0 if none.
	 */
	public int clearRows() {
		int filledRows = 0;

		switch ( this.clearMode ) {
		case STANDARD:
			filledRows = this.clearStandard();
			break;

		case CASCADE:
			this.clearSticky();
			break;

		default:
			throw new AssertionError( "ShouldNotHappenException" );
		}

		this.sanityCheck();

		return filledRows;
	}

	private int clearSticky() {
		int filledRows = 0;

		// First pass: clear all lines.
		for ( int y = 0; y < this.getHeight(); y++ ) {
			if ( this.canFillRow( y ) ) {
				++filledRows;
				for ( int x = 0; x < this.getWidth(); x++ ) {
					this.setState( EMPTY, x, y );
				}
			}
		}

		if ( filledRows == 0 ) {
			return 0;
		}

		// Second pass: clump to segments.
		List<Segment> segments = new StickySegmentFinder( this ).find();

		return filledRows;
	}

	private int clearCascade() {
		/*
		 * Complexity: O(height).
		 * - The row was filled: increment steps to move down/filled-rows.
		 * - If we've steps to move down & we didn't find a filled row this
		 * 		time we can move down n(filledRows) rows,
		 * 		swap copy row y to y - filledRows & clear y.
		 */
		int filledRows = 0;
		for ( int y = 0; y < this.getHeight(); y++ ) {
			if ( this.canFillRow( y ) ) {
				++filledRows;
			} else if ( filledRows > 0 ) {
				int dest = y - filledRows;
				this.widths[dest] = this.widths[y];
				this.widths[y] = 0;

				for ( int x = 0; x < this.getWidth(); x++ ) {
					this.setState( this.getState( x, y ), x, dest );
					this.setState( EMPTY, x, y );
				}
			}
		}

		// Recalculate column heights & max height.
		if ( filledRows > 0 ) {
			this.maxHeight = 0;
			for ( int x = 0; x < this.heights.length; x++ ) {
				this.heights[x] = this.tryMaxHeight( this.computeColHeight( x ) );
			}
		}

		return filledRows;
	}


	/**
	 * Uses the standard clearing method with no gravity.
	 *
	 * @return the amount of filled rows.
	 */
	private int clearStandard() {
		/*
		 * Complexity: O(height).
		 * - The row was filled: increment steps to move down/filled-rows.
		 * - If we've steps to move down & we didn't find a filled row this
		 * 		time we can move down n(filledRows) rows,
		 * 		swap copy row y to y - filledRows & clear y.
		 */
		int filledRows = 0;
		for ( int y = 0; y < this.getHeight(); y++ ) {
			if ( this.canFillRow( y ) ) {
				++filledRows;
			} else if ( filledRows > 0 ) {
				int dest = y - filledRows;
				this.widths[dest] = this.widths[y];
				this.widths[y] = 0;

				for ( int x = 0; x < this.getWidth(); x++ ) {
					this.setState( this.getState( x, y ), x, dest );
					this.setState( EMPTY, x, y );
				}
			}
		}

		// Recalculate column heights & max height.
		if ( filledRows > 0 ) {
			this.maxHeight = 0;
			for ( int x = 0; x < this.heights.length; x++ ) {
				this.heights[x] = this.tryMaxHeight( this.computeColHeight( x ) );
			}
		}

		return filledRows;
	}


	/**
	 * Returns true if the board is completely empty.<br/>
	 * This is the same as <code>{@link #getMaxHeight()} == 0.</code>
	 *
	 * @return true if the board is empty.
	 */
	public boolean isEmpty() {
		return this.getMaxHeight() == 0;
	}

	/**
	 * <p>If a {@link #place(Piece, Position)} happens,<br/>
	 * optionally followed by a {@link #clearRows()}, a subsequent<br/>
	 * {@link #undo()} reverts the board to its state before the place().</p>
	 *
	 * <p>If the conditions for undo() are not met, such as calling<br/>
	 * {@link #undo()} twice in a row, then the second {@link #undo()} does nothing.</p>
	 */
	public void undo() {
		if ( this.isCommitted() ) {
			return;
		}

		// Let grid point to where gridCopy was, and clear gridCopy reference.
		this.grid = this.gridCopy;
		this.gridCopy = null;

		this.widths = this.widthsCopy;
		this.widthsCopy = null;

		this.heights = this.heightsCopy;
		this.heightsCopy = null;

		this.maxHeight = this.maxHeightCopy;
		this.maxHeightCopy = 0;

		this.sanityCheck();
	}

	/**
	 * Puts the board in the committed state.
	 * This means that {@link #undo()} will no longer revert back.
	 */
	public void commit() {
		this.gridCopy = null;
	}

	/**
	 * Returns whether or not the board is in a committed state or not.
	 *
	 * @return true if the board is in a committed state.
	 */
	public boolean isCommitted() {
		return this.gridCopy == null;
	}

	/**
	 * Returns the max column height present in the board.<br/>
	 * For an empty board this is 0.
	 *
	 * @return The max column height in board.
	 */
	public int getMaxHeight() {
		return this.maxHeight;
	}
	
	/**
	 * Given a piece and an x-coordinate, returns the y-value
	 * where the origin (lower left corner) of the piece would
	 * come to rest if it were dropped straight down at that x.
	 *
	 * @param piece The piece to check for.
	 * @param x The column it would be dropped at.
	 * @return The y-value where piece would rest if dropped.
	 */
	public int dropHeight( Piece piece, final int x ) {
		/*
		 * Implementation: uses the skirt and
		 * the column heights to compute this fast.
		 *
		 * Complexity: O(skirt length).
		 */
		int height = 0;
		for ( int i = x; i < x + piece.getSkirt().length; i++ ) {
			height = Math.max( this.getColumnHeight( i ), height );
		}
		return height;
	}

	/**
	 * Returns the height of the given column,<br/>
	 * which is the y-value of the highest block + 1.<br/>
	 * The height is 0 if the column contains no blocks.<br/>
	 *
	 * @param x The no. x column
	 * @return The height of the column x.
	 */
	public int getColumnHeight( final int x ) {
		return this.heights[x];
	}

	/**
	 * Returns the number of filled blocks in the given row.
	 *
	 * @param y The no. y row.
	 * @return The number of filled blocks in row y.
	 */
	public int getRowWidth( final int y ) {
		return this.widths[y];
	}

	/**
	 * Computes the column height.
	 *
	 * @param x the column.
	 * @return the height.
	 */
	private int computeColHeight( int x ) {
		int height = 0;

		/*
		 * Strategy discussion: Iterate forward or backwards?
		 * We pose that it is better to start from bottom to up
		 * because a skilled player will probably not allow
		 * blocks to stack to high and thus if we begin from
		 * the bottom to up (forward iteration) we are likely to loop less.
		 *
		 * If the user plays crap it will end the game
		 * quickly so it ain't an issue.
		 *
		 * However! Iterating backwards claims much less
		 * operations per iteration anyhow, so we use that!
		 */
		for ( int y = this.getHeight() - 1; y >= 0; y-- ) {
			if ( this.isFilled( x, y ) ) {
				height = y + 1;
				break;
			}
		}

		return height;
	}

	/**
	 * Performs sanity check on all redundancies of board (widths, heights, maxHeight, topSpace).
	 */
	private void sanityCheck() {
		if ( !DEBUG ) {
			return;
		}

		// Check array sizes.
		if ( this.heights.length != this.getWidth() ) {
			throw new SanityException( "Insanity! Illegal length for heights array = " + this.heights.length + ", should be = " + this.getWidth() );
		}

		if ( this.widths.length != this.getHeight() ) {
			throw new SanityException( "Insanity! Illegal length for widths array = " + this.widths.length + ", should be = " + this.getHeight() );
		}

		// Width sanity.
		for ( int y = 0; y < this.getHeight(); y++ ) {
			int nFilled = 0;
			for ( int x = 0; x < this.getWidth(); x++ ) {
				if ( this.isFilled( x, y ) ) {
					nFilled++;
				}
			}

			if ( this.widths[y] != nFilled ) {
				throw new SanityException( "Insanity! Row y = " + y + " has unmatching [real, stored] widths = [" + nFilled + ", " + this.widths[y] + "]" );
			}
		}

		// Height sanity.
		int maxHeight = 0;
		for ( int x = 0; x < this.getWidth(); x++ ) {
			int height = this.computeColHeight( x );
			if ( this.heights[x] != height ) {
				throw new SanityException( "Insanity! Column x = " + x + " has unmatching [real, stored] heights = [" + height + ", " + this.heights[x] + "]" );
			}

			if ( height > maxHeight ) {
				maxHeight = height;
			}
		}

		if ( maxHeight != this.maxHeight ) {
			throw new SanityException( "Insanity! max height has unmatching [real, stored] values = [" + maxHeight + ", " + this.maxHeight + "]" );
		}
	}

	public static class SanityException extends RuntimeException {
		private static final long serialVersionUID = 5426367390688265057L;

		public SanityException( String desc ) {
			super( desc );
		}
	}
}