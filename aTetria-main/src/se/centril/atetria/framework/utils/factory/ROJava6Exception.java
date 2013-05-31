package se.centril.atetria.framework.utils.factory;

/**
 * <p>Wraps exception classes that in JDK1.7 extends<br/>
 * ReflectiveOperationException but in JDK1.6 does not.<br/>
 * Some systems (e.g Android as of 24 march 2013) doesn't have JDK1.7.<br/>
 * Therefore this exception type is motivated.</p>
 *
 * <p>These exception types are covered:
 * 1. ClassNotFoundException<br/>
 * 2. IllegalAccessException<br/>
 * 3. InvocationTargetException<br/>
 * 4. NoSuchFieldException<br/>
 * 5. NoSuchMethodException</p>
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 24 mar 2013
 * @version 1.0
 */
public final class ROJava6Exception extends RuntimeException {
	private static final long serialVersionUID = -7851363969294802728L;

	/**
	 * Takes a type of Throwable that in JDK1.7 extends<br/>
	 * ReflectiveOperationException but in JDK1.6 does not.
	 *
	 * @param throwable the throwable to wrap.
	 */
	public ROJava6Exception( Throwable throwable ) {
		super( throwable );
	}

	/**
	 * Re-throws as ROJava6Exception.
	 *
	 * @param e the exception.
	 * @throws ROJava6Exception always.
	 */
	public static void reThrow( Throwable e ) throws ROJava6Exception {
		throw new ROJava6Exception( e );
	}
}