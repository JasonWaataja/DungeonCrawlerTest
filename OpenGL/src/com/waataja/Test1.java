package com.waataja;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.create();
		} catch (Exception ex) {
			
		}
		glMatrixMode(GL_PROJECTION);
	}

}
