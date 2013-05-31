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