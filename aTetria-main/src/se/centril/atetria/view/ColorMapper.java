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
package se.centril.atetria.view;

import com.badlogic.gdx.graphics.Color;

import se.centril.atetria.framework.utils.factory.FactoryInstantiator;
import se.centril.atetria.model.Tetromino;

final class ColorMapper extends Tetromino.ToFactory<Color> {
	private static class ColorHolder implements FactoryInstantiator<Tetromino, Color> {
		private final Color color;
		public ColorHolder( Color color ) { this.color = color; }
		@Override
		public Color get( Tetromino key ) {
			return this.color;
		}
	};

	{
		add( Tetromino.SQUARE, new ColorHolder( Color.YELLOW ) );
		add( Tetromino.LINE, new ColorHolder( Color.CYAN ) );
		add( Tetromino.SIGMA, new ColorHolder( Color.GREEN ) );
		add( Tetromino.ZETA, new ColorHolder( Color.RED ) );
		add( Tetromino.TAU, new ColorHolder( new Color( 170f / 255f, 0f, 1f, 1f ) ) ); // purple
		add( Tetromino.LAMBDA, new ColorHolder( new Color( 1f, 165f / 255f, 0f, 1f ) ) );
		add( Tetromino.LAMBDA_MIRRORED, new ColorHolder( Color.BLUE ) );
	}
}