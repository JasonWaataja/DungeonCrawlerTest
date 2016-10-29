package com.waataja.DungeonCrawlerTest;
import static com.waataja.Utils.*;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

public class BasicFireballEntity extends Entity {
	public BasicFireballEntity(Vector2f pos, Vector2f velocity) {
		super(loadPNGTexture("res/textures/textures/magic/fireR.png"), pos, vec(1,1), velocity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
	}

	@Override
	public void ifIntersects(ArrayList<Entity> intersectingEntities) {
		// TODO Auto-generated method stub
		if (!intersectingEntities.isEmpty()) {
			System.out.println("a fireball entity intersects another entity");
		}
	}

	@Override
	public void ifIntersectsBlocks(ArrayList<Block> intersectingBlocks) {
		// TODO Auto-generated method stub
		if (!intersectingBlocks.isEmpty()) {
			System.out.println("your player intersects a block");
		}
	}
}
