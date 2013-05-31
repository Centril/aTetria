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

import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import se.centril.atetria.framework.rng.Randomizer;

import com.google.common.base.Objects;

/**
 * Utilities for collections.
 * 
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 16 apr 2013
 * @version 1.0
 */
public class Collections {
	/**
	 * Shuffles a collection using a Randomizer object
	 *
	 * @see java.utils.Collections#shuffle(List<?> list, Random random) on Android implementation.
	 * @param list the list to shuffle
	 * @param random randomizer.
	 */
	public static void shuffle( List<?> list, Randomizer random ) {
        @SuppressWarnings("unchecked") // we won't put foreign objects in
        final List<Object> objectList = (List<Object>) list;

        if (list instanceof RandomAccess) {
            for (int i = objectList.size() - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                objectList.set(index, objectList.set(i, objectList.get(index)));
            }
        } else {
            Object[] array = objectList.toArray();
            for (int i = array.length - 1; i > 0; i--) {
                int index = random.nextInt(i + 1);
                Object temp = array[i];
                array[i] = array[index];
                array[index] = temp;
            }

            int i = 0;
            ListIterator<Object> it = objectList.listIterator();
            while (it.hasNext()) {
                it.next();
                it.set(array[i++]);
            }
        }
	}

	/**
	 * <p>Checks if subList is an ordered sub-list of superList.<br/>
	 * Returned is the index of the first matching element, or -1 if not a subList.</p>
	 *
	 * <p>Definition: <code>SL( A )</code> for a list A = the set of all sub-lists.<br/>
	 * For example: <pre>
	 * SL( [a, a, b, c] ) = {
	 *	[a], [a, a], [a, a, b], [a, a, b, c],
	 *	[a, b], [a, b, c],
	 *	[b], [b, c]
	 *	[c]
	 * }</pre>
	 *
	 * <p>Assume a super list A, and a sub list B.<br/>
	 * Definition: <code>|A|</code> is the size of A ( A.size() ).<br/>
	 * Given these assumptions, the method will in the:<br/>
	 * <ul>
	 * 	<li>best case operate in: <strong><code>O( |A| - |B| ).</code></strong></li>
	 * 	<li>worst case operate in: <strong><code>O( Σ( i = 0 to |A| - |B| of F(x) ) ),
	 * F(x) = |A| - i</code></strong></li>
	 * </ul>
	 *
	 * <p><strong>Note</strong>: The empty set <code>Ø</code> is mathematically always<br/>
	 * an ordered sub-list, but not in this method.</p>
	 *
	 * @param superList the superList.
	 * @param subList the subList.
	 * @return The index of the first matching element in subList, or -1 if not a subList.
	 */
	public static <T> int isOrderedSubList( List<T> superList, List<T> subList ) {
		int superSize = superList.size(),
			subSize = subList.size(),
			diff = superSize - subSize;

		if ( subSize == 0 || diff < 0 ) {
			// Quit early if sub can't be sub of super due to size.
			return -1;
		} else if ( diff == 0 ) {
			// Same size => use equals(...).
			return superList.equals( subList ) ? 0 : -1;
		}

		/*
		 * Best: O( |super| - |sub|) ).
		 * Worst: sigma(|super| - |sub|) of f(x) = |super| - x. 
		 */

		// Iterate over the size diff.
		for ( int i = 0; i < diff; i++ ) {
			int found = 0;

			List<T> currView = superList.subList( i, superSize );
			for ( int k = 0; k < currView.size(); k++ ) {
				// This iteration ain't right if objects ain't equal.
				if ( !Objects.equal( currView.get( k ), subList.get( i ) ) ) {
					break;
				}

				found++;

				// The whole list was found, so all of subList is in universe.
				if ( found == subSize ) {
					return i;
				}
			}
		}

		return -1;
	}
}