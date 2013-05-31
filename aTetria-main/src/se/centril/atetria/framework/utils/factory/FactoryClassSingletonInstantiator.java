package se.centril.atetria.framework.utils.factory;

/**
 * FactoryDirectSingletonInstantiator acts as a gateway between FactoryInstantiator
 * and a Class object by directly constructing it when {@link #get()} is called.
 *
 * @param <K> Key type restriction.
 * @param <V> Value type restriction. All items produced from instantiator must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-21
 * @version 1.0
 */
public class FactoryClassSingletonInstantiator<K, V> extends FactorySingletonInstantiator<K, V> {
	/**
	 * Takes a Class object that will be singleton-instantiated with no-parameter constructor.
	 *
	 * @param _class The class object to instantiate.
	 */
	public FactoryClassSingletonInstantiator( Class<? extends V> _class ) {
		super( new FactoryClassInstantiator<K, V>( _class ) );
	}
}