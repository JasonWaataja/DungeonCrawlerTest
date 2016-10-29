package com.waataja;
import org.lwjgl.util.vector.Vector3f;

public class Face {
	private Vector3f vertex = new Vector3f();
	private Vector3f normal = new Vector3f();
	public Vector3f getVertex() {
		return vertex;
	}
	public Vector3f getNormal() {
		return normal;
	}
}
