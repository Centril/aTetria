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
package se.centril.atetria.framework.message;

import net.engio.mbassy.bus.BusConfiguration;
import net.engio.mbassy.bus.MBassador;

/**
 * The message bus for handling events.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 30, 2013
 */
public class MessageBus<T extends Message> extends MBassador<T> {
	/**
	 * Constructs bus with default bus config.
	 */
	public MessageBus() {
		this( BusConfiguration.Default() );
	}

	/**
	 * Constructs bus with given bus config.
	 *
	 * @param config the bus config.
	 */
	public MessageBus( BusConfiguration config ) {
		super( config );
	}

	/**
	 * Creates a new message bus.
	 *
	 * @return the new message bus.
	 */
	public static <T extends Message> MessageBus<T> create() {
		return new MessageBus<T>();
	}

	/**
	 * Creates a new message bus with given config.
	 *
	 * @param config the bus config.
	 * @return the new message bus.
	 */
	public static <T extends Message> MessageBus<T> create( BusConfiguration config ) {
		return new MessageBus<T>( config );
	}
}