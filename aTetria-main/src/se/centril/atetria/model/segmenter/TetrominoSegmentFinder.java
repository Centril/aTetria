package se.centril.atetria.model.segmenter;

import java.util.List;

import se.centril.atetria.framework.geom.Direction;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.model.Board;
import se.centril.atetria.model.Tetromino;

/**
 * TetrominoSegmentFinder finds segments based on tetromino affiliation of pieces.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 6, 2013
 */
public class TetrominoSegmentFinder extends StickySegmentFinder {
	private Tetromino type;

	public TetrominoSegmentFinder( Board board ) {
		super( board );
	}

	@Override
	public boolean condition( Position pos, Direction direction ) {
		return super.condition( pos, direction ) && this.type == board().getState( pos ).getType();
	}

	protected Segment find( Position pos ) {
		this.type = board().getState( pos ).getType();
		return super.find( pos );
	}

	public List<Segment> find()  {
		List<Segment> segs = super.find();
		this.type = null;
		return segs;
	}
}