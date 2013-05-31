package se.centril.atetria.framework.utils.factory;

/**
 * An abstract factory class wrapping all given FactoryInstantiator or
 * {@link FactoryClassInstantiator} in case of {@link #add(Object, Class)} in
 * a {@link FactorySingletonInstantiator} which has the effect that
 * once produced the same object is returned for the same key.
 *
 * @param <K> Key type.
 * @param <T> Type restriction. All items produced from factory must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-21
 * @version 1.0
 */
public abstract class SingletonFactory<K, T> extends AbstractFactory<K, T> {
	/**
	 * Enables run-time addition of a Class object.
	 *
	 * @param key What the FactoryInstantiator is called "publicly".
	 * @param _class Class object of class to instantiate
	 */
	public AbstractFactory<K, T> add( final K key, final Class<? extends T> _class ) {
		return add( key, new FactorySingletonInstantiator<K, T>( new FactoryClassInstantiator<K, T>( _class ) ) );
	}

	/**
	 * Enables run-time addition of a FactoryInstantiator.
	 *
	 * @param name What the FactoryInstantiator is called "publicly".
	 * @param instantiator A FactoryInstantiator used to instantiate an object.
	 */
	public AbstractFactory<K, T> add( final K key, final FactoryInstantiator<K, T> instantiator) {
		this.relations.put( key, new FactorySingletonInstantiator<K, T>( instantiator ) );
		return this;
	}
}
