package com.waataja.DungeonCrawlerTest;
import static com.waataja.utils.math.VectorMath.*;
import static com.waataja.utils.input.Input.*;
public class BasicFire extends MagicSpell {

	@Override
	public void up(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(up,game.fireSpeed))));
	}

	@Override
	public void down(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(down,game.fireSpeed))));
	}

	@Override
	public void left(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(left,game.fireSpeed))));
	}

	@Override
	public void right(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(right,game.fireSpeed))));
	}

	@Override
	public void upRight(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(vec(1,-1),game.fireSpeed))));
	}

	@Override
	public void upLeft(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(vec(-1,-1),game.fireSpeed))));
	}

	@Override
	public void downLeft(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(vec(-1,1),game.fireSpeed))));
	}

	@Override
	public void downRight(DungeonCrawlerTest game) {
		// TODO Auto-generated method stub
		game.getEntities().add(new BasicFireballEntity(game.getPlayer().getCenter(),add(game.getPlayer().getVelocity(),withLength(vec(1,1),game.fireSpeed))));
	}

}
