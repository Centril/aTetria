package se.centril.atetria.model.command;

/**
 * RotationCommand is a command for rotations.
 *
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since May 29, 2013
 */
public enum RotationCommand implements PieceCommand {
	CLOCKWISE {
		public RotationCommand inversion() {
			return COUNTER_CLOCKWISE;
		}
	},
	COUNTER_CLOCKWISE {
		public RotationCommand inversion() {
			return CLOCKWISE;
		}
	};

	/**
	 * Returns the inversion of the direction.
	 *
	 * @return the inversion of the direction.
	 */
	public abstract RotationCommand inversion();
}