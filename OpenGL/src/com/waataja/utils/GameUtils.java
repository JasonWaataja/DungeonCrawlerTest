package com.waataja.utils;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import static com.waataja.utils.math.VectorMath.*;
public class GameUtils {

	public static float scaledToTime(float number, float milliseconds) {
		return number * milliseconds / 1000;
	}
	public static Vector3f scaledToTime(Vector3f vector, float milliseconds) {
		return scaled(vector, milliseconds / 1000);
	}
	public static Vector2f scaledToTime(Vector2f vector, float milliseconds) {
		return scaled(vector, milliseconds / 1000);
	}
	public static void updateDisplay(int fps) {
		Display.update();
		Display.sync(fps);
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

	public static void createDisplay(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
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
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	public static void cleanUp() {
		Display.destroy();
		System.exit(0);
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
}
