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