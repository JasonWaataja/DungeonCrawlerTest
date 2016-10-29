package PracticeOpenGL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.*;

import java.nio.FloatBuffer;
import java.io.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class MultipleTextures1Vbo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Rendering Practice");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Texture crate = null;
		Texture dirt = null;
		try {
			dirt = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/textures/dirt.png")));
			crate = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/textures/textures/magic/fireR.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,1,1,0,-1,1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		
		float[] vertData = new float[]{0,0,.5f,.5f,0,.5f,0,0,.5f,.5f,.5f,0,
				.5f,.5f,1,1,1,.5f,.5f,.5f,1,1,.5f,1};
		float[] texsData = new float[]{0,0,1,1,0,1,0,0,1,1,1,0,0,0,1,1,0,1,0,0,1,1,1,0};
		FloatBuffer verts = BufferUtils.createFloatBuffer(vertData.length + texsData.length);
		for (int i = 0; i < 12; i++) {
			verts.put(vertData[2 * i]).put(vertData[2 * i + 1]);
			verts.put(texsData[2 * i]).put(texsData[2 * i + 1]);
		}
		verts.flip();
		
		int vertsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		

        
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			glBindTexture(GL_TEXTURE_2D, crate.getTextureID());

			
			glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
			glVertexPointer(2, GL_FLOAT, 4 * 4, 0);
			glTexCoordPointer(2, GL_FLOAT, 4 * 4, 2 * 4);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glDrawArrays(GL_TRIANGLES, 0, 6);
			
			glBindTexture(GL_TEXTURE_2D, dirt.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
			glVertexPointer(2, GL_FLOAT, 4 * 4, 6 * 2 * 4 + 6 * 2 * 4);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glDrawArrays(GL_TRIANGLES, 0, 6);
			
			glDisableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
			Display.update();
			Display.sync(60);
		}
		
		glDeleteBuffers(vertsVbo);
		crate.release();
		dirt.release();
		Display.destroy();
	}
}
