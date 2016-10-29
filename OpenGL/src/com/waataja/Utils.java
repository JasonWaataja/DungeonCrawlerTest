package com.waataja;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static java.lang.Math.*;
public class Utils {
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
	public static Vector3f vec(float x, float y, float z) {
		return new Vector3f(x,y,z);
	}
	public static Vector2f vec(float x, float y) {
		return new Vector2f(x,y);
	}
	public static Vector3f vec3() {
		return new Vector3f();
	}
	public static Vector2f vec2() {
		return new Vector2f();
	}
	public static void plot(float x, float y, float z) {
		glVertex3f(x,y,z);
	}
	public static void plot(Vector3f vector) {
		plot(vector.x, vector.y, vector.z);
	}
	public static void plot(Vector2f vec) {
		plot(vec.x, vec.y);
	}
	public static void plot(float x, float y) {
		glVertex2f(x, y);
	}
	public static void tex(float x, float y) {
		glTexCoord2f(x, y);
	}
	public static void tex(Vector2f coord) {
		tex(coord.x, coord.y);
	}
	public static void texPlot(float texX, float texY, float pointX, float pointY, float pointZ) {
		tex(texX, texY);
		plot(pointX, pointY, pointZ);
	}
	public static void texPlot(float texX, float texY, Vector3f point) {
		texPlot(texX, texY, point.x, point.y, point.z);
	}
	public static void texPlot(Vector2f tex, Vector3f point) {
		tex(tex);
		plot(point);
	}
	public static void texPlot(Vector2f tex, float x, float y, float z) {
		tex(tex);
		plot(x,y,z);
	}
	public static void texPlot(float texX, float texY, float pointX, float pointY) {
		tex(texX, texY);
		plot(pointX, pointY);
	}
	public static void texPlot(Vector2f tex, float x, float y) {
		tex(tex);
		plot(x,y);
	}
	public static void texPlot(Vector2f tex, Vector2f point) {
		tex(tex);
		plot(point);
	}
	public static void textPlot(float x, float y, Vector2f point) {
		tex(x, y);
		plot(point);
	}
	public static void color(float x, float y, float z) {
		glColor3f(x,y,z);
	}
	public static void color(Vector3f vec) {
		color(vec.x, vec.y, vec.z);
	}
	public static void resetColor() {
		color(1,1,1);
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
	public static FileInputStream inputStream(String fileName) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	public static boolean isKey(int key) {
		return Keyboard.isKeyDown(key);
	}
	public static boolean leftMouse() {
		return Mouse.isButtonDown(0);
	}
	public static boolean rightMouse() {
		return Mouse.isButtonDown(1);
	}
	public static Texture loadPNGTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", inputStream(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return texture;
	}
	public static boolean continueDisplay() {
		return !Display.isCloseRequested();
	}
	public static void createDisplay(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static void initOpenGLOrtho() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,Display.getWidth(),Display.getHeight(),0,-1,1);
		glMatrixMode(GL_MODELVIEW);
	}
	public static void createDisplay(String name, int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(name);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	public static Vector3f rotateX(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				1,0,0,
				0,(float) cos(x),(float) sin(x),
				0,(float) -sin(x),(float) cos(x)
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateX(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateX(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static Vector3f rotateY(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				(float) cos(x),0,(float) -sin(x),
				0,1,0,
				(float) sin(x),0,(float) cos(x)
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateY(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateY(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static Vector3f rotateZ(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				(float) cos(x),(float) sin(x),0,
				(float) -sin(x),(float) cos(x),0,
				0,0,1
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateZ(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateZ(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static float scaledToTime(float number, float milliseconds) {
		return number * milliseconds / 1000;
	}
	public static Vector3f scaledToTime(Vector3f vector, float milliseconds) {
		return scaled(vector, milliseconds / 1000);
	}
	public static Vector2f scaledToTime(Vector2f vector, float milliseconds) {
		return scaled(vector, milliseconds / 1000);
	}
	public static Vector3f scaled(Vector3f vector, float scale) {
		Vector3f newVector = new Vector3f(vector);
		/*newVector.x = vector.x * scale;
		newVector.y = vector.y * scale;
		newVector.z = vector.z * scale;*/
		newVector.scale(scale);
		return newVector;
	}
	public static Vector2f scaled(Vector2f vector, float scale) {
		/*Vector2f newVector = new Vector2f(vector);
		newVector.scale(scale);
		return newVector;*/
		return mul(vector, scale);
	}
	public static boolean isOnScreen() {
		if (Mouse.getX() > 0 && Mouse.getX() < Display.getWidth() && Mouse.getY() > 0 && Mouse.getY() < Display.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	public static void updateDisplay(int fps) {
		Display.update();
		Display.sync(fps);
	}
	public static TriangleFace[] splitQuad(int[] vecs, int[] texs) {
		TriangleFace[] faces = new TriangleFace[2];
		faces[0] = new TriangleFace(vec(vecs[0],vecs[1],vecs[2]), vec(texs[0],texs[1],texs[2]));
		//faces[0] = new TriangleFace(vec(vecs[0],vecs[3],vecs[2]), vec(texs[0],texs[3],texs[2]));
		faces[1] = new TriangleFace(vec(vecs[0],vecs[3],vecs[2]), vec(texs[0],texs[3],texs[2]));
		//faces[1] = new TriangleFace(vec(vecs[0],vecs[1],vecs[2]), vec(texs[0],texs[1],texs[2]));
		return faces;
	}
	public static Vector3f constructDirection(Vector3f rotation) {
		Vector3f vector = vec(0,0,-1);
		rotateX(vector, -rotation.x, vector);
		rotateY(vector, -rotation.y, vector);
		rotateZ(vector, -rotation.z, vector);
		vector.normalise();
		return vector;
	}
	public static Vector3f translate(Vector3f vector, Vector3f translation) {
		Vector3f newVector = new Vector3f();
		Vector3f.add(vector, translation, newVector);
		return newVector;
	}
	public static boolean keepLooping() {
		return !Display.isCloseRequested();
	}
	public static void beginLoop() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	public static void endLoop() {
		Display.update();
		Display.sync(60);
	}
	public static void bindTexture(Texture texture) {
		try {
			glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		} catch (Exception e) {
			
		}
	}
	public static void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	public static String combine(String[] words) {
		String str = "";
		for (String word : words) {
			str = str + word;
		}
		return str;
	}
	public static String append(String str, String suf) {
		return str + suf;
	}
	public static String appendFile(String str, String suf) {
		return str + "/" + suf;
	}
	public static String after(String str, String start) {
		return str.substring(start.length() + 1);
	}
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	public static float toFloat(String string) {
		return Float.valueOf(string);
	}
	public static int toInt(String string) {
		return Integer.valueOf(string);
	}
	public static void cleanUp() {
		Display.destroy();
		System.exit(0);
	}
	public static void enableTextures() {
		glEnable(GL_TEXTURE_2D);
	}
	public static void triangles() {
		glBegin(GL_TRIANGLES);
	}
	public static void quads() {
		glBegin(GL_QUADS);
	}
	public static void end() {
		glEnd();
	}
	public static void initOpenGLOrtho16Pixels() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth()/16f, Display.getHeight() / 16f, 0, -1, 1);
	}
	public static int toPixelLength(float number) {
		return (int) (number * 16);
	}
	public static Vector2f toPixels(Vector2f vector) {
		return vec(toPixelLength(vector.x),toPixelLength(vector.y));
	}
	public static float toBlockLength(int pixels) {
		return pixels / 16f;
	}
	public static Vector2f toBlocks(Vector2f vector) {
		return vec(toBlockLength((int)vector.x),(int)toBlockLength((int)vector.y));
	}
	public static Vector2f add(Vector2f vec1, Vector2f vec2) {
		return Vector2f.add(vec1, vec2, vec2());
	}
	public static Vector2f sub(Vector2f vec1, Vector2f vec2) {
		return Vector2f.sub(vec1, vec2, vec2());
	}
	public static Vector2f mul(Vector2f vector, float scale) {
		return vec(vector.x * scale, vector.y * scale);
	}
	public static Vector2f divide(Vector2f vec, float number) {
		return mul(vec, 1f / number);
	}
	public void setLength(Vector2f vec, float length) {
		vec = mul(vec, length / vec.length());
	}
	public static Vector2f withLength(Vector2f vec, float length) {
		return mul(vec, (float)length / vec.length());
	}
	public static ArrayList<File> filesIn(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				files.addAll(filesIn(file));
			}
		}
		return files;
	}
	public static ArrayList<Texture> texturesIn(File folder) {
		ArrayList<Texture> textures = new ArrayList<Texture>();
		for (File file : filesIn(folder)) {
			textures.add(loadPNGTexture(file.getAbsolutePath()));
		}
		return textures;
	}
	public static void printArrayList(ArrayList list) {
		for (Object o : list) {
			System.out.println(o);
		}
	}
	public static boolean equalVecs(Vector2f vec1, Vector2f vec2) {
		if (vec1 == null || vec2 == null) {
			return false;
		} else if (vec1.x == vec2.x && vec1.y == vec2.y) {
			return true;
		} else {
			return false;
		}
	}
	public static float lengthBetween(Vector2f vec1, Vector2f vec2) {
		return sub(vec1,vec2).length();
	}
	public static float distanceBetween() {
		return 0;
	}
	public static Vector2f proj(Vector2f p, Vector2f q) {
		return mul(q, Vector2f.dot(p, q) / q.lengthSquared());
	}
}
