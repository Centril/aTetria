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
package se.centril.atetria.controller;

import net.engio.mbassy.listener.Handler;
import se.centril.atetria.controller.input.CommandReceiver;
import se.centril.atetria.controller.input.FallbackCommandProcessor;
import se.centril.atetria.framework.gdx.BaseGdxController;
import se.centril.atetria.framework.rng.MersenneTwisterFast;
import se.centril.atetria.model.Board;
import se.centril.atetria.model.Board.SanityException;
import se.centril.atetria.model.Game;
import se.centril.atetria.model.Game.GameOverEvent;
import se.centril.atetria.model.PieceFactory;
import se.centril.atetria.model.command.Command;
import se.centril.atetria.model.retriever.RandomizedPieceRetriever;
import se.centril.atetria.view.GameView;

/**
 * GameController is the central game controller.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 30, 2013
 */
public class GameController extends BaseGdxController implements CommandReceiver {
	private final Game game;

	private boolean update = true;

	private float deltaTime;

	private float updateSpeed = 0.5f;

	public GameController() {
		super();

		// Factory: Model.
		RandomizedPieceRetriever rpr = new RandomizedPieceRetriever();
		rpr.setRandomizer( new MersenneTwisterFast() );
		rpr.setPieceFactory( new PieceFactory() );

		this.game = new Game( new Board( 10, 20, 4 ) );
		this.game.setNextQueueSize( 5 );
		this.game.setPieceRetriever( rpr );

		// Factory: View.
		this.view( new GameView( game ) );
	}

	public void init() {
		this.catchBackKey();

		FallbackCommandProcessor processor = new FallbackCommandProcessor();
		this.input().setInputProcessor( processor.setCommandReceiver( this ) );

		this.game.eventBus().subscribe( this );
	}

	@Override
	public void update() {
		if ( this.update == false ) {
			return;
		}

		this.deltaTime += this.graphics().getDeltaTime();

		try {
			if ( this.deltaTime > this.updateSpeed ) {
				this.game.tick();
				this.deltaTime = 0;
			}
		} catch( SanityException e ) {
			this.update = false;
		//	logger().debug( "insanity!", e );
		}
	}

	@Handler
	public void onGameOver( GameOverEvent e ) {
		logger().debug( e.toString() );
	}

	@Override
	public void onCommandReceive( Command command ) {
		this.game.command( command );
	}

	@Override
	public void onCommandStart( Command command ) {
	}

	@Override
	public void onCommandEnd( Command command ) {
	}
}