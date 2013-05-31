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
package se.centril.atetria.framework.utils.collect;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Functions;
import com.google.common.collect.Ordering;

/**
 * ValueComparableMap imposes a total ordered TreeMap on VALUES instead of keys.
 * 
 * @see http://stackoverflow.com/questions/109383/how-to-sort-a-mapkey-value-on-the-values-in-java
 */
class ValueComparableMap<K extends Comparable<K>, V> extends TreeMap<K, V> {
	private static final long serialVersionUID = -8273811749543538940L;

	/**
	 * A map for doing lookups on the keys for comparison so we don't get infinite loops
	 */
	private final Map<K, V> valueMap;

	ValueComparableMap( final Ordering<? super V> partialValueOrdering ) {
		this( partialValueOrdering, new HashMap<K, V>() );
	}

	private ValueComparableMap( Ordering<? super V> partialValueOrdering, HashMap<K, V> valueMap ) {
		/*
		 * Apply the value ordering:
		 * On the result of getting the value for the key from the map
		 * as well as ensuring that the keys don't get clobbered.
		 */
		super( partialValueOrdering.onResultOf( Functions.forMap( valueMap ) ).compound( Ordering.natural() ) );
		this.valueMap = valueMap;
	}

	public V put( K k, V v ) {
		if ( valueMap.containsKey( k ) ) {
			// remove the key in the sorted set before adding the key again
			remove( k );
		}
		valueMap.put( k, v ); // To get "real" unsorted values for the comparator
		return super.put( k, v ); // Put it in value order
	}

	/*public static void main( String[] args ) {
		TreeMap<String, Integer> map = new ValueComparableMap<String, Integer>( Ordering.natural() );
		map.put( "a", 5 );
		map.put( "b", 1 );
		map.put( "c", 3 );
		assertEquals( "b", map.firstKey() );
		assertEquals( "a", map.lastKey() );
		map.put( "d", 0 );
		assertEquals( "d", map.firstKey() );
		// ensure it's still a map (by overwriting a key, but with a new value)
		map.put( "d", 2 );
		assertEquals( "b", map.firstKey() );
		// Ensure multiple values do not clobber keys
		map.put( "e", 2 );
		assertEquals( 5, map.size() );
		assertEquals( 2, ( int) map.get( "e" ) );
		assertEquals( 2, ( int) map.get( "d" ) );
	}*/
}
