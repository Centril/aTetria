package se.centril.atetria.controller.input;

import com.badlogic.gdx.InputProcessor;

/**
 * A CommandProcessor is an InputProcessor that only cares about in-game related commands.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 28, 2013
 */
public interface CommandProcessor extends InputProcessor {
	/**
	 * Sets the receiver of commands.
	 *
	 * @param receiver the receiver of commands.
	 */
	public CommandProcessor setCommandReceiver( CommandReceiver receiver );
}