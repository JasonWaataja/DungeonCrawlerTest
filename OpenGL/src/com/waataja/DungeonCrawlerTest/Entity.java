package com.waataja.DungeonCrawlerTest;

import static com.waataja.utils.math.Math.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static com.waataja.Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public abstract class Entity {
	private Vector2f position;
	private Vector2f dimmension;
	private Texture texture;
	private Vector2f velocity;
	public boolean moving;
	public int vbo;

	public Entity(Texture tex, Vector2f pos, Vector2f dim, Vector2f vel) {
		texture = tex;
		position = pos;
		dimmension = dim;
		velocity = vel;
		//FloatBuffer verts= BufferUtils.createFloatBuffer(4);
		//verts.put(position.x).put(position.y).put(0).put(1);
		//verts.put(position.x+dimmension.x).put(position.y).put(0).put(1);
		//verts.put(position.x+dimmension.x).put(position.y+dimmension.y).put(0).put(1);
		//verts.put(position.x).put(position.y+dimmension.y).put(0).put(1);
		//verts.flip();
		//vbo = glGenBuffers();
		//glBindBuffer(GL_ARRAY_BUFFER, vbo);
		//GL15.glBufferData(GL_ARRAY_BUFFER, buff, GL_STREAM_DRAW);
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public Vector2f getDimmension() {
		return dimmension;
	}

	public void setDimmension(Vector2f dimmension) {
		this.dimmension = dimmension;
	}

	public float getX() {
		return position.x;
	}

	public void setX(float x) {
		position.x = x;
	}

	public float getY() {
		return position.y;
	}

	public void setY(float y) {
		position.y = y;
	}

	public float getWidth() {
		return dimmension.x;
	}

	public void setWidth(float width) {
		dimmension.x = width;
	}

	public float getHeight() {
		return dimmension.y;
	}

	public void setHeight(float height) {
		dimmension.y = height;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) position.x, (int) position.y,
				(int) dimmension.x, (int) dimmension.y);
	}

	public Rectangle getPixelRectangle() {
		return new Rectangle(toPixelLength(position.x),
				toPixelLength(position.y), toPixelLength(dimmension.x),
				toPixelLength(dimmension.y));
	}

	public Vector2f getCenter() {
		return add(position, mul(dimmension, .5f));
	}

	public void setCenter(Vector2f position) {
		this.position = sub(position, mul(dimmension, .5f));
	}

	public boolean intersects(Entity ent) {
		return getPixelRectangle().intersects(ent.getPixelRectangle());
	}

	public boolean intersects(Block block) {
		Rectangle rec = new Rectangle(
				toInt(toPixelLength(block.getPosition().x)),
				toInt(toPixelLength(block.getPosition().y)), 16, 16);
		return getPixelRectangle().intersects(rec);
	}

	public void draw() {
		try {
		FloatBuffer verts = BufferUtils.createFloatBuffer(8);
		verts.put(position.x).put(position.y);
		verts.put(position.x+dimmension.x).put(position.y);
		verts.put(position.x+dimmension.x).put(position.y+dimmension.y);
		verts.put(position.x).put(position.y+dimmension.y);
		verts.flip();
		FloatBuffer texs = BufferUtils.createFloatBuffer(8);
		texs.put(0).put(0);
		texs.put(1).put(0);
		texs.put(1).put(1);
		texs.put(0).put(1);
		texs.flip();

		/*bindTexture(texture);
		quads();
		texPlot(0, 0, position.x, position.y);
		texPlot(0, 1, position.x, position.y + dimmension.y);
		texPlot(1, 1, position.x + dimmension.x, position.y + dimmension.y);
		texPlot(1, 0, position.x + dimmension.x, position.y);
		end();
		unbindTexture();*/
		texture.bind();
		int vertsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		int texsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
		glBufferData(GL_ARRAY_BUFFER, texs, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		
		//texture.bind();
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		GL11.glDrawArrays(GL_QUADS, 0, 4);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		
		glBindTexture(GL_TEXTURE_2D, 0);
		} catch (Exception e) {}
		
	}

	public void move(int delta) {
		setPosition(add(getPosition(), scaledToTime(velocity, delta)));
	}

	public abstract void update(DungeonCrawlerTest game);

	public Vector2f getNewPosition(int delta) {
		return add(getPosition(), scaledToTime(velocity, delta));
	}

	public Vector2f getVelocity() {
		// TODO Auto-generated method stub
		return velocity;
	}

	public void setVelocity(Vector2f velocity) {
		// TODO Auto-generated method stub
		this.velocity = velocity;
	}

	public abstract void ifIntersects(ArrayList<Entity> intersectingEntities);

	public abstract void ifIntersectsBlocks(ArrayList<Block> intersectingBlocks);

	public static void main(String[] args) {
		PlayerEntity ent = new PlayerEntity(null, vec(0, 0), vec(4, 4));
		Block block = new Block(null, vec(3, 3));
		System.out.println(ent);
		System.out.println(block);
		boolean intersects = ent.intersects(block);
		System.out.println(intersects);
	}
	public boolean shouldMove(ArrayList<Vector2f> newPositions, ArrayList<Block> blocks, Vector2f newPosition) {
		
		return false;
	}
}
