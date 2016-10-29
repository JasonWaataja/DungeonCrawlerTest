package com.waataja;

import org.lwjgl.util.vector.Vector3f;

public interface BulletAI {
	public void update(int delta);
	public Vector3f getVelocity();
	public void setVelocity(Vector3f velocity);
	public void initialize(Test test);
}
