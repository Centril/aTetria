package se.centril.atetria.model.command;

/**
 * A command to the model.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public interface Command {
	/**
	 * Returns the name of command.
	 *
	 * @return the name of the command.
	 */
	public String name();
}