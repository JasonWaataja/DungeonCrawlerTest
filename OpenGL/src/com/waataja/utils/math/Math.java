package com.waataja.utils.math;

public class Math {
	public static float sqrt(float number) {
		return (float) Math.sqrt(number);
	}
	public static float cos(float number) {
		return (float) cos(number);
	}
	public static float sin(float number) {
		return (float) sin(number);
	}
	public static int toInt(double x) {
		return (int) x;
	}
	public static float abs(double x) {
		return (float) java.lang.Math.abs(x);
	}
	public static int sign(double x) {
		if (x > 0) {
			return 1;
		} else if (x < 0) {
			return -1;
		} else {
			return 0;
		}
	}
}
