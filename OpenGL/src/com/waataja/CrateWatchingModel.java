package com.waataja;

import java.util.ArrayList;

import static com.waataja.Utils.*;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import static org.lwjgl.opengl.GL11.*;

public class CrateWatchingModel {
	private Texture texture;
	private ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	private ArrayList<Vector2f> texCoords = new ArrayList<Vector2f>();
	private ArrayList<TriangleFace> faces = new ArrayList<TriangleFace>();
	private Vector3f location = vec(0,0,0);
	public CrateWatchingModel() {}
	public CrateWatchingModel(Vector3f pos) {
		setLocation(pos);
	}
	public CrateWatchingModel(String fileLocation, Vector3f pos) {
		this(pos);
		load(fileLocation);
	}
	public CrateWatchingModel(String fileLocation) {
		load(fileLocation);
	}
	public ArrayList<Vector3f> getVertices() {
		return vertices;
	}
	public void setVertices(ArrayList<Vector3f> verts) {
		this.vertices = verts;
	}
	public ArrayList<Vector2f> getTexCoords() {
		return texCoords;
	}
	public void setTexCoords(ArrayList<Vector2f> coords) {
		this.texCoords = coords;
	}
	public ArrayList<TriangleFace> getFaces() {
		return faces;
	}
	public void setFaces(ArrayList<TriangleFace> faces) {
		this.faces = faces;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public void rotateModelX(float rotation) {
		for (Vector3f vec : vertices) {
			rotateX(vec, rotation, vec);
		}
	}
	public void rotateAroundX(Vector3f point, float rotation) {
		rotateX(location, point, rotation, location);
		rotateModelX(rotation);
	}
	public void rotateModelY(float rotation) {
		for (Vector3f vec : vertices) {
			rotateY(vec, rotation, vec);
		}
	}
	public void rotateAroundY(Vector3f point, float rotation) {
		rotateY(location, point, rotation, location);
		rotateModelY(rotation);
	}
	public void rotateModelZ(float rotation) {
		for (Vector3f vec : vertices) {
			rotateZ(vec, rotation, vec);
		}
	}
	public void rotateAroundZ(Vector3f point, float rotation) {
		rotateZ(location, point, rotation, location);
		rotateModelZ(rotation);
	}
	public Vector3f getLocation() {
		return location;
	}
	public void setLocation(Vector3f position) {
		location = position;
	}
	public void render() {
		glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		for (Vector3f point : vertices) {
			Vector3f.add(point, location, point);
		}
		for (TriangleFace face : faces) {
			glBegin(GL_TRIANGLES);
			tex(texCoords.get((int) face.getTexCoords().x));
			plot(vertices.get((int) face.getVertices().x));
			tex(texCoords.get((int) face.getTexCoords().y));
			plot(vertices.get((int) face.getVertices().y));
			tex(texCoords.get((int) face.getTexCoords().z));
			plot(vertices.get((int) face.getVertices().z));
			glEnd();
		}
		for (Vector3f point : vertices) {
			Vector3f.sub(point, location, point);
		}
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	public void addVert(Vector3f vec) {
		getVertices().add(vec);
	}
	public void addVerts(Vector3f[] vecs) {
		for (Vector3f vec : vecs) {
			addVert(vec);
		}
	}
	public void addTexs(Vector2f[] vecs) {
		for (Vector2f vec : vecs) {
			addTex(vec);
		}
	}
	public void addFaces(TriangleFace[] faces) {
		for (TriangleFace face : faces) {
			addFace(face);
		}
	}
	public void addTex(Vector2f tex) {
		getTexCoords().add(tex);
	}
	public void addFace(TriangleFace face) {
		getFaces().add(face);
	}
	public void setVert(int index, Vector3f vec) {
		getVertices().set(index, vec);
	}
	public void setTex(int index, Vector2f vec) {
		getTexCoords().set(index, vec);
	}
	public void setFace(int index, TriangleFace face) {
		getFaces().set(index, face);
	}
	public ArrayList<Vector3f> getTranslatedVertices() {
		ArrayList<Vector3f> newList = new ArrayList<Vector3f>();
		for (int i = 0; i < vertices.size(); i++) {
			Vector3f.add(vertices.get(i), location, newList.get(i));
		}
		return newList;
	}
	public ArrayList<Vector3f> getUnTranslatedVertices(ArrayList<Vector3f> oldList) {
		ArrayList<Vector3f> newList = new ArrayList<Vector3f>();
		for (int i = 0; i < oldList.size(); i++) {
			Vector3f.sub(oldList.get(i), location, newList.get(i));
		}
		return newList;
	}
	public void load(String fileLocation) {
		CrateWatchingModel m = ModelLoader.loadModel(fileLocation);
		vertices = m.vertices;
		texCoords = m.texCoords;
		faces = m.faces;
		texture = m.texture;
	}
	public Vector3f averagePosition() {
		Vector3f sum = vec(0,0,0);
		for (Vector3f vector : vertices) {
			Vector3f.add(sum, vector, sum);
		}
		sum.scale(vertices.size());
		Vector3f.add(sum,location,sum);
		return sum;
	}
	public void translate(Vector3f vec) {
		Vector3f.add(location,vec,location);
	}
}
