package com.waataja;
import static com.waataja.Utils.*;

import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.*;

public class CrateWatcher {
	private Vector3f position;
	private Vector3f velocity;
	private Vector3f acceleration;
	private Vector3f rotation;
	private float jumpSpeed;
	private float walkingSpeed;
	private int delta;
	private long lastFrame;
	private Camera camera;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	public int getTime() {
		return (int) ((Sys.getTime() * 1000) / Sys.getTimerResolution());
	}
	public int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	public void updateTime() {
		delta = getDelta();
	}
	public Vector3f getPosition() {
		return position;
	}
	public void setPosition(Vector3f location) {
		position = location;
	}
	public Vector3f getVelocity() {
		return velocity;
	}
	public void setVelocity(Vector3f velocity) {
		this.velocity = velocity;
	}
	public Vector3f getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(Vector3f acceleration) {
		this.acceleration = acceleration;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	public float getWalkingSpeed() {
		return walkingSpeed;
	}
	public void setWalkingSpeed(float speed) {
		walkingSpeed = speed;
	}
	public float getJumpSpeed() {
		return jumpSpeed;
	}
	public void setJumpSpeed(float speed) {
		jumpSpeed = speed;
	}
}
