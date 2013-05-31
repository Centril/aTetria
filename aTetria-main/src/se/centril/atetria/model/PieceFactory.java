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