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

import se.centril.atetria.framework.utils.reflect.DAOUtil;
import se.centril.atetria.framework.utils.reflect.Reflect;
import se.centril.atetria.framework.utils.string.StringUtils;

/**
 * StringLookupFactory adds some fallback mechanisms,<br/>
 * based on reflection on top of AbstractFactory<String, V>.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 8, 2013
 */
public abstract class StringLookupFactory<V> extends AbstractFactory<String, V> {
	/**
	 * Returns the default package to use when non-fully-qualified fallback.
	 *
	 * @return the default package, fully qualified.
	 */
	protected abstract String getDefaultPackage();

	public V produce( String key ) {
		// Take "" as being null key, meaning default instantiator.
		if ( key != null && key.length() == 0 ) {
			key = null;
		}

		// Produce the value.
		V val = super.produce( key );

		/*
		 * Not found -> try to use reflection to recover.
		 * If not fully qualified, qualify!
		 * If not found for some reason, we're toast.
		 */
		if ( val == null ) {
			if ( key.indexOf( '.' ) == -1 ) {
				key = StringUtils.QUALIFIER_JOINER.join( this.getDefaultPackage(), key );
			}

			try {
				@SuppressWarnings( "unchecked" )
				Class<V> valClazz = ( Class<V>) DAOUtil.getTypeArguments( StringLookupFactory.class, this.getClass() ).get( 0 );

				val = Reflect.classForName( key, valClazz ).newInstance();
			} catch ( InstantiationException e ) {
			} catch ( IllegalAccessException e ) {
			} catch ( ClassNotFoundException e ) {
			}
		}

		return val;
	}
}