package com.waataja.DungeonCrawlerTest;

import static com.waataja.utils.math.VectorMath.add;
import static com.waataja.utils.math.VectorMath.vec;
import static com.waataja.utils.math.VectorMath.withLength;
import static com.waataja.utils.input.Input.*;

import org.lwjgl.util.vector.Vector2f;

public class ConstantVelocity extends MagicSpell {
	public Vector2f velocity = vec(0,0);
	public static BasicFireballEntity ent;
	@Override
	public void up(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		velocity = up;
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(velocity,game.fireSpeed))));
	}

	@Override
	public void down(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void left(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void right(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upRight(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void upLeft(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downLeft(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void downRight(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub

	}

}
