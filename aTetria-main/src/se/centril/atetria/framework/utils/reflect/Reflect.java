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
package se.centril.atetria.framework.utils.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.centril.atetria.framework.utils.collect.Collections;

/**
 * Utility methods for reflection.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 24 apr 2013
 * @version 1.0
 */
public class Reflect {
	private Reflect() {
	}

	/**
	 * Tries to get Class<? extends T> object from a qualified class string.
	 *
	 * @param clazzString the class string.
	 * @param ifaceClazz the super-type Class<T>
	 * @return the Class<? extends T> object.
	 * @throws ClassNotFoundException When class is not found.
	 */
	public static <T> Class<? extends T> classForName( String clazzString, Class<T> ifaceClazz )
			throws ClassNotFoundException {
		return Class.forName( clazzString ).asSubclass( ifaceClazz );
	}

	/**
	 * Returns first declared method in clazz with name.
	 * 
	 * @param clazz the Class<?> object to find method in.
	 * @param name the name of method to find.
	 * @return the method with name or null.
	 */
	public static Method getFirstDeclaredMethod( Class<?> clazz, String name ) {
		return getFirstMethodsImpl( clazz.getDeclaredMethods(), name );
	}

	/**
	 * Returns first method in clazz with name.
	 * 
	 * @param clazz the Class<?> object to find method in.
	 * @param name the name of method to find.
	 * @return the method with name or null.
	 */
	public static Method getFirstMethod( Class<?> clazz, String name ) {
		return getFirstMethodsImpl( clazz.getMethods(), name );
	}

	/**
	 * Returns methods in clazz with name.
	 * 
	 * @param haystack the array of Method objects to find methods with name in.
	 * @param name the name of methods to find.
	 * @return the method with name or null.
	 */
	private static Method getFirstMethodsImpl( Method[] haystack, String name ) {
		for ( Method curr : haystack ) {
			if ( curr.getName().equals( name ) ) {
				return curr;
			}
		}

		return null;
	}

	/**
	 * Returns declared methods in clazz with name.
	 * 
	 * @param clazz the Class<?> object to find methods in.
	 * @param name the name of methods to find.
	 * @return the list of methods with name.
	 */
	public static List<Method> getDeclaredMethods( Class<?> clazz, String name ) {
		return getMethodsImpl( clazz.getDeclaredMethods(), name );
	}

	/**
	 * Returns methods in clazz with name.
	 * 
	 * @param clazz the Class<?> object to find methods in.
	 * @param name the name of methods to find.
	 * @return the list of methods with name.
	 */
	public static List<Method> getMethods( Class<?> clazz, String name ) {
		return getMethodsImpl( clazz.getMethods(), name );
	}

	/**
	 * Returns methods in clazz with name.
	 * 
	 * @param haystack the array of Method objects to find methods with name in.
	 * @param name the name of methods to find.
	 * @return the list of methods with name.
	 */
	private static List<Method> getMethodsImpl( Method[] haystack, String name ) {
		List<Method> methods = new ArrayList<Method>();

		for ( Method curr : haystack ) {
			if ( curr.getName().equals( name ) ) {
				methods.add( curr );
			}
		}

		return methods;
	}

	public static boolean isStatic( Method method ) {
		return Modifier.isStatic( method.getModifiers() );
	}

	/**
	 * Returns the most accepting method in clazz with name (null = any)
	 * and a list of parameter types of the most demanding method version,
	 * and whether or not the method is static or not.
	 *
	 * Not tested yet.
	 *
	 * @param clazz the Class<?> object to get method from.
	 * @param name the name of method - null = any.
	 * @param types the list of parameter types.
	 * @param retrParams a list to which the parameters of found method will be added to.
	 * @param demandStatic whether or not to demand a static method.
	 * @return the reflective method object.
	 */
	public static Method getMostAcceptingMethod( Class<?> clazz,
			String name, List<Class<?>> types, List<Class<?>> retrParams, boolean demandStatic ) {
		Method[] all = clazz.getDeclaredMethods();

		int acceptedStart = -1;
		List<Class<?>> acceptedParams = null;
		Method retr = null;

		for ( Method m : all ) {
			if ( // Check name if needed.
				(name != null && !m.getName().equals( name )) ||
				// Check static if needed.
				(demandStatic && !isStatic( m ))
			) {
				continue;
			}

			List<Class<?>> currParams = Arrays.asList( m.getParameterTypes() );

			int currStart = Collections.isOrderedSubList( types, currParams );
			if ( // Skip if currParams is not an ordered sub-list of types.
				 currStart == -1 ||
				 // Skip if not or lower size or not before.
				 (acceptedParams != null &&
				  (currParams.size() < acceptedParams.size() ||
				   (currParams.size() == acceptedParams.size() && currStart >= acceptedStart) ) ) ) {
				continue;
			}

			acceptedStart = currStart;
			acceptedParams = currParams;
			retr = m;
		}

		if ( acceptedParams != null ) {
			retrParams.addAll( acceptedParams );
		}

		return retr;
	}
}