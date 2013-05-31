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