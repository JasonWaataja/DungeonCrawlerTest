package com.waataja.utils.math;

import static java.lang.Math.toRadians;

import java.nio.FloatBuffer;

import javax.xml.crypto.dsig.Transform;

import org.lwjgl.util.vector.Matrix2f;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static com.waataja.utils.opengl.GLUtils.*;
import static com.waataja.utils.math.Math.*;
public class VectorMath {
	
	public static Vector3f vec(float x, float y, float z) {
		return new Vector3f(x,y,z);
	}
	public static Vector2f vec(float x, float y) {
		return new Vector2f(x,y);
	}
	public static Vector3f vec3() {
		return new Vector3f();
	}
	public static Vector2f vec2() {
		return new Vector2f();
	}
	public static Vector3f rotateX(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				1,0,0,
				0,(float) cos(x),(float) sin(x),
				0,(float) -sin(x),(float) cos(x)
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateX(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateX(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static Vector3f rotateY(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				(float) cos(x),0,(float) -sin(x),
				0,1,0,
				(float) sin(x),0,(float) cos(x)
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateY(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateY(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static Vector3f rotateZ(Vector3f point, float rotation, Vector3f dest) {
		float x = (float) toRadians(rotation);
		//Vector3f newVector = new Vector3f();
		FloatBuffer buffer = asFloatBuffer(new float[]{
				(float) cos(x),(float) sin(x),0,
				(float) -sin(x),(float) cos(x),0,
				0,0,1
		});
		Matrix3f transform = new Matrix3f();
		transform.load(buffer);
		Matrix3f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f rotateZ(Vector3f vec, Vector3f point, float rotation, Vector3f dest) {
		//Vector3f vector = new Vector3f(vec);
		Vector3f.sub(vec,point,vec);
		rotateZ(vec, rotation, dest);
		Vector3f.add(vec, point, vec);
		return dest;
	}
	public static Vector2f rotate(Vector2f point, float rotation, Vector2f dest) {
		FloatBuffer buffer = asFloatBuffer(new float[]{
				cos(rotation),sin(rotation),
				-sin(rotation),cos(rotation)
		});
		Matrix2f transform = new Matrix2f();
		transform.load(buffer);
		Matrix2f.transform(transform, point, dest);
		return dest;
	}
	public static Vector3f scaled(Vector3f vector, float scale) {
		Vector3f newVector = new Vector3f(vector);
		/*newVector.x = vector.x * scale;
		newVector.y = vector.y * scale;
		newVector.z = vector.z * scale;*/
		newVector.scale(scale);
		return newVector;
	}
	public static Vector2f scaled(Vector2f vector, float scale) {
		/*Vector2f newVector = new Vector2f(vector);
		newVector.scale(scale);
		return newVector;*/
		return mul(vector, scale);
	}
	public static Vector3f constructDirection(Vector3f rotation) {
		Vector3f vector = vec(0,0,-1);
		rotateX(vector, -rotation.x, vector);
		rotateY(vector, -rotation.y, vector);
		rotateZ(vector, -rotation.z, vector);
		vector.normalise();
		return vector;
	}
	public static Vector3f translate(Vector3f vector, Vector3f translation) {
		Vector3f newVector = new Vector3f();
		Vector3f.add(vector, translation, newVector);
		return newVector;
	}
	public static Vector2f add(Vector2f vec1, Vector2f vec2) {
		return Vector2f.add(vec1, vec2, vec2());
	}
	public static Vector2f sub(Vector2f vec1, Vector2f vec2) {
		return Vector2f.sub(vec1, vec2, vec2());
	}
	public static Vector2f mul(Vector2f vector, float scale) {
		return vec(vector.x * scale, vector.y * scale);
	}
	public static Vector2f divide(Vector2f vec, float number) {
		return mul(vec, 1f / number);
	}
	public void setLength(Vector2f vec, float length) {
		vec = mul(vec, length / vec.length());
	}
	public static Vector2f withLength(Vector2f vec, float length) {
		return mul(vec, (float)length / vec.length());
	}
	public static boolean equalVecs(Vector2f vec1, Vector2f vec2) {
		if (vec1 == null || vec2 == null) {
			return false;
		} else if (vec1.x == vec2.x && vec1.y == vec2.y) {
			return true;
		} else {
			return false;
		}
	}
	public static Vector2f to(Vector2f vec1, Vector2f vec2) {
		return sub(vec2, vec1);
	}
	public static float distanceBetween(Vector2f vec1, Vector2f vec2) {
		return sub(vec1,vec2).length();
	}
	public static float distanceBetween(Line line, Vector2f point) {
		return perp(sub(point, line.getEndpoint()), line.getDirection()).length();
	}
	public static Vector2f proj(Vector2f p, Vector2f q) {
		return mul(q, Vector2f.dot(p, q) / q.lengthSquared());
	}
	public static Vector2f perp(Vector2f p, Vector2f q) {
		return sub(p, proj(p,q));
	}
	public static float length(Vector2f vec) {
		return vec.length();
	}
	public static float angle(Vector2f vec1, Vector2f vec2) {
		return Vector2f.angle(vec1, vec2);
	}
	public static Matrix2f inverse(Matrix2f mat) {
		Matrix2f inverse = new Matrix2f(mat);
		inverse.invert();
		return inverse;
	}
	public static Vector2f mul(Matrix2f matrix, Vector2f vec) {
		return Matrix2f.transform(matrix, vec, vec2());
	}
	public static Vector2f intersection(Line lin1, Line lin2) {
		Matrix2f transform = new Matrix2f();
		transform.m00 = lin1.direction.x;
		transform.m10 = lin1.direction.y;
		transform.m01 = lin2.direction.x;
		transform.m11 = lin2.direction.y;
		transform = inverse(transform);
		Vector2f times = mul(inverse(transform), sub(lin2.endpoint, lin1.endpoint));
		Vector2f intersection = lin1.pointAt(times.x);
		return intersection;
	}
	public static void main(String args[]) {
		Line lin1 = new Line(vec(0,1), vec(1,0));
		Line lin2 = new Line(vec(0,0), vec(1,0));
		FloatBuffer buffer = asFloatBuffer(new float[]{
				1,3,
				2,4
		});
		Matrix2f matrix = new Matrix2f();
		matrix.load(buffer);
		System.out.println(matrix);
		System.out.println(inverse(matrix));
		System.out.println(intersection(lin1,lin2));
	}
}
