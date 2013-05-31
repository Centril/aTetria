package se.centril.atetria.framework.rng;

/**
 * RandomizerUtilizer simply tells us that an object requires a Randomizer.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 8, 2013
 */
public interface RandomizerUtilizer {
	/**
	 * Sets the randomizer/PRNG.
	 *
	 * @param rng the randomizer/PRNG.
	 */
	public void setRandomizer( Randomizer rng );
}