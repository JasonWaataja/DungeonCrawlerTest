package com.waataja;
import static com.waataja.Utils.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Crate {
	private CrateWatchingModel model;
	private boolean hasAI = false;
	private CrateAI ai;
	public Crate(CrateWatchingModel model, CrateAI ai) {
		this.model = model;
		this.ai = ai;
		hasAI = true;
	}
	public Crate(CrateWatchingModel model) {
		this.model = model;
	}
	public Crate(Vector3f pos, float size, Texture texture) {
		model = new CrateWatchingModel();
		model.setTexture(texture);
		model.setLocation(pos);
		float len = size / 2;
		Vector3f[] verts = new Vector3f[8];
		verts[0] = vec(len,len,len);
		verts[1] = vec(len,len,-len);
		verts[2] = vec(len,-len,len);
		verts[3] = vec(len,-len,-len);
		verts[4] = vec(-len,len,len);
		verts[5] = vec(-len,len,-len);
		verts[6] = vec(-len,-len,len);
		verts[7] = vec(-len,-len,-len);
		model.addVerts(verts);
		model.addTexs(new Vector2f[]{vec(0,0),vec(0,1),vec(1,1),vec(1,0)});
		TriangleFace[] faces = new TriangleFace[12];
		int[][] vertLocs = new int[6][];
		vertLocs[0] = new int[]{0,1,3,2};
		vertLocs[1] = new int[]{4,5,7,6};
		vertLocs[2] = new int[]{0,1,5,4};
		vertLocs[3] = new int[]{2,3,7,6};
		vertLocs[4] = new int[]{0,2,6,4};
		vertLocs[5] = new int[]{1,3,7,5};
		int[] texLocs = {0,1,2,3};
		for (int i = 0; i < vertLocs.length; i++) {
			faces[2*i] = splitQuad(vertLocs[i], texLocs)[0];
			faces[2 * i + 1] = splitQuad(vertLocs[i], texLocs)[1];
		}
		model.addFaces(faces);
	}
	public Crate(Vector3f pos, float size, Texture texture, CrateAI ai) {
		this(pos, size, texture);
		this.ai = ai;
		hasAI = true;
	}
	public Crate(Vector3f pos, float size, String textureLocation) {
		this(pos, size, loadPNGTexture(textureLocation));
	}
	public Crate(Vector3f pos, float size, String textureLocation, CrateAI ai) {
		this(pos, size, loadPNGTexture(textureLocation), ai);
	}
	public CrateAI getAi() {
		return ai;
	}
	public void setCrateAi(CrateAI ai) {
		this.ai = ai;
		hasAI = true;
	}
	public CrateWatchingModel getModel() {
		return model;
	}
	public void setModel(CrateWatchingModel model) {
		this.model = model;
	}
	public boolean hasAI() {
		return hasAI;
	}
}
