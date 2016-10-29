import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;


public class VBOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Display.setDisplayMode(new DisplayMode(640,480));
			Display.create();
		} catch (Exception e) {
			e.printStackTrace();
		}
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 640, 480, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(8);
		buffer.put(new float[]{0,0,320,0,320,240,0,240});
		buffer.flip();
		FloatBuffer coord = BufferUtils.createFloatBuffer(8);
		coord.put(new float[]{0,0,1,0,1,1,0,1});
		coord.flip();
		/*FloatBuffer color = BufferUtils.createFloatBuffer(9);
		color.put(new float[]{1,1,1,1,1,1,1,1});
		color.flip();*/
		
		int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		int tbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tbo);
		glBufferData(GL_ARRAY_BUFFER, coord, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		/*int cbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, cbo);
		glBufferData(GL_ARRAY_BUFFER, color, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);*/
		Texture crate = null;
		try {
			crate = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/Crate.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		glBindTexture(GL_TEXTURE_2D, crate.getTextureID());
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			/*glBegin(GL_QUADS);
			glVertex2f(0,0);
			glVertex2f(640,0);
			glVertex2f(640,480);
			glVertex2f(0,480);
			glEnd();*/
			glBindTexture(GL_TEXTURE_2D, crate.getTextureID());
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glVertexPointer(2, GL_FLOAT, 0, 0L);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindBuffer(GL_ARRAY_BUFFER, tbo);
			glTexCoordPointer(2, GL_FLOAT, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			//glBindBuffer(GL_ARRAY_BUFFER, cbo);
			//glColorPointer(3, GL_FLOAT, 0, 0L);
			//glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			glDrawArrays(GL_QUADS, 0, 4);
			glDisableClientState(GL_VERTEX_ARRAY);
			glDisableClientState(GL_TEXTURE_COORD_ARRAY);
			glBindTexture(GL_TEXTURE_2D, 0);
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
}
