package se.centril.atetria.framework.utils.factory;

/**
 * FactoryInstantiator is a helper for factories which constructs Objects of some sort.
 *
 * @param <K> Key type.
 * @param <V> Value type restriction. All items produced from instantiator must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-11
 * @version 1.1
 */
public interface FactoryInstantiator<K, V> {
	/**
	 * Constructs an Object for Factory.
	 *
	 * @param key The key that retrieved this FactoryInstantiator ( may be null or not used! ).
	 * @return The constructed Object.
	 */
	public V get( K key );
}