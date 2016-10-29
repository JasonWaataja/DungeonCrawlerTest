package com.waataja.utils.opengl;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class GLOperations {
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
	public static void bindTexture(Texture texture) {
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
	}
	public static void unbindTexture() {
		glBindTexture(GL_TEXTURE_2D, 0);
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
}
