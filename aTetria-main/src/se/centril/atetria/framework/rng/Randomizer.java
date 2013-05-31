package se.centril.atetria.framework.rng;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * See MersenneTwisterFast.
 *
 * @see MersenneTwisterFast
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @since 11 apr 2013
 * @version 1.0
 */
public interface Randomizer {

	/* We're overriding all internal data, to my knowledge, so this should be okay */
	public abstract Object clone();

	public abstract boolean stateEquals( Object o );

	/** Reads the entire state of the MersenneTwister RNG from the stream */
	public abstract void readState( DataInputStream stream ) throws IOException;

	/** Writes the entire state of the MersenneTwister RNG to the stream */
	public abstract void writeState( DataOutputStream stream ) throws IOException;

	/**
	 * Initalize the pseudo random number generator.  Don't
	 * pass in a long that's bigger than an int (Mersenne Twister
	 * only uses the first 32 bits for its seed).   
	 */

	public abstract void setSeed( long seed );

	/**
	 * Sets the seed of the MersenneTwister using an array of integers.
	 * Your array must have a non-zero length.  Only the first 624 integers
	 * in the array are used; if the array is shorter than this then
	 * integers are repeatedly used in a wrap-around fashion.
	 */

	public abstract void setSeed( int[] array );

	public abstract int nextInt();

	public abstract short nextShort();

	public abstract char nextChar();

	public abstract boolean nextBoolean();

	/** This generates a coin flip with a probability <tt>probability</tt>
	    of returning true, else returning false.  <tt>probability</tt> must
	    be between 0.0 and 1.0, inclusive.   Not as precise a random real
	    event as nextBoolean(double), but twice as fast. To explicitly
	    use this, remember you may need to cast to float first. */

	public abstract boolean nextBoolean( float probability );

	/** This generates a coin flip with a probability <tt>probability</tt>
	    of returning true, else returning false.  <tt>probability</tt> must
	    be between 0.0 and 1.0, inclusive. */

	public abstract boolean nextBoolean( double probability );

	public abstract byte nextByte();

	public abstract void nextBytes( byte[] bytes );

	public abstract long nextLong();

	/** Returns a long drawn uniformly from 0 to n-1.  Suffice it to say,
	    n must be > 0, or an IllegalArgumentException is raised. */
	public abstract long nextLong( long n );

	/** Returns a random double in the half-open range from [0.0,1.0).  Thus 0.0 is a valid
	    result but 1.0 is not. */
	public abstract double nextDouble();

	/** Returns a double in the range from 0.0 to 1.0, possibly inclusive of 0.0 and 1.0 themselves.  Thus:
	
	    <p><table border=0>
	    <th><td>Expression<td>Interval
	    <tr><td>nextDouble(false, false)<td>(0.0, 1.0)
	    <tr><td>nextDouble(true, false)<td>[0.0, 1.0)
	    <tr><td>nextDouble(false, true)<td>(0.0, 1.0]
	    <tr><td>nextDouble(true, true)<td>[0.0, 1.0]
	    </table>
	    
	    <p>This version preserves all possible random values in the double range.
	*/
	public abstract double nextDouble( boolean includeZero, boolean includeOne );

	public abstract double nextGaussian();

	/** Returns a random float in the half-open range from [0.0f,1.0f).  Thus 0.0f is a valid
	    result but 1.0f is not. */
	public abstract float nextFloat();

	/** Returns a float in the range from 0.0f to 1.0f, possibly inclusive of 0.0f and 1.0f themselves.  Thus:
	
	    <p><table border=0>
	    <th><td>Expression<td>Interval
	    <tr><td>nextFloat(false, false)<td>(0.0f, 1.0f)
	    <tr><td>nextFloat(true, false)<td>[0.0f, 1.0f)
	    <tr><td>nextFloat(false, true)<td>(0.0f, 1.0f]
	    <tr><td>nextFloat(true, true)<td>[0.0f, 1.0f]
	    </table>
	    
	    <p>This version preserves all possible random values in the float range.
	*/
	public abstract float nextFloat( boolean includeZero, boolean includeOne );

	/** Returns an integer drawn uniformly from 0 to n-1.  Suffice it to say,
	    n must be > 0, or an IllegalArgumentException is raised. */
	public abstract int nextInt( int n );

}