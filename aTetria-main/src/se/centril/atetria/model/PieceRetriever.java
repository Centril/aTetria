package se.centril.atetria.model;

/**
 * <p>PieceRetriever is the interface that delivers the next Piece to the Game.</p>
 * <p>It has one method: {@link #nextPiece()} that returns the Piece to use.<br/>
 * The given piece is not used directly. Instead, it is put into a queue.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public interface PieceRetriever {
	/**
	 * Returns the next piece.
	 *
	 * @return the next piece.
	 */
	public Piece nextPiece();
}