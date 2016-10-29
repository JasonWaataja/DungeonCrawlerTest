package com.waataja.DungeonCrawlerTest;
import com.waataja.utils.math.Line;
import static com.waataja.utils.math.VectorMath.*;


import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
public class MagicPattern {
	private ArrayList<Vector2f> points;
	private ArrayList<Float> angles;
	public MagicPattern() {
		points = new ArrayList<Vector2f>();
		angles = new ArrayList<Float>();
	}
	public ArrayList<Vector2f> getPoints() {
		return points;
	}
	public ArrayList<Float> getAngles() {
		return angles;
	}
	public Vector2f firstPoint() {
		return points.get(0);
	}
	public Vector2f lastPoint() {
		if (points.size() > 0) {
			return points.get(points.size()-1);
		} else {
			return null;
		}
	}
	public float angleBetween(Vector2f vec1, Vector2f vec2, Vector2f vec3) {
		
		return (float) (Math.PI - angle(to(vec2,vec1), to(vec2,vec3)));
	}
	public void addPoint(Vector2f point) {
		points.add(point);
		if (points.size() >= 3) {
			angles.add(angleBetween(points.get(points.size()-1),points.get(points.size()-2),points.get(points.size()-3)));
		}
	}
	public void process(DungeonCrawlerTest game) {
		/*boolean anglesLessThan45 = true;
		for (float num : angles) {
			if (num > (Math.PI / 4)) {
				anglesLessThan45 = false;
			}
		}
		if (anglesLessThan45) {
			Vector2f velocity = sub(lastPoint(), firstPoint());
			BasicFireballEntity ent = new BasicFireballEntity(game.getPlayer().getCenter(), velocity);
			System.out.println(velocity + "\n");
			game.getEntities().add(ent);
		}*/
		Line line = Line.createLine(firstPoint(), lastPoint());
		boolean pointsClose = true;
		for (Vector2f point : points) {
			if (distanceBetween(line, point) > 2) {
				pointsClose = false;
			}
		}
		if (pointsClose) {
			Vector2f velocity = sub(lastPoint(), firstPoint());
			BasicFireballEntity ent = new BasicFireballEntity(game.getPlayer().getCenter(), velocity);
			System.out.println(velocity + "\n");
			game.getEntities().add(ent);
		}
	}
}
