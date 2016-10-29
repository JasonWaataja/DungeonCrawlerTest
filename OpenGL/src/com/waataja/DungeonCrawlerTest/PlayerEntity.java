package com.waataja.DungeonCrawlerTest;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static com.waataja.Utils.*;

public class PlayerEntity extends Entity {
	private Texture upTexture;
	private Texture downTexture;
	private Texture leftTexture;
	private Texture rightTexture;
	public PlayerEntity(Texture tex, Vector2f pos, Vector2f dim) {
		super(tex, pos, dim, vec(0,0));
		upTexture = loadPNGTexture("res/textures/Crate.png");
		downTexture = loadPNGTexture("res/textures/Crate.png");
		rightTexture = loadPNGTexture("res/textures/Crate.png");
		downTexture = loadPNGTexture("res/textures/Crate.png");
		setVelocity(vec(0,0));
		// TODO Auto-generated constructor stub
	}
	public void up() {
		setTexture(upTexture);
	}
	public void down() {
		setTexture(downTexture);
	}
	public void left() {
		setTexture(leftTexture);
	}
	public void right() {
		setTexture(rightTexture);
	}
	public void update(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		//addVelocity(game.getDelta());
	}
	@Override
	public void ifIntersects(ArrayList<Entity> intersectingEntities) {
		// TODO Auto-generated method stub
		if (!intersectingEntities.isEmpty()) {
			System.out.println("your player intersects another entity");
		}
	}
	@Override
	public void ifIntersectsBlocks(ArrayList<Block> intersectingBlocks) {
		// TODO Auto-generated method stub
		if (!intersectingBlocks.isEmpty()) {
			System.out.println("your player intersects another block");
		}
	}
}
