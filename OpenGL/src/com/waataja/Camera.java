package com.waataja;
import static org.lwjgl.opengl.GL11.*;
import static com.waataja.Utils.*;
public class Camera {
	private CrateWatcher game;
	public Camera(CrateWatcher watcher) {
		game = watcher;
	}
	public void applyTranslation() {
		glLoadIdentity();
		glRotatef(game.getRotation().x, 1, 0, 0);
		glRotatef(game.getRotation().y, 0, 1, 0);
		glRotatef(game.getRotation().z, 0, 0, 1);
		glTranslatef(-game.getPosition().x, -game.getPosition().y, -game.getPosition().z);
	}
}
