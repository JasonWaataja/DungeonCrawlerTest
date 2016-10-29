package com.waataja.utils.input;
import org.lwjgl.util.vector.Vector2f;

import static com.waataja.utils.math.VectorMath.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Input {
	public static final Vector2f up = vec(0,-1);
	public static final Vector2f down = vec(0,1);
	public static final Vector2f left = vec(-1,0);
	public static final Vector2f right = vec(1,0);
	public static final Vector2f upRight = vec(1,-1);
	public static final Vector2f upLeft = vec(-1,-1);
	public static final Vector2f downRight = vec(1,1);
	public static final Vector2f downLeft = vec(-1,1);
	public static boolean isKey(int key) {
		return Keyboard.isKeyDown(key);
	}
	public static boolean leftMouse() {
		return Mouse.isButtonDown(0);
	}
	public static boolean rightMouse() {
		return Mouse.isButtonDown(1);
	}
	public static boolean isOnScreen() {
		if (Mouse.getX() > 0 && Mouse.getX() < Display.getWidth() && Mouse.getY() > 0 && Mouse.getY() < Display.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
}
