package se.centril.atetria.controller;

import net.engio.mbassy.listener.Handler;
import se.centril.atetria.controller.input.CommandReceiver;
import se.centril.atetria.controller.input.FallbackCommandProcessor;
import se.centril.atetria.framework.gdx.BaseGdxController;
import se.centril.atetria.model.Board;
import se.centril.atetria.model.Game;
import se.centril.atetria.model.Game.GameOverEvent;
import se.centril.atetria.model.command.Command;
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

	public GameController() {
		super( new GameView() );

		this.game = new Game( new Board( 10, 20, 4 ) );
	}

	public void init() {
		this.catchBackKey();

		FallbackCommandProcessor processor = new FallbackCommandProcessor();
		this.input().setInputProcessor( processor.setCommandReceiver( this ) );

		this.game.eventBus().subscribe( this );
	}

	@Override
	public void update() {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCommandEnd( Command command ) {
		// TODO Auto-generated method stub
		
	}
}