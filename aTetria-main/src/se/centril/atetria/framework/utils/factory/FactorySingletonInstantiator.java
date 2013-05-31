/*
 * This file is part of aTetria.
 *
 * aTetria is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aTetria is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with aTetria. If not, see <http://www.gnu.org/licenses/>.
 */
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