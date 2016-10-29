package com.waataja.DungeonCrawlerTest;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import static com.waataja.Utils.*;

public class Block {
	private Vector2f position;
	private Texture texture;
	private boolean solid;
	
	public Block(Texture texture, int x, int y) {
		this.texture = texture;
		position = vec(x,y);
		solid = false;
	}
	public Block(Texture texture, Vector2f vec) {
		this.texture = texture;
		position = vec;
		solid = false;
	}
	public Block(Texture texture, int x, int y, boolean solid) {
		this(texture, x, y);
		this.solid = solid;
	}
	public Block(Texture texture, Vector2f vec, boolean solid) {
		this(texture, vec);
		this.solid = solid;
	}
	public Vector2f getPosition() {
		return position;
	}
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	public boolean getSolid() {
		return solid;
	}
	public void setSolid(boolean solidity) {
		this.solid = solidity;
	}
	public void draw() {
		DungeonCrawlerTest.drawBlock(texture, position);
	}
}
