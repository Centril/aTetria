package se.centril.atetria.controller.input;

import se.centril.atetria.framework.math.VectorUtils;
import se.centril.atetria.model.command.HorizontalCommand;
import se.centril.atetria.model.command.RotationCommand;
import se.centril.atetria.model.command.VerticalCommand;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Handles inputs from Android and maps them to commands in game.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 28, 2013
 */
public class AndroidCommandProcessor extends InputAdapter implements CommandProcessor, GestureListener {
	private static final float FLING_ANGLE_ADJUSTMENT = VectorUtils.ANGLE_QUAD;

	private GestureDetector detector;
	private CommandReceiver receiver;
	private Rectangle bounds;

	private FlingThreshold dropThreshold;
	private FlingThreshold rotationThreshold;
	private RotationCommand defaultRotation;

	private float cellSize;

	private float panDeltaX = 0;
	private boolean inDownPan = false;

	{
		FlingThreshold threshold = new FlingThreshold( 10f, 30f );
		this.setDropThreshold( threshold );
		this.setRotationThreshold( threshold );

		this.setDefaultRotation( RotationCommand.COUNTER_CLOCKWISE );
	}

	public AndroidCommandProcessor() {
		this.detector = new GestureDetector( this );
		this.updateTapSize();
	}

	/* --------------------------------
	 * Setter public interface.
	 * --------------------------------
	 */

	@Override
	public AndroidCommandProcessor setCommandReceiver( CommandReceiver receiver ) {
		this.receiver = receiver;
		return this;
	}

	/**
	 * Sets the fling threshold for drop command in on-fling event.
	 *
	 * @param threshold the threshold.
	 */
	public AndroidCommandProcessor setDropThreshold( FlingThreshold threshold ) {
		this.dropThreshold = threshold;
		return this;
	}

	/**
	 * Sets the fling threshold for default-inverse rotation command in on-fling event.
	 *
	 * @param threshold the threshold.
	 */
	public AndroidCommandProcessor setRotationThreshold( FlingThreshold threshold ) {
		this.rotationThreshold = threshold;
		return this;
	}

	/**
	 * Sets the default rotation direction.<br/>
	 * The initial default is CCW.
	 *
	 * @param direction the direction.
	 */
	public AndroidCommandProcessor setDefaultRotation( RotationCommand direction ) {
		this.defaultRotation = direction;
		return this;
	}

	/* --------------------------------
	 * Implementations.
	 * --------------------------------
	 */

	private static final float PAN_UP_IGNORE_ANGLE = 30f;
	private static final float PAN_DOWN_THRESHOLD_ANGLE = 30f;
	private static final float PAN_UP_ABORT_THRESHOLD = 10f;

	@Override
	public boolean pan( float x, float y, float dx, float dy ) {
		Vector2 delta = new Vector2( dx, dy );

		float angle = delta.angle();

		if ( VectorUtils.isAngleWithin( FLING_ANGLE_ADJUSTMENT, angle, PAN_UP_IGNORE_ANGLE ) ) {
			// Do "nothing" if upwards pan. Abort down pan if needed.
			if ( this.inDownPan && dy > PAN_UP_ABORT_THRESHOLD ) {
				this.panDeltaX = 0;
				this.reset();
			}
		} else if ( VectorUtils.isAngleWithin( 3 * FLING_ANGLE_ADJUSTMENT, angle, PAN_DOWN_THRESHOLD_ANGLE ) ) {
			if ( !this.inDownPan ) {
				this.inDownPan = true;
				this.receiver.onCommandStart( VerticalCommand.DOWN );
			}

			this.receiver.onCommandReceive( VerticalCommand.DOWN );
		} else {
			// We've horizontal movement.
			this.endDown();

			this.panDeltaX += dx;

			if ( Math.abs( this.panDeltaX ) >= this.cellSize ) {
				HorizontalCommand cmd = this.panDeltaX < 0 ? HorizontalCommand.LEFT : HorizontalCommand.RIGHT;
				this.panDeltaX = 0;
				this.receiver.onCommandReceive( cmd );
			}
		}

		return false;
	}

	private void endDown() {
		if ( this.inDownPan ) {
			this.inDownPan = false;
			this.receiver.onCommandEnd( VerticalCommand.DOWN );
		}
	}

	private void reset() {
		this.endDown();
		this.detector.reset();
	}

	@Override
	public boolean tap( float x, float y, int count, int button ) {
		if ( !bounds.contains( x, y ) ) {
			// Don't handle.
			return false;
		}

		// We've got a tap inside the bounds, rotate!
		this.reset();
		this.receiver.onCommandReceive( this.defaultRotation );
		return true;
	}

	@Override
	public boolean fling( float velocityX, float velocityY, int button ) {
		Vector2 velocity = new Vector2( velocityX, velocityY );

		float angle = velocity.angle();

		if ( this.rotationThreshold.isReached( FLING_ANGLE_ADJUSTMENT, angle, velocityY ) ) {
			this.reset();
			this.receiver.onCommandReceive( this.defaultRotation.inversion() );
			return true;
		} else if ( this.dropThreshold.isReached( 3 * FLING_ANGLE_ADJUSTMENT, angle, velocityY ) ) {
			this.reset();
			this.receiver.onCommandReceive( VerticalCommand.DROP );
			return true;
		}

		return false;
	}

	/**
	 * Updates the tap-size according to the specified bounds.
	 */
	private void updateTapSize() {
		this.detector.setTapSquareSize( (int) Math.ceil( Math.max( bounds.getWidth(), bounds.getHeight() ) ) );
	}

	/* --------------------------------
	 * Unhandled.
	 * --------------------------------
	 */

	@Override
	public boolean touchDown( float x, float y, int pointer, int button ) {
		return false;
	}

	@Override
	public boolean longPress( float x, float y ) {
		return false;
	}

	@Override
	public boolean zoom( float initialDistance, float distance ) {
		return false;
	}

	@Override
	public boolean pinch( Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2 ) {
		return false;
	}

	/* --------------------------------
	 * Delegation.
	 * --------------------------------
	 */

	@Override
	public final boolean touchDown( int x, int y, int pointer, int button ) {
		return this.detector.touchDown( x, y, pointer, button );
	}

	@Override
	public final boolean touchUp( int x, int y, int pointer, int button ) {
		return this.detector.touchUp( x, y, pointer, button );
	}

	@Override
	public final boolean touchDragged( int x, int y, int pointer ) {
		return this.detector.touchDragged( x, y, pointer );
	}
}