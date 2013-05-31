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