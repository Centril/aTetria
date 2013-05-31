package se.centril.atetria.framework.utils.factory;

/**
 * This exception is at minimum thrown when:
 * 1) No FactoryInstantiator found for a name/key in hash-map (issues a {@link NullPointerException} in backend).
 * 2) In the case of {@link FactoryClassInstantiator} or similar:
		a) If there's no _class object (_class == null) -> NullPointerException.
 *		b) Constructor object can't be fetched (not accessible/found -> {@link SecurityException}/{@link NoSuchMethodException}).
 *		c) Constructor throws exception or an {@link ReflectiveOperationException} is thrown.
 *
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-11
 * @version 1.0
 */
public class FactoryInstantiationException extends RuntimeException {
	private static final long serialVersionUID = -2285286855275253354L;

    /**
     * Constructs a new FactoryInstantiationException
     * and copies the cause of another exception.
     *
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
    public FactoryInstantiationException( Throwable cause ) {
        super( cause );
    }
}