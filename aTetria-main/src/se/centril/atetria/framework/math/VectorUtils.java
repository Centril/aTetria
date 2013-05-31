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
package se.centril.atetria.framework.math;

/**
 * Utilities for vectors.<br/>
 * All angle operations are in degrees if not otherwise specified.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public class VectorUtils {
	private VectorUtils() {
	}

	public static float ANGLE_FULL = 360;
	public static float ANGLE_HALF = 180;
	public static float ANGLE_QUAD = 90;

	/**
	 * Checks if 2 angles are opposite of each other with a fuzziness given by delta.<br/>
	 * If delta = 0, use {@link #isAngleOpposite(float, float)}.
	 *
	 * @param angle the origin angle.
	 * @param opposite the potential opposite.
	 * @param delta the delta that makes up fuzziness.
	 * @return true if they are opposite of each other.
	 */
	public static boolean isAngleOpposite( float angle, float opposite, float delta ) {
		return isAngleWithin( angleOpposite( angle ), opposite, delta );
	}

	/**
	 * Checks if 2 angles are opposite of each other.<br/>
	 * If fuzziness is required, use {@link #isAngleOpposite(float, float, float)}.
	 *
	 * @param angle the angle.
	 * @param opposite the potential opposite.
	 * @return true if they are opposite of each other.
	 */
	public static boolean isAngleOpposite( float angle, float opposite ) {
		return opposite == angleOpposite( angle );
	}

	/**
	 * Returns the opposite of a given angle.
	 *
	 * @param angle the angle to get opposite of.
	 * @return the opposite angle.
	 */
	public static float angleOpposite( float angle ) {
		return normalizeAngle( angle + ANGLE_HALF );
	}

	/**
	 * Checks if the other angle is in range of angle with a given delta.
	 *
	 * @param required the required angle.
	 * @param angle the angle to check.
	 * @param delta the delta the allowed range.
	 * @return true if other is within angle.
	 */
	public static boolean isAngleWithin( float required, float angle, float delta ) {
		float[] range = angleRangePair( required, delta );
		return range[0] <= angle && angle <= range[1];
	}

	/**
	 * Returns the range pair of normalized angles from angle with a delta.
	 *
	 * @param angle the origin angle.
	 * @param delta the delta to make out range from.
	 * @return the normalized angle pair, 0 = low, 1 = high.
	 */
	public static float[] angleRangePair( float angle, float delta ) {
		float low = normalizeAngle( angle - delta );
		float high = normalizeAngle( angle + delta );

		if ( low > high ) {
			float tmp = low;
			low = high;
			high = tmp;
		}

		return new float[] { low, high };
	}

	/**
	 * Guarantees a positive angle in degrees.<br/>
	 * Faster than {@link #normalizeAngle(float)} when angle can't be > 360.
	 *
	 * @param angle angle to normalize.
	 * @return the positive angle.
	 */
	public static float positiveAngle( float angle ) {
		return angle < 0 ? angle + ANGLE_FULL : angle;
	}

	/**
	 * Normalizes angle in degrees.
	 *
	 * @param angle the angle to normalized.
	 * @return the normalized angle.
	 */
	public static float normalizeAngle( float angle ) {
		return (angle + ANGLE_FULL) % ANGLE_FULL;
	}

	/**
	 * Guarantees a positive angle in radians.<br/>
	 * Faster than {@link #normalizeAngleRad(double)} when angle can't be > 360.
	 *
	 * @param angle angle to normalize.
	 * @return the positive angle.
	 */
	public static double positiveAngleRad( double angle ) {
		return angle < 0 ? angle + 2 * Math.PI : angle;
	}

	/**
	 * Normalizes angle in radians.
	 *
	 * @param angle the angle to normalized.
	 * @return the normalized angle.
	 */
	public static double normalizeAngleRad( double angle ) {
		return (angle + 2 * Math.PI) % Math.PI;
	}
}