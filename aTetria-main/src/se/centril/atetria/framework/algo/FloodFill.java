package se.centril.atetria.framework.algo;

import java.util.ArrayDeque;
import java.util.Queue;

import se.centril.atetria.framework.geom.Direction;
import se.centril.atetria.framework.geom.MutablePosition;
import se.centril.atetria.framework.geom.Position;

import com.badlogic.gdx.Gdx;

/**
 * Generic implementation of flood fill algorithm.<br/>
 * Credit: {@link http://www.codecodex.com/wiki/Implementing_the_flood_fill_algorithm}
 *
 * @see http://en.wikipedia.org/wiki/Flood_fill
 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
 * @version 1.0
 * @since Jun 6, 2013
 */
public class FloodFill {
	/**
	 * Handler for flood fill algorithm.
	 *
	 * @author Centril<twingoow@gmail.com> / Mazdak Farrokhzad.
	 * @version 1.0
	 * @since Jun 6, 2013
	 */
	public interface FloodHandler {
		/**
		 * Handle a fill request.
		 *
		 * @param position the position to fill.
		 * @param direction the direction that the algorithm is currently searching in.
		 */
		void handle( Position position, Direction direction );

		/**
		 * Whether or not the given position meets the conditions of being filled.
		 *
		 * @param position the position to test for filling.
		 * @param direction the direction that the algorithm is currently searching in.
		 * @return true if it should be filled.
		 */
		boolean condition( Position position, Direction direction );
	}

	/**
	 * Performs a flood-fill handler when given a handler, start-position and dimensions of coordinate system.
	 *
	 * @param handler
	 * @param pos starting position in coordinate system.
	 * @param dim the dimensions of coordinate system.
	 */
	public static void floodFill( FloodHandler handler, Position pos, Position dim ) {
		floodFill( handler, pos.x(), pos.y(), dim.x(), dim.y() );
	}

	private static class Node {
		public Node( Position pos, Direction dir ) {
			this.position = pos;
			this.dir = dir;
		}

		private Direction dir;
		private Position position;

		public Direction dir() {
			return this.dir;
		}

		public Position position() {
			return this.position;
		}
	}

	public static void floodFill( FloodHandler handler, int x, int y, int width, int height ) {
		Gdx.app.debug( "FloodFill", "start" );

		Queue<Node> q = new ArrayDeque<Node>();

		q.add( new Node( new MutablePosition( x, y ), Direction.NONE ) );

		while ( !q.isEmpty() ) {
			Node n = q.poll();
			Position p = n.position();
			Direction d = n.dir();

			if ( handler.condition( p, d ) ) {
				Gdx.app.debug( "FloodFill", "handling: " + p );

				handler.handle( p, d );

				if ( p.x() - 1 >= 0 ) {
					push( q, p, Direction.WEST );
				}

				if ( p.x() + 1 < width ) {
					push( q, p, Direction.EAST );
				}

				if ( p.y() - 1 >= 0 ) {
					push( q, p, Direction.NORTH );
				}

				if ( p.y() + 1 < height ) {
					push( q, p, Direction.SOUTH );
				}
			}
		}

		Gdx.app.debug( "FloodFill", "end" );
	}

	private static void push( Queue<Node> q, Position p, Direction d ) {
		q.add( new Node( p.cpy().move( d ), d ) );
	}
}