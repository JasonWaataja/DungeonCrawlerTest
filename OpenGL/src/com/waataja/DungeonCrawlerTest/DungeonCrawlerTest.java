package com.waataja.DungeonCrawlerTest;
import java.io.File;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static com.waataja.Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
public class DungeonCrawlerTest {
	private ArrayList<Block> blocks;
	private Vector2f translation;
	private float translationSpeed;
	private int delta;
	private long lastFrame;
	private InputTaker input;
	private Block[][] blockArray;
	private PlayerEntity player;
	private float movementSpeed;
	private ArrayList<GLCommand> extraCommands;
	private ArrayList<Texture> textures;
	private ArrayList<String> textureNames;
	private ArrayList<Entity> entities;
	private MagicSpell currentSpell;
	public float playerVelocity = 40;
	public float fireSpeed = 50;
	public Vector2f constantVelocity = vec(0,0);
	public DungeonCrawlerTest() {
		setUpDisplay();
		setUpOpenGL();
		setUpState();
		loop();
		cleanUp();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DungeonCrawlerTest();
	}
	public void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(640, 480));
			Display.setTitle("Dungeon Crawler Test");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setUpOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth() / 16f, Display.getHeight() / 16f, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(new float[]{0f,0f,0f,1}));
		glLight(GL_LIGHT0, GL_DIFFUSE, asFloatBuffer(new float[]{3f,3f,3f,1f}));
		glEnable(GL_BLEND);
		//glEnable(GL_ALPHA);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		//glEnable(GL_SMOOTH);
		//glEnable(GL_ALPHA);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE);	
	}
	
	public void setUpState() {
		blocks = new ArrayList<Block>();
		extraCommands = new ArrayList<GLCommand>();
		entities = new ArrayList<Entity>();
		player = new PlayerEntity(loadPNGTexture("res/textures/textures/magic/fireB.png"), vec(10,10), vec(4,4));
		entities.add(player);
		translation = vec(0,0);
		translationSpeed = 1;
		input = new InputTaker(this);
		StateLoader.load(this, "res/TestTerrainFile.txt");
		movementSpeed = 30;
		currentSpell = new BasicFire();
	}
	
	public void loop() {
		Texture crateTexture = loadPNGTexture("res/textures/Crate.png");
		Texture faceTexture = loadPNGTexture("res/textures/TestImage.png");
		Block testBlock = new Block(crateTexture, 17, 5);
		ArrayList<Texture> allTextures = texturesIn(new File("res/textures"));
		for (int i = 0; i < allTextures.size(); i++) {
			blocks.add(new Block(allTextures.get(i), vec(i + 10, 0)));
		}
		blocks.add(testBlock);
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT);
			
			
			/*bindTexture(crateTexture);
			glBegin(GL_QUADS);
				tex(0,0);
				plot(0,0);
				tex(0,1);
				plot(0,1);
				tex(1,1);
				plot(1,1);
				tex(1,0);
				plot(1,0);
			glEnd();
			unbindTexture();*/
			
			drawBlock(crateTexture, 5, 3);
			drawBlock(faceTexture, 9, 15);
			
			/*ArrayList<ArrayList<Entity>> ents = new ArrayList<ArrayList<Entity>>();
			ArrayList<ArrayList<Block>> blo = new ArrayList<ArrayList<Block>>();
			for (int i = 0; i <= entities.size() - 1; i++) {
				ents.add(new ArrayList<Entity>());
				blo.add(new ArrayList<Block>());
				for (Block block: blocks) {
					if (entities.get(i).intersects(block)) {
						blo.get(i).add(block);
					}
				}
				for (int j = i+1; j < entities.size(); j++) {
					if (entities.get(i).intersects(entities.get(j))) {
						ents.get(i).add(entities.get(j));
					}
				}
				entities.get(i).ifIntersects(ents.get(i));
				entities.get(i).ifIntersectsBlocks(blo.get(i));
			}*/
			ArrayList<Boolean> shouldMoves = shouldMoves();
			ArrayList<Vector2f> newPositions = new ArrayList<Vector2f>();
			/*for (Entity ent : entities) {
				newPositions.add(ent.getPosition());
			}
			for (int i = 0; i < entities.size(); i++) {
				boolean shouldMove = entities.get(i).shouldMove(newPositions, blocks, newPositions.get(i));
			}*/
			for (Entity ent : entities) {
				ent.move(delta);
				ent.update(this);
				ent.draw();
			}
			for (Block block : blocks) {
				block.draw();
			}
			for (GLCommand command : extraCommands) {
				command.performCommand();
			}
			glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(new float[]{player.getX(),player.getY(),2, 1}));
			
			glLoadIdentity();
			glTranslatef(-translation.x, -translation.y, 0);
			
			input.takeInput();
			Display.update();
			updateTime();
			Display.sync(60);
		}
	}
	public void cleanUp() {
		Display.destroy();
	}
	public static void drawBlock(Texture texture, int x, int y) {
		/*glBegin(GL_TRIANGLES);
			texPlot(0,0,x,y);
			texPlot(0,1,x, y+1);
			texPlot(1,1, x+1, y+1);
			
			texPlot(0,0,x,y);
			texPlot(1,0,x+1,y);
			texPlot(1,1,x+1,y+1);
		glEnd();*/
		try {
		FloatBuffer verts = BufferUtils.createFloatBuffer(12);
		FloatBuffer texs = BufferUtils.createFloatBuffer(12);
		verts.put(new float[]{x,y,x,y+1,x+1,y+1,x,y,x+1,y,x+1,y+1});
		texs.put(new float[]{0,0,0,1,1,1,0,0,1,0,1,1});
		verts.flip();
		texs.flip();
		//glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
		
		/*int vertsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
		int texsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
		glBufferData(GL_ARRAY_BUFFER, texs, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		GL11.glDrawArrays(GL_TRIANGLES, 0, 6);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glDeleteBuffers(vertsVbo);
		glDeleteBuffers(texsVbo);*/
		
		//glBindTexture(GL_TEXTURE_2D, texture.getTextureID());
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
		GL11.glDrawArrays(GL_TRIANGLES, 0, 6);
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		
		glBindTexture(GL_TEXTURE_2D, 0);
		} catch (Exception e) {}
		
	}
	public static void drawBlock(Texture texture, Vector2f vec) {
		drawBlock(texture, (int) vec.x, (int) vec.y);
	}
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	public void setUpTranslationMatrix() {
		glLoadIdentity();
		glPushMatrix();
		glTranslatef(-translation.x, -translation.y, 0);
	}
	public void resetTranslationMatrix() {
		glPopMatrix();
	}
	public void updateTime() {
		long currentTime = getTime();
		delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
	}
	public Vector2f getTranslation() {
		return translation;
	}
	public void setTranslation(Vector2f trans) {
		this.translation = trans;
	}
	public float getTranslationSpeed() {
		return translationSpeed;
	}
	public void setTranslationSpeed(float speed) {
		this.translationSpeed = speed;
	}
	public int getDelta() {
		return delta;
	}
	public float getMovementSpeed() {
		return movementSpeed;
	}
	public void setMoveMentSpeed(float speed) {
		movementSpeed = speed;
	}
	public void addBlock(Block block) {
		blocks.add(block);
		blockArray[(int) block.getPosition().x][(int) block.getPosition().y] = block;
	}
	public Block[][] getBlockArray() {
		return blockArray;
	}
	public void setBlockArray(Block[][] blocks) {
		blockArray = blocks;
	}
	public ArrayList<GLCommand> getExtraCommands() {
		return extraCommands;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void addTexture(Texture texture, String name) {
		textures.add(texture);
		textureNames.add(name);
	}
	public Vector2f onScreenCoord(Vector2f point) {
		return sub(point,translation);
	}
	public Vector2f onScreenCoord(Entity ent) {
		return onScreenCoord(ent.getPosition());
	}
	public Vector2f worldCoord(Vector2f point) {
		return add(point,translation);
	}
	public Vector2f getCenterPositionOnScreen(Entity ent) {
		return onScreenCoord(ent.getCenter());
	}
	public float getMouseX() {
		return toBlockLength(Mouse.getX());
	}
	public float getMouseY() {
		return toBlockLength(Display.getHeight() - Mouse.getY() - 1);
	}
	public Vector2f getMousePosition() {
		return vec(getMouseX(), getMouseY());
	}
	public Vector2f getWorldMousePosition() {
		return worldCoord(getMousePosition());
	}
	public boolean mouseOver(Entity ent) {
		return over(vec(getMouseX(),getMouseY()), ent);
	}
	public boolean over(Vector2f point, Entity ent) {
		Vector2f pos = onScreenCoord(ent);
		if (point.x > pos.x && point.x < pos.x+ent.getWidth() && point.y > pos.y && point.y < pos.y + ent.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
	public PlayerEntity getPlayer() {
		return player;
	}
	public float getWidth() {
		return toBlockLength(Display.getWidth());
	}
	public float getHeight() {
		return toBlockLength(Display.getHeight());
	}
	public Vector2f getDimmensions() {
		return vec(getWidth(), getHeight());
	}
	public MagicSpell getSpell() {
		return currentSpell;
	}
	public ArrayList<Boolean> shouldMoves() {
		return new ArrayList<Boolean>();
	}
}
