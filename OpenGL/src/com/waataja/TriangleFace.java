package com.waataja;

import org.lwjgl.util.vector.Vector3f;

class TriangleFace {
	private Vector3f vertices;
	private Vector3f texCoords;
	public TriangleFace(Vector3f vertices, Vector3f texCoords) {
		this.vertices = vertices;
		this.texCoords = texCoords;
	}
	public Vector3f getVertices() {
		return vertices;
	}
	public Vector3f getTexCoords() {
		return texCoords;
	}
}
