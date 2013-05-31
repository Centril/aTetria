package se.centril.atetria.framework.utils.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import se.centril.atetria.framework.rng.Randomizer;

/**
 * An abstract factory class relating a key K to a {@link FactoryInstantiator} of type T.
 * The factory can - given a set of given FactoryInstantiators produce objects of type T.
 * Extending the class is easy. Just extend it with
 * AbstractFactory<KeyType, YourRestrictedType>, eg.: AbstractFactory<String, Icecream>.
 * and either use constructor or initializer blocks (non-static, and most compact method...)
 * to add your relations with: add( "Strawberry", StrawberryIcecream.class );
 *
 * @param <K> Key type restriction.
 * @param <V> Value type restriction. All items produced from factory must be at least of this super-class.
 * @author Mazdak Farrokhzad/Centril <mazdakf@student.chalmers.se>
 * @license LGPL 3.0 http://www.gnu.org/copyleft/lesser.html
 * @since 2012-12-11
 * @version 1.2
 */
public abstract class AbstractFactory<K, V> implements Factory<K, V> {
	/*
	 * ALL of these methods and all fields should in the best of worlds be static.
	 * But in the piece-of-crap-language that is called Java there's no support for Late Static Binding (LSB),
	 * and thus it is impossible to implement this in a clean and well-designed manner without resorting to ugly hacks.
	 *
	 * http://en.wikipedia.org/wiki/Late_static_binding
	 * http://php.net/manual/en/language.oop5.late-static-bindings.php
	 *
	 * Using non-static context comes with pros & cons.
	 * Pros:	- The relations are lazy-loaded which means that it won't allocate memory until AbstractFactory is needed.
	 * 			- Better type-safety on T.
	 *
	 * Cons:	- Every time a factory is made, so is all of the relations,
	 * 			thus you might want to avoid making duplicates using some Singleton-like-pattern in your derived Factory, eg.:
	 * 			private static class Holder { public static IcecreamFactory instance = new IcecreamFactory(); }
	 *			public static IcecreamFactory getInstance() { return Holder.instance; }
	 */

	/**
	 * A map/list relating a key to a FactoryInstantiator.
	 * This map has a 1x1 relation between keys and FactoryInstantiators and is thus bijective.
	 */
	protected final Map<K, FactoryInstantiator<K, V>> relations;

	/**
	 * Constructor: creates the relations map.
	 */
	public AbstractFactory() {
		this.relations = this.createMap();
	}

	/**
	 * Creates/instantiates the relations map.
	 * Override if you want a different data structure.
	 *
	 * @return The relations map.
	 */
	protected Map<K, FactoryInstantiator<K, V>> createMap() {
		return new HashMap<K, FactoryInstantiator<K, V>>();
	}

	/**
	 * Enables run-time addition of a Class object.
	 *
	 * @param key What the FactoryInstantiator is called "publicly".
	 * @param _class Class object of class to instantiate
	 */
	public AbstractFactory<K, V> add( final K key, final Class<? extends V> _class ) {
		return add( key, new FactoryClassInstantiator<K, V>( _class ) );
	}

	/**
	 * Enables run-time addition of a FactoryInstantiator.
	 *
	 * @param name What the FactoryInstantiator is called "publicly".
	 * @param instantiator A FactoryInstantiator used to instantiate an object.
	 */
	public AbstractFactory<K, V> add( final K key, final FactoryInstantiator<K, V> instantiator ) {
		relations.put( key, instantiator );
		return this;
	}

	/**
	 * Disables run-time instantiation of a given "public" key from factory.
	 *
	 * @param name What the FactoryInstantiator is called "publicly".
	 */
	public AbstractFactory<K, V> remove( final K key ) {
		relations.remove( key );
		return this;
	}

	/**
	 * {@inheritDoc}
	 * In the case of AbstractFactory it relates to FactoryInstatiator:s.
	 */
	public Set<K> getKeys() {
		return this.relations.keySet();
	}

	/** {@inheritDoc} */
	public V get( final K key ) {
		return this.produce( key );
	}

	/**
	 * {@inheritDoc}
	 * In the case of AbstractFactory it relates to FactoryInstatiator:s.
	 *
	 * @throws FactoryInstantiationException if no object can be produced.
	 */
	public V produce( final K key ) {
		try {
			return relations.get( key ).get( key );
		} catch ( ROJava6Exception e ){
			throw new FactoryInstantiationException( e );
		} catch ( NullPointerException e ) {
			throw new FactoryInstantiationException( e );
		} catch ( SecurityException e ) {
			throw new FactoryInstantiationException( e );
		}
	}

	/**
	 * Returns returns a list of the produced objects from all keys.
	 * NOTE: This method walks through all keys and calls produce(),
	 * thus it's recommended to limit the number of times it is called,
	 * especially if not using singleton-production.
	 *
	 * @return List of the produced objects from all keys.
	 */
	public List<V> getAll() {
		Set<? extends K> keys = this.getKeys();
		List<V> all = new ArrayList<V>( keys.size() );

		for ( K key : keys ) {
			all.add( this.produce( key ) );
		}

		return all;
	}

	/**
	 * Given a RNG (Random Number Generator) it retrieves
	 * & produces a randomly selected item from factory.
	 *
	 * @param rng Random Number Generator.
	 * @return The randomly selected produced item.
	 */
	public V getRandom( final Random rng ) {
		List<K> keys = new ArrayList<K>( this.getKeys() );
		return this.get( keys.get( rng.nextInt( keys.size() ) ) );
	}

	/**
	 * Given a RNG (Random Number Generator) it retrieves
	 * & produces a randomly selected item from factory.
	 *
	 * @param rng Random Number Generator.
	 * @return The randomly selected produced item.
	 */
	public V getRandom( final Randomizer rng ) {
		List<K> keys = new ArrayList<K>( this.getKeys() );
		return this.get( keys.get( rng.nextInt( keys.size() ) ) );
	}
}