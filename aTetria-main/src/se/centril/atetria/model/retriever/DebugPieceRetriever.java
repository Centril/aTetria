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
package se.centril.atetria.model.retriever;

import se.centril.atetria.model.Piece;
import se.centril.atetria.model.Tetromino;

/**
 * Stupid piece retriever for debugging only.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 6, 2013
 */
public class DebugPieceRetriever extends RandomizedPieceRetriever {
	private static final Tetromino USE = Tetromino.LINE;

	@Override
	public Piece nextPiece() {
		return this.pieceFactory.get( USE );
	}
}