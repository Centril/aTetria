package se.centril.atetria.controller.input;

import se.centril.atetria.framework.math.VectorUtils;

/**
 * <p>FlingThreshold pieces together the vertical velocity threshold<br/>
 * and the maximum allowed angle of velocity for on-fling events.</p>
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public class FlingThreshold {
	private float threshold;
	private float maxAngle;

	/**
	 * Constructs the threshold with properties not set.<br/>
	 * {@link #setThreshold(float)} & {@link #setMaxAngle(float)} must be called afterwards.
	 */
	public FlingThreshold() {
	}

	/**
	 * Constructs the threshold given vertical velocity threshold & maxAngle.
	 * 
	 * @param threshold the threshold for vertical velocity to use.
	 * @param maxAngle the max angle between horizontal & vertical velocities allowed.
	 */
	public FlingThreshold( float threshold, float maxAngle ) {
		this.threshold = threshold;
		this.setMaxAngle( maxAngle );
	}

	/**
	 * Sets the threshold for vertical velocity to use.
	 *
	 * @param threshold the threshold for vertical velocity to use.
	 */
	public void setThreshold( float threshold ) {
		this.threshold = threshold;
	}

	/**
	 * Sets the max angle.
	 *
	 * @param maxAngle the max angle between horizontal & vertical velocities allowed.
	 */
	public void setMaxAngle( float maxAngle ) {
		this.maxAngle = maxAngle;
	}

	/**
	 * Returns true if threshold is reached.
	 *
	 * @param originAngle the angle of velocity.
	 * @param requiredAngle the required angle.
	 * @param velocityY the vertical velocity.
	 * @return true if threshold is reached.
	 */
	public boolean isReached( float originAngle, float requiredAngle, float velocityY ) {
		// Reached if angle is vertical enough & vertical velocity is enough.
		return this.verticalInRange( velocityY ) && this.angleInRange( originAngle, requiredAngle );
	}

	/**
	 * Returns true if vertical velocity is in range.
	 *
	 * @param velocityY the vertical velocity.
	 * @return true if in range.
	 */
	protected boolean verticalInRange( float velocityY ) {
		return velocityY >= this.threshold;
	}

	/**
	 * Returns true if angle is in range.
	 *
	 * @param originAngle the angle of velocity.
	 * @param requiredAngle the required angle.
	 * @return true if in range.
	 */
	protected boolean angleInRange( float originAngle, float requiredAngle ) {
		return VectorUtils.isAngleWithin( originAngle, requiredAngle, this.maxAngle );
	}
}