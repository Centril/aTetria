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
