package com.waataja.utils.opengl;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import static com.waataja.utils.Files.*;
public class GLUtils {
	public static FloatBuffer asFloatBuffer(float[] list) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.length);
		buffer.put(list);
		buffer.flip();
		return buffer;
	}
	public FloatBuffer reserveData(int size) {
		return BufferUtils.createFloatBuffer(size);
	}
	public static float[] asFloats(Vector3f vector) {
		return new float[]{vector.x, vector.y, vector.z};
	}
	public static float[] asFloats(Vector3f[] vector) {
		float[] array = new float[vector.length * 3];
		for (int i = 0; i < vector.length; i++) {
			int index = 3 * i;
			array[index] = vector[i].x;
			array[index + 1] = vector[i].y;
			array[index + 2] = vector[i].y;
		}
		return array;
	}
	public static int createVBO(float[] points) {
		return createVBO(asFloatBuffer(points));
	}
	public static int createVBO(FloatBuffer buffer) {
		int handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return handle;
	}
	public static Texture loadPNGTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", inputStream(fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texture;
	}

	public static boolean continueDisplay() {
		return !Display.isCloseRequested();
	}
	public static void initOpenGLOrtho() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Display.getWidth(),Display.getHeight(),0,-1,1);
		glMatrixMode(GL_MODELVIEW);
	}
	public static void initOpenGLOrtho16Pixels() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth()/16f, Display.getHeight() / 16f, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
	}
	public static ArrayList<Texture> texturesIn(File folder) {
		ArrayList<Texture> textures = new ArrayList<Texture>();
		for (File file : filesIn(folder)) {
			textures.add(loadPNGTexture(file.getAbsolutePath()));
		}
		return textures;
	}
}
