package se.centril.atetria.model.retriever;

import se.centril.atetria.framework.rng.Randomizer;
import se.centril.atetria.framework.rng.RandomizerUtilizer;
import se.centril.atetria.model.Piece;
import se.centril.atetria.model.PieceFactory;
import se.centril.atetria.model.PieceRetriever;

/**
 * The default randomized implementation.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class RandomizedPieceRetriever implements PieceRetriever, RandomizerUtilizer {
	/** Pseudo Random Number Generator (PRNG) */
	private Randomizer rng;

	/** The factory for pieces. */
	private PieceFactory pieceFactory;

	/**
	 * Sets the piece factory.
	 *
	 * @param factory the factory to set.
	 */
	public void setPieceFactory( PieceFactory factory ) {
		this.pieceFactory = factory;
	}

	@Override
	public void setRandomizer( Randomizer rng ) {
		this.rng = rng;
	}

	@Override
	public Piece nextPiece() {
		return this.pieceFactory.getRandom( this.rng );
	}
}