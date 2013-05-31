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

import java.util.ArrayDeque;
import java.util.Deque;

import com.google.common.base.Preconditions;

import se.centril.atetria.framework.geom.FinalPosition;
import se.centril.atetria.framework.geom.MutablePosition;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.framework.message.Message;
import se.centril.atetria.framework.message.MessageBus;
import se.centril.atetria.model.Board.PlacementState;
import se.centril.atetria.model.command.Command;
import se.centril.atetria.model.command.ExtraCommand;
import se.centril.atetria.model.command.HorizontalCommand;
import se.centril.atetria.model.command.PieceCommand;
import se.centril.atetria.model.command.RotationCommand;
import se.centril.atetria.model.command.VerticalCommand;

/**
 * Game is the master model of the game.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 27, 2013
 */
public class Game {
	/* --------------------------------
	 * Observation/Events.
	 * --------------------------------
	 */

	/**
	 * (Game)Event interface.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since May 30, 2013
	 */
	public static interface Event extends Message {
		/**
		 * Returns the game source of event.
		 *
		 * @return the game.
		 */
		public Game game();
	}

	/**
	 * Base game event class.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since May 30, 2013
	 */
	private abstract class BaseEvent implements Event {
		public Game game() {
			return Game.this;
		}

		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	/**
	 * Enumeration of change events in game.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since May 30, 2013
	 */
	public class GameOverEvent extends BaseEvent {
	}

	private MessageBus<Event> eventBus = MessageBus.create();

	/**
	 * Sets the event bus of the game.
	 *
	 * @param eventBus the event bus.
	 */
	public void eventBus( MessageBus<Event> eventBus ) {
		this.eventBus = Preconditions.checkNotNull( eventBus );
	}

	/**
	 * Returns the event bus of the game.
	 *
	 * @return the event bus.
	 */
	public MessageBus<Event> eventBus() {
		return this.eventBus;
	}

	/* --------------------------------
	 * Extra states.
	 * --------------------------------
	 */

	/** Time when game was started. */
	private long startTime;

	/** Time when a row was last cleared. */
	private long clearTime;

	/** Amount of pieces played (Score). */
	private int playedPiecesCount;

	/** The piece that has been put away for now to use later. */
	private Piece savedPiece;

	/* --------------------------------
	 * Basic Position/States.
	 * --------------------------------
	 */

	private boolean isGameOver;

	/** Grid-board of game. */
	private final Board board;

	/** The current piece in play (if none -> null). */
	private Piece currentPiece;

	/** The position of currentPiece. */
	private Position currentPos;

	/** Did the player move the piece? */
	private boolean moved;

	/** Queue that stores next-in-line pieces. */
	private Deque<Piece> nextQueue = new ArrayDeque<Piece>();

	/** The size of nextQueue at all times. */
	private int nextQueueSize;

	private PieceRetriever retriever;

	/* --------------------------------
	 * Getter public interface.
	 * --------------------------------
	 */

	/**
	 * Returns the grid-board of the game.
	 *
	 * @return The grid-board.
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Returns the number of elapsed milliseconds (ms) since start of game.
	 *
	 * @return Elapsed time since game start.
	 */
	public long getElapsedTime() {
		return this.now() - this.startTime;
	}

	/**
	 * Returns true if the game is NOT over.
	 *
	 * @return true if the game is NOT over.
	 */
	public boolean isActive() {
		return !this.isGameOver();
	}

	/**
	 * Returns true if the game is over.
	 *
	 * @return true if the game is over.
	 */
	public boolean isGameOver() {
		return this.isGameOver;
	}

	/**
	 * Returns the score: the amount of played pieces.
	 *
	 * @return Score.
	 */
	public int getScore() {
		return this.playedPiecesCount;
	}

	/**
	 * Returns the number of elapsed milliseconds (ms) since last row clearing occured.
	 *
	 * @return Elapsed time since last row clearing.
	 */
	public long timeSinceClear() {
		return this.now() - this.clearTime;
	}

	/**
	 * Returns the number of pieces to hold in queue at all times.
	 *
	 * @return the number of pieces to hold.
	 */
	public int getNextQueueSize() {
		return this.nextQueueSize;
	}

	/* --------------------------------
	 * Constructors + setters interface.
	 * --------------------------------
	 */

	/**
	 * Constructs a Model.
	 *
	 * @param dim the dimensions of board.
	 */
	public Game( Board board ) {
		this.board = board;

		// Set count of played pieces, initially 0.
		this.playedPiecesCount = 0;
	}

	/**
	 * Sets the PieceRetriever for filling the queue of next pieces.
	 *
	 * @param retriever the PieceRetriever to use.
	 */
	public void setPieceRetriever( PieceRetriever retriever ) {
		this.retriever = retriever;
	}

	/**
	 * Sets the number of pieces to hold in queue at all times.
	 *
	 * @param max the number of pieces to hold.
	 */
	public void setNextQueueSize( int size ) {
		this.nextQueueSize = size;
	}

	/* --------------------------------
	 * Logic interface.
	 * --------------------------------
	 */

	/**
	 * Called when the piece should be commanded {@link VerticalCommand#DOWN} on timer.
	 */
	public void tick() {
		this.controlPiece( VerticalCommand.DOWN );
	}

	/**
	 * Commands the model.<br/>
	 * Most game logic via commands.
	 *
	 * @param command the command.
	 */
	public void command( final Command command ) {
		if ( command instanceof PieceCommand ) {
			this.controlPiece( (PieceCommand) command );
		} else if ( command instanceof ExtraCommand ) {
			this.handleExtra( (ExtraCommand) command );
		}
	}

	/* --------------------------------
	 * Misc private interface.
	 * --------------------------------
	 */

	/**
	 * Handles extra commands.
	 *
	 * @param command the command.
	 */
	private void handleExtra( ExtraCommand command ) {
		if ( command == ExtraCommand.SAVE_CURRENT ) {
			this.saveCurrent();
		}
	}

	/**
	 * Saves the current piece.
	 */
	private void saveCurrent() {
		// There's already a saved piece, it must become current now.
		if ( this.savedPiece == null ) {
			// Swap.
			Piece tmp = this.savedPiece;
			this.savedPiece = this.currentPiece;
			this.setCurrentPiece( tmp );
		} else {
			this.savedPiece = this.currentPiece;
			this.addNextPiece();
		}
	}

	/**
	 * Handles commands for pieces that's in board.
	 *
	 * @throws GameOverException If board is too tall.
	*/
	private void controlPiece( final PieceCommand command ) {
		if ( this.isGameOver() ) {
			throw new IllegalArgumentException( "The game is over." );
		}

		this.init();

		if ( this.currentPiece != null) {
			// Remove the piece from its old position.
			this.board.undo();
		}

		// Try out the new position + piece (rolls back if it doesn't work)
		PlacementState result = this.tryNewPosition( command );
		boolean failed = result.isFailed();

		if ( result.isRowFilled() ) {
			// Row clearing is going to happen, notify listeners!
			//this.pcs.firePropertyChange( "prepareClear", null, null );
		} else if ( failed && command == VerticalCommand.DOWN && !moved ) {	// it's landed
			/*
			 * How to detect when a piece has landed: if this move hits
			 * something on its DOWN verb, and the previous verb was also DOWN
			 * (i.e. the player was not still moving it), then the previous
			 * position must be the correct "landed" position, so we're done
			 * with the falling of this piece.
			 */
			int clearedRows = this.board.clearRows();
			if ( clearedRows > 0 ) {
				this.clearTime = this.now();
				// Notify listeners of clear!
				// this.pcs.firePropertyChange( "clear", 0, clearedRows );
			}

			if ( this.board.hasOverflow() ) {
				// The board is too tall, we've lost.
				this.gameOver();
			} else {
				// Otherwise add a new piece and keep playing.
				this.addNextPiece();
			}
		}

		// Note if the player made a successful non-DOWN move --
		// used to detect if the piece has landed on the next tick()
		this.moved = !failed && command != VerticalCommand.DOWN;
	}

	/**
	 * Does initializations on game start.
	 *
	 * @throws GameOverException Doesn't happen.
	 */
	private void init() {
		if ( this.startTime == 0 ) {
			this.addNextPiece();

			// Set start time of game, user later for calculation of elapsed time.
			this.startTime = this.now();
		}
	}

	/**
	 * Returns the current time in milliseconds (ms).
	 *
	 * @return the current time.
	 */
	private long now() {
		return System.currentTimeMillis();
	}

	/**
	 * Tries out a new position for the current piece
	 * based on the given TickCommand.
	 *
	 * The board should be in the committed state --
	 * i.e. the piece should not be in the board at the moment.
	 * This is necessary so dropHeight() may be called without
	 * the piece "hitting itself" on the way down.
	 */
	private PlacementState tryNewPosition( PieceCommand command ) {
		// As a starting point, the new position is the same as the old.
		Piece newPiece = this.currentPiece;
		Position newPos = this.currentPos.cpy();

		// Make changes based on the command.
		if ( command instanceof HorizontalCommand ) {
			switch ( (HorizontalCommand) command ) {
			default:
			case LEFT:	newPos.subX( 1 ); break;
			case RIGHT: newPos.addX( 1 ); break;
			}
		} else if ( command instanceof VerticalCommand ) {
			switch ( (VerticalCommand) command ) {
			default:
			case DOWN:	newPos.subY( 1 ); break;
			// NOTE: if the piece were in the board, it would interfere here.
			case DROP:	newPos.setY( this.board.dropHeight( newPiece, newPos.x() ) ); break;
			}
		} else if ( command instanceof RotationCommand ) {
			/*
			 * Make the piece appear to rotate about its center.
			 * We can't just leave it at the same lower-left
			 * origin as the previous piece.
			 */
			newPiece = newPiece.rotate( ((RotationCommand) command) );
			FinalPosition diff = this.currentPiece.getSize().sub( newPiece.getSize() );
			newPos.add( diff.x() / 2, diff.y() / 2 );
		} else {
			throw new AssertionError( "ShouldNotHappenException" );
		}

		// Set to current.
		return this.setCurrent( newPiece, newPos );
	}

	/**
	 * Given a piece, tries to install that piece into the board and set it to
	 * be the current piece. If the placement is not possible, then the
	 * placement is undone, and the board is not changed. The board should be in
	 * the committed state when this is called. Returns the same error code as
	 * {@link Board#place(Piece, Position)}.
	 *
	 * @param piece the piece to place.
	 * @param pos the position to place piece at.
	 * @return the state of placement.
	 */
	private PlacementState setCurrent( Piece piece, Position pos ) {
		PlacementState result = this.board.place( piece, pos );

		if ( result.isFailed() ) {
			this.board.undo();
		} else {
			this.currentPiece = piece;
			this.currentPos = pos;
		}

		return result;
	}

	/**
	 * Tries to add the next-in-queue piece at the top of the board.<br/>
	 * Ends the game if it's not possible.
	 */
	private void addNextPiece() {
		this.setCurrentPiece( this.getNextPiece() );
	}

	/**
	 * Retrieves the next piece and enforces queue size.
	 *
	 * @return the next piece.
	 */
	private Piece getNextPiece() {
		// Fill the queue to max + 1.
		while ( this.nextQueue.size() <= this.getNextQueueSize() + 1 ) {
			this.nextQueue.add( this.retriever.nextPiece() );
		}

		// Retrieve one, size-of queue is now max.
		return this.nextQueue.pop();
	}

	/**
	 * Sets a piece to new current one and centers it at top.
	 *
	 * @param piece the piece to set as new current.
	 */
	private void setCurrentPiece( Piece piece ) {
		this.playedPiecesCount++;

        // Commit things the way they are.
        this.board.commit();
		this.currentPiece = null;

		// Add set piece to be in play, center it at top.
		FinalPosition diff = this.board.getSize().sub( piece.getSize() );
		PlacementState result = this.setCurrent( piece, new MutablePosition( diff.x() / 2, diff.y() ) );

		/*
		 * This probably never happens, since
		 * the blocks at the top allow space
		 * for new pieces to at least be added.
		 */
		if ( result.isFailed() ) {
			this.gameOver();
		}
	}

	/**
	 * Issues game-over.<br/>
	 * Notifies listeners.
	 */
	private void gameOver() {
		this.isGameOver = true;
		this.eventBus.publish( new GameOverEvent() );
	}
}