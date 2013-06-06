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
package se.centril.atetria.view;

import se.centril.atetria.framework.gdx.BaseGdxView;
import se.centril.atetria.framework.geom.Position;
import se.centril.atetria.framework.utils.factory.Factory;
import se.centril.atetria.model.Board;
import se.centril.atetria.model.Game;
import se.centril.atetria.model.Piece;
import se.centril.atetria.model.Tetromino;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GameView extends BaseGdxView {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	private ShapeRenderer shapeRenderer;

	private Factory<Tetromino, Color> colorMapper;

	private final Game game;

	private float cellSize;

	private Color bgColor = new Color( 0.2f, 0.2f, 0.2f, 1 );
	private Color gridLineColor = new Color( 0.1f, 0.1f, 0.1f, 1 );

	// Work objects.
	private final Vector2 temp = new Vector2();

	public GameView( Game game ) {
		this.game = game;

		colorMapper = new ColorMapper();

		// Stuff.
		float w = graphics().getWidth();
		float h = graphics().getHeight();

		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);

		shapeRenderer = new ShapeRenderer();
	}

	private void updateCellSize() {
		cellSize = camera.viewportHeight / this.game.getBoard().getHeight();
	}

	private Position dim() {
		return this.game.getBoard().getSize();
	}

	@Override
	public void render() {
		this.updateCellSize();

		/*
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		sprite.draw(batch);

		batch.end();
		*/

		shapeRenderer.setProjectionMatrix( camera.combined );


		//logger().debug( "begin");

		this.renderBg();

		this.renderBricks();

		this.renderGridLines();

		//logger().debug( "end");
	}

	private void renderBricks() {
		Board board = this.game.getBoard();
		Position dim = board.getSize();

		basify();

		// 
		shapeRenderer.begin( ShapeType.Filled );
		for ( int x = 0; x < dim.x(); x++ ) {
			for ( int y = 0; y < dim.y(); y++ ) {
				Piece piece = board.getState( x, y );

				if ( piece == Board.EMPTY ) {
					continue;
				}

				Color color = colorMapper.get( piece.getType() );

				Vector2 vec = temp.cpy().add( x * cellSize, y * cellSize );

				shapeRenderer.setColor( color );
				shapeRenderer.rect( vec.x, vec.y, cellSize, cellSize );

				//vec.add( 0, cellSize );
			}

			//vec.add( cellSize, 0 );
		}
		shapeRenderer.end();
	}

	private void basify() {
		temp.set( -camera.viewportWidth / 2, -camera.viewportHeight / 2 );
	}

	private void renderBg() {
		this.basify();

		Position dim = dim();

		// Filled cells.
		shapeRenderer.begin( ShapeType.Filled );
		shapeRenderer.setColor( bgColor );
		for ( int x = 0; x < dim.x(); x++ ) {
			for ( int y = 0; y < dim.y(); y++ ) {
				Vector2 vec = temp.cpy().add( x * cellSize, y * cellSize );

				shapeRenderer.rect( vec.x, vec.y, cellSize, cellSize );
			}
		}
		shapeRenderer.end();
	}

	private void renderGridLines() {
		Position dim = dim();

		// Grid-lines.
		shapeRenderer.begin( ShapeType.Line );
		shapeRenderer.setColor( gridLineColor );

		// vertical.
		for ( int x = 0; x < dim.x(); x++ ) {
			Vector2 vec = temp.cpy().add( x * cellSize, 0 );
			shapeRenderer.line( vec.x, vec.y, vec.x, vec.y + camera.viewportHeight );
		}

		// horizontal.
		for ( int y = 0; y < dim.y(); y++ ) {
			Vector2 vec = temp.cpy().add( 0, y * cellSize );
			shapeRenderer.line( vec.x, vec.y, vec.x + camera.viewportWidth, vec.y );
		}
		shapeRenderer.end();
	}

	public void dispose() {
		// TODO view needs dispose.
		batch.dispose();
		texture.dispose();
		shapeRenderer.dispose();
	}
}