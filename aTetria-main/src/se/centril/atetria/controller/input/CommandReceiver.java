package se.centril.atetria.controller.input;

import se.centril.atetria.model.command.Command;

/**
 * A CommandReceiver receives commands.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 28, 2013
 */
public interface CommandReceiver {
	/**
	 * Called when a command is received from GUI.
	 *
	 * @param command the command.
	 */
	public void onCommandReceive( Command command );

	/**
	 * Called when a playable command is started.
	 *
	 * @param command the command.
	 */
	public void onCommandStart( Command command );

	/**
	 * Called when a playable command is ended.
	 *
	 * @param command the command.
	 */
	public void onCommandEnd( Command command );
}