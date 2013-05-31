package se.centril.atetria;

import se.centril.atetria.controller.GameController;
import se.centril.atetria.framework.gdx.GdxFrontController;
import se.centril.atetria.framework.mvc.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;

public class ATetriaGame extends GdxFrontController {
	private static final String TITLE = "ATetria";
	private static final int LOG_LEVEL = Logger.DEBUG;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;

	public ATetriaGame() {
		super();
	}

	@Override
	public Controller getInitController() {
		return new GameController();
	}

	@Override
	public void bootstrap() {
		this.logger( new Logger( TITLE, LOG_LEVEL ) );
		Gdx.graphics.setTitle( TITLE );

		// Fine tuning.
		Gdx.graphics.setVSync( true );

		// Stuff.
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
	}

	@Override
	public void preRender() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void postRender() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}

	@Override
	public void dispose() {
		super.dispose();

		batch.dispose();
		texture.dispose();
	}
}
