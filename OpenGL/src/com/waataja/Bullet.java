package com.waataja;

import org.lwjgl.util.vector.Vector3f;
import static com.waataja.Utils.*;

public abstract class Bullet {
	private Vector3f velocity;
	private Vector3f position;
	private Vector3f acceleration;
	private CrateWatchingModel model;
	private Test game;
	public Bullet(Test game) {
		this.game = game;
		acceleration = vec(0,0,0);
		model = new CrateWatchingModel("res/models/Octahedron.txt", game.getPosition());
		initialize();
	}
	public abstract void update(int delta);
	public  Vector3f getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}
	public Vector3f getAcceleration() {
		return acceleration;
	}
	public abstract void initialize();
}
