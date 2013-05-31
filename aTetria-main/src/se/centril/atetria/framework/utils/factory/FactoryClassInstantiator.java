package se.centril.atetria.framework.utils.factory;

/**
 * FactoryDirectInstantiator acts as a gateway between FactoryInstantiator
 * and a Class object by directly constructing it when {@link #get()} is called.
 *
 * @param <K> Key type restriction.
 * @param <V> Value type restriction. All items produced from instantiator must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-11
 * @version 1.0
 */
public class FactoryClassInstantiator<K, V> implements FactoryInstantiator<K, V> {
	/** Stores the Class object that will instantiated */
	protected final Class<? extends V> _class;

	/**
	 * Takes a Class object that will be instantiated with no-parameter constructor.
	 *
	 * @param _class The class object to instantiate.
	 */
	public FactoryClassInstantiator( Class<? extends V> _class ) {
		this._class = _class;
	}

	/**
	 * {@inheritDoc}
	 * The object is constructed from a Class object.
	 * The key parameter is ignored, it is simply to
	 * costly to find the class of generic type K every time.
	 * 
	 * @param key The key parameter is ignored.
	 * @throws NullPointerException If reflective operations are used this may be thrown if no Class object was provided.
	 * @throws ReflectiveOperationException If a reflective operation in the chain of instantiation fails.
	 * @throws SecurityException On access violation regarding constructor-access.
	 */
	@Override
	public V get( K key ) {
		try {
			/*
			 * We use reflective logic for instantiation.
			 *
			 * 1) _class == null -> NullPointerException.
			 * 2) Constructor not accessible -> IllegalAccessException.
			 * 3) Finally the object is instantiated, could result in InstantiationException.
			 */
			return this._class.newInstance();
		} catch( IllegalAccessException e ) {
			ROJava6Exception.reThrow( e );
		} catch( InstantiationException e ) {
			ROJava6Exception.reThrow( e );
		}

		// Satisfy compiler.
		return null;
	}
}