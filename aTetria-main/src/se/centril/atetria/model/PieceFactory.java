package se.centril.atetria.model;

import se.centril.atetria.framework.utils.factory.FactoryInstantiator;

/**
 * Factory for Piece:s.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public final class PieceFactory extends Tetromino.ToFactory<Piece> {
	{
		FactoryInstantiator<Tetromino, Piece> maker = new FactoryInstantiator<Tetromino, Piece>() {
			public Piece get( Tetromino key ) { return new Piece( key ); }
		};

		add( Tetromino.SQUARE, maker );
		add( Tetromino.LINE, maker );
		add( Tetromino.SIGMA, maker );
		add( Tetromino.ZETA, maker );
		add( Tetromino.TAU, maker );
		add( Tetromino.LAMBDA, maker );
		add( Tetromino.LAMBDA_MIRRORED, maker );
	}
}