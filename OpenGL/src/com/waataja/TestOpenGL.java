package com.waataja;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;
import static com.waataja.Utils.*;
public class TestOpenGL {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		createDisplay(1280,768);
		initOpenGLOrtho();
		enableTextures();
		Texture crateTexture = loadPNGTexture("res/textures/Crate.png");
		while (keepLooping()) {
			beginLoop();
			
			bindTexture(crateTexture);
			triangles();
			tex(0,0);
			plot(0,0);
			tex(1,0);
			plot(100,0);
			tex(0,1);
			plot(0,100);
			end();
			unbindTexture();
			
			
			endLoop();
		}
		cleanUp();
	}

}
