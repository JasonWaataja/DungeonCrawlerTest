package com.waataja;
import static com.waataja.Utils.*;
import org.lwjgl.util.vector.Vector2f;

public class Line {
	private Vector2f endpoint;
	private Vector2f direction;
	public Line(Vector2f endpoint, Vector2f direction) {
		this.endpoint = endpoint;
		this.direction = direction;
	}
	public static Line createLine(Vector2f vec1, Vector2f vec2) {
		return new Line(vec1, sub(vec2, vec1));
	}
	public Vector2f getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Vector2f vec) {
		this.endpoint = vec;
	}
	public Vector2f getDirection() {
		return direction;
	}
	public void setDirection(Vector2f vec) {
		this.direction = vec;
	}
	public Vector2f pointAtTime(float time) {
		return add(endpoint, mul(direction, time));
	}
	public float distanceFrom(Vector2f point) {
		return 0;
	}
}
