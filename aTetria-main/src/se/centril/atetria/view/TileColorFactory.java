package se.centril.atetria.view;

import java.awt.Color;

import se.centril.atetria.framework.utils.factory.FactoryInstantiator;
import se.centril.atetria.model.Tetromino;

final class TileColorFactory extends Tetromino.ToFactory<Color> {
	private static class TileInstantiator implements FactoryInstantiator<Tetromino, Color> {
		private final Color color;
		public TileInstantiator( Color color ) { this.color = color; }
		@Override
		public Color get( Tetromino key ) {
			return this.color;
		}
	};

	{
		add( Tetromino.SQUARE, new TileInstantiator( Color.YELLOW ) );
		add( Tetromino.LINE, new TileInstantiator( Color.CYAN ) );
		add( Tetromino.SIGMA, new TileInstantiator( Color.GREEN ) );
		add( Tetromino.ZETA, new TileInstantiator( Color.RED ) );
		add( Tetromino.TAU, new TileInstantiator( new Color( 170, 0, 255 ) ) ); // purple
		add( Tetromino.LAMBDA, new TileInstantiator( new Color( 255, 165, 0 ) ) );
		add( Tetromino.LAMBDA_MIRRORED, new TileInstantiator( Color.BLUE ) );
	}
}