package se.centril.atetria;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "aTetria";
		cfg.useGL20 = false;

		float cell = 60;
		float aspect = 0.5f;
		float height = 10 * cell;
		float width = aspect * height;
		

		cfg.width = (int) width;
		cfg.height = (int) height;
		
		new LwjglApplication(new ATetriaGame(), cfg);
	}
}
