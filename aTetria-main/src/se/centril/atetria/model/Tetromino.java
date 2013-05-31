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
package se.centril.atetria.model;

import java.util.List;

import se.centril.atetria.framework.geom.FinalPosition;
import se.centril.atetria.framework.utils.factory.EnumKeyedSingletonFactory;
import se.centril.atetria.framework.utils.string.StringUtils;

import com.google.common.collect.Lists;

/**
 * Tetromino has the role of identifying a tetromino.<br/>
 * Each tetromino has a body accessible by {@link #getBody()}.
 */
public enum Tetromino {						// N rotations
	SQUARE			( "00	01	10	11" ),	// 1
	LINE			( "00	01	02	03" ),	// 2
	SIGMA			( "00	10	11	21" ),	// 2
	ZETA			( "01	11	10	20" ),	// 2
	TAU				( "00	10	11	20" ),	// 4
	LAMBDA			( "00	01	02	10" ),	// 4
	LAMBDA_MIRRORED	( "00	10	11	12" );	// 4

	public abstract static class ToFactory<V> extends EnumKeyedSingletonFactory<Tetromino, V> {
		protected Class<Tetromino> keyClass() { return Tetromino.class; }
	}

	/** Stores the body-string of a tetromino. */
	private final FinalPosition[] bodyPos;

	/**
	 * Constructs a Tetromino-identifier from a bodyString.
	 *
	 * @param bodyString String with (x, y) pairs in the form of: "xy xy xy xy".
	 */
	Tetromino( final String bodyString ) {
		this.bodyPos = makeBody( bodyString );
	}

	/**
	 * Returns the body positions.
	 *
	 * @return the body positions.
	 */
	public FinalPosition[] getBody() {
		return this.bodyPos;
	}

	/**
	 * Returns {@link #getBodyString()} in the form of a FinalPosition[] array.
	 * The string has (x, y) pairs in the form of: "xy xy xy xy".
	 *
	 * @return Resulting positions.
	 */
	public static FinalPosition[] makeBody( String string ) {
		List<FinalPosition> positions = Lists.newArrayList();

		try {
			for ( String part : StringUtils.WS_SPLITTER.split( string ) ) {
				positions.add( new FinalPosition(
					Integer.parseInt( part.substring( 0, 1 ) ),
					Integer.parseInt( part.substring( 1 ) ) ) );
			}

			if ( positions.isEmpty() ) {
				throw new RuntimeException( "Couldn't add any positions." );
			}
		} catch ( NumberFormatException e ) {
			throw new RuntimeException( "Couldn't parse (x, y) string:" + string );
		}

		return positions.toArray( new FinalPosition[0] );
	}
}