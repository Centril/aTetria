package se.centril.atetria.framework.utils.factory;

import java.util.Set;

/**
 * A factory interface that given a key can produce a well-defined object.
 *
 * @param <K> Key type restriction.
 * @param <V> Value type restriction. All items produced from factory must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-14
 * @version 1.1
 */
public interface Factory<K, V> {
	/**
	 * Returns set with public keys relating to what this factory can produce.
	 *
	 * @return List of keys relating to what factory can produce.
	 */
	public Set<K> getKeys();

	/**
	 * Alias for {@link #produce(K)}
	 */
	public V get( final K key );

	/**
	 * Returns a produced/constructed/instantiated object from a related key.
	 *
	 * @param key The key as given by getKeys()
	 * @return The produced object.
	 */
	public V produce( final K key );
}