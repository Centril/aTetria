package se.centril.atetria.framework.utils.string;

import java.io.File;
import java.util.Locale;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Static utilities for string operations.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 19 apr 2013
 * @version 1.0
 */
public class StringUtils {
	/** Splitter for whitespace. */
	public static final Splitter WS_SPLITTER = Splitter.onPattern( "\\s" ).omitEmptyStrings().trimResults();

	/** Splitter for directory paths. */
	public static final Splitter COMMA_SPLITTER = Splitter.on( ',' ).omitEmptyStrings().trimResults();

	/** Splitter for directory paths. */
	public static final Splitter DIRECTORY_SPLITTER = Splitter.on( File.separatorChar );

	/** Pair splitter for directory paths. */
	public static final Splitter PAIR_DIRECTORY_SPLITTER = DIRECTORY_SPLITTER.limit( 2 );

	/** Joiner for directory paths. */
	public static final Joiner DIRECTORY_JOINER = Joiner.on( File.separatorChar ).skipNulls();

	/** Joiner for slugs. */
	public static final Joiner SLUG_JOINER = Joiner.on( '_' ).skipNulls();

	/** Joiner for slugs. */
	public static final char PACKAGE_SEPARATOR = '.';

	/** Joiner for slugs. */
	public static final Joiner QUALIFIER_JOINER = Joiner.on( PACKAGE_SEPARATOR ).skipNulls();

	/**
	 * "Casts" a string to uppercase.
	 *
	 * @param string the string to uppercase.
	 * @return the string, uppercased.
	 */
	public static String castUpper( String string ) {
		return string.toUpperCase( Locale.getDefault() );
	}

	/**
	 * "Casts" a string to lowercase.
	 *
	 * @param string the string to lowercase.
	 * @return the string, lowercase.
	 */
	public static String castLower( String string ) {
		return string.toLowerCase( Locale.getDefault() );
	}

	/**
	 * Capitalizes the first letter of a string.
	 *
	 * @param string the string to capitalize.
	 * @return the capitalized string.
	 */
	public static String capitalizeFirst( String string ) {
		return string.substring( 0, 1 ).toUpperCase( Locale.getDefault() ) + string.substring( 1 ).toLowerCase( Locale.getDefault());
	}

	/**
	 * Reads a hex-value-string, into a natural number, removes 0x / # prefixes.
	 *
	 * @param value the value string.
	 * @return natural read number.
	 */
	public static Integer readHexString( String value ) {
		if ( value.charAt( 0 ) == '#' ) {
			value = value.substring( 1 );
		} else if ( value.startsWith( "0x" ) ) {
			value = value.substring( 2 );
		}

		return Integer.parseInt( value, 16 );
	}
}