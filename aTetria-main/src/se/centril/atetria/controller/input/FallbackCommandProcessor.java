package se.centril.atetria.controller.input;

import java.util.Arrays;

import se.centril.atetria.model.command.Command;
import se.centril.atetria.model.command.HorizontalCommand;
import se.centril.atetria.model.command.RotationCommand;
import se.centril.atetria.model.command.VerticalCommand;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Handles inputs from some random OS and maps them to commands in game.<br/>
 * Provides only keyboard bindings atm.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public class FallbackCommandProcessor extends InputAdapter implements CommandProcessor {
	private final ListMultimap<Command, Integer> keyMap = ArrayListMultimap.create();

	{
		keyMap.putAll( HorizontalCommand.LEFT, Arrays.asList( new Integer[] { Keys.LEFT,	Keys.A } ) );
		keyMap.putAll( HorizontalCommand.RIGHT, Arrays.asList( new Integer[] { Keys.RIGHT,	Keys.D } ) );
		keyMap.putAll( VerticalCommand.DOWN, Arrays.asList( new Integer[] { Keys.DOWN,	Keys.S } ) );
		keyMap.putAll( VerticalCommand.DROP, Arrays.asList( new Integer[] {	Keys.ENTER } ) );
		keyMap.putAll( RotationCommand.COUNTER_CLOCKWISE, Arrays.asList( new Integer[] { Keys.SPACE, Keys.Q } ) );
		keyMap.putAll( RotationCommand.CLOCKWISE, Arrays.asList( new Integer[] { Keys.E } ) );
	}

	private CommandReceiver receiver;

	private boolean inDown = false;

	/**
	 * Sets the keys for a command.
	 *
	 * @param cmd the command.
	 * @param keycodes the keycodes to use for that command.
	 */
	public void setCommandKeys( Command cmd, Iterable<Integer> keycodes ) {
		this.keyMap.replaceValues( cmd, keycodes );
	}

	@Override
	public FallbackCommandProcessor setCommandReceiver( CommandReceiver receiver ) {
		this.receiver = receiver;
		return this;
	}

	@Override
	public boolean keyDown( int keycode ) {
		if ( this.handleDownStart( keycode ) ) {
			return true;
		}

		for ( Command cmd : keyMap.keySet() ) {
			if ( !(cmd instanceof RotationCommand) && this.bindsKey( cmd, keycode ) ) {
				return this.issue( cmd );
			}
		}

		return false;
	}

	@Override
	public boolean keyUp( int keycode ) {
		for ( Command cmd : keyMap.keySet() ) {
			if ( cmd instanceof RotationCommand && this.bindsKey( cmd, keycode ) ) {
				return this.issue( cmd );
			}
		}

		return false;
	}

	private boolean handleDownStart( int keycode ) {
		Command cmd = VerticalCommand.DOWN;
		if ( bindsKey( cmd, keycode ) ) {
			this.inDown = true;
			this.receiver.onCommandStart( cmd );
			this.receiver.onCommandReceive( cmd );
			return true;
		}

		return false;
	}

	private boolean bindsKey( Command cmd, int keycode ) {
		return keyMap.get( cmd ).contains( keycode );
	}

	private void endDown() {
		if ( this.inDown ) {
			this.inDown = false;
			this.receiver.onCommandEnd( VerticalCommand.DOWN );
		}
	}

	private boolean issue( Command cmd ) {
		this.endDown();
		this.receiver.onCommandReceive( cmd );
		return true;
	}
}