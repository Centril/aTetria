package se.centril.atetria.framework.utils.factory;

/**
 * FactorySingletonInstantiator acts as a singleton gateway for a given FactoryInstantiator.
 *
 * @param <K> Key type.
 * @param <V> Value type restriction. All items produced from instantiator must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-21
 * @version 1.0
 */
public class FactorySingletonInstantiator<K, V> implements FactoryInstantiator<K, V> {
	/** Holds the lazy-loaded singleton instance. */
	protected V instance;

	/** The instantiator used for instantiation. */
	protected final FactoryInstantiator<K, V> instantiator;

	/**
	 * Constructs the FactorySingletonInstantiator with a given FactoryInstantiator<T>
	 *
	 * @param instantiator The FactoryInstantiator<T> to singleton-construct.
	 */
	public FactorySingletonInstantiator( final FactoryInstantiator<K, V> instantiator ) {
		this.instantiator = instantiator;
	}

	/**
	 * Returns the singleton instance.
	 *
	 * @return The singleton instance.
	 */
	public V get( K key ) {
		if ( this.instance == null ) {
			this.instance = this.instantiator.get( key );
		}

		return this.instance;
	}
}