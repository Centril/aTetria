package se.centril.atetria.model.segmenter;

import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.model.Piece;

/**
 * Segment part class.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 6, 2013
 */
public class SegPart {
	private final Piece piece;
	private final Position position;

	/**
	 * Constructs segment part given piece & position.
	 */
	public SegPart( Piece piece, Position pos ) {
		this.piece = piece;
		this.position = pos;
	}

	/**
	 * Returns the piece of the part.
	 *
	 * @return the piece.
	 */
	public Piece piece() {
		return this.piece;
	}

	/**
	 * Returns the position of the part.
	 *
	 * @return the position.
	 */
	public Position position() {
		return this.position;
	}
}