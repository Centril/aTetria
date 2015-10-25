package se.centril.atetria.model.segmenter;

import java.util.List;
import java.util.Set;

import se.centril.atetria.framework.algo.FloodFill;
import se.centril.atetria.framework.algo.FloodFill.FloodHandler;
import se.centril.atetria.framework.geom.Direction;
import se.centril.atetria.framework.geom.MutablePosition;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.model.Board;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * StickySegmentFinder finds segments in Board.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 6, 2013
 */
public class StickySegmentFinder implements FloodHandler {
	/** The board to find segments in. */
	private final Board board;

	// Work objects.
	protected Position origin;
	protected Set<Position> pool;
	protected Segment segment;

	/**
	 * Constructs the segment finder.
	 *
	 * @param board 
	 */
	public StickySegmentFinder( Board board ) {
		this.board = board;
	}

	@Override
	public boolean condition( Position pos, Direction direction ) {
		return board().isFilled( pos );
	}

	@Override
	public void handle( Position pos, Direction direction ) {
		if ( origin.equals( pos ) ) {
			return;
		}

		this.addSegPart( new MutablePosition( pos ) );
	}

	protected void addSegPart( Position pos ) {
		this.pool.remove( pos );
		this.segment.add( new SegPart( board.getState( pos ), pos ) );
	}

	protected Board board() {
		return this.board;
	}

	protected Segment find( Position pos ) {
		this.segment = new Segment();

		this.origin = pos;
		this.addSegPart( this.origin );

		FloodFill.floodFill( this, this.origin, board.getSize() );

		Segment segment = this.segment;
		this.segment = null;
		this.origin = null;
		return segment;
	}

	/**
	 * Add all non-empty positions to pool.
	 */
	protected void fillPool() {
		if ( this.pool == null ) {
			this.pool = Sets.newHashSet();
		} else {
			this.pool.clear();
		}

		for ( int x = 0; x < board.getWidth(); x++ ) {
			for ( int y = 0; y < board.getHeight(); y++ ) {
				if ( board.isFilled( x, y ) ) {
					this.pool.add( new MutablePosition( x, y ) );
				}
			}
		}
	}

	public List<Segment> find()  {
		this.fillPool();

		// Keep finding segments until pool is empty.
		List<Segment> segments = Lists.newArrayList();
		while ( !this.pool.isEmpty() ) {
			// Ugly...
			this.find( this.pool.iterator().next() );
		}

		return segments;
	}
}