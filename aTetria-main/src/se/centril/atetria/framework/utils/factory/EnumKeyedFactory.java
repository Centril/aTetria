package se.centril.atetria.framework.utils.factory;

import java.util.EnumMap;
import java.util.Map;

/**
 * Changes the data structure of relations in a AbstractFactory to EnumMap.
 * Extending classes must implement {@link #getEnumClass()}.
 *
 * @param <K> Key type restriction.
 * @param <V> Value type restriction. All items produced from factory must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-21
 * @version 1.0
 */
public abstract class EnumKeyedFactory<K extends Enum<K>, V> extends AbstractFactory<K, V> {
	/**
	 * Provides EnumMap as data structure for factory relations.
	 * Extending classes must implement {@link #keyClass()}
	 * which returns the Class object of the enum used for key.
	 *
	 * @return The EnumMap.
	 */
	protected final Map<K, FactoryInstantiator<K, V>> createMap() {
		return new EnumMap<K, FactoryInstantiator<K, V>>( this.keyClass() );
	}

	/**
	 * Returns the Class object of the enum class used for keys.
	 * Must be implemented by sub-classes.
	 *
	 * @return The Class object of the enum-key class.
	 */
	protected abstract Class<K> keyClass();
}