package com.waataja.DungeonCrawlerTest;
import static com.waataja.utils.math.Math.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import static com.waataja.Utils.*;
import static org.lwjgl.opengl.GL11.*;

public class StateLoader {
	private static DungeonCrawlerTest game;
	private static String line;
	private static String resourceLocation;
	private static boolean solid;
	private static Texture texture;
	private static Texture defaultGroundTexture;
	private static Texture defaultWallTexture;
	private static String word0;
	public static void load(DungeonCrawlerTest game, String fileLocation) {
		StateLoader.game = game;
		BufferedReader reader = null;
		resourceLocation = "res";
		try {
			reader = new BufferedReader(new FileReader(new File(fileLocation)));
			while ((line = reader.readLine()) != null) {
				processLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void processLine() {
		String[] words = line.split(" ");
		word0 = words[0];
		if (word0.equals("nav")) {
			if (words[1].equals("out")) {
				String[] locationWords = resourceLocation.split("/");
				resourceLocation = locationWords[0];
				for (int i = 1; i < locationWords.length - 1; i++) {
					resourceLocation = appendFile(resourceLocation, locationWords[i]);
				}
			} else {
				resourceLocation = appendFile(resourceLocation, after(line, "nav"));
			}
		} else if (word0.equals("texture")) {
			texture = loadPNGTexture(appendFile(resourceLocation, after(line, "texture") + ".png"));
		} else if (word0.equals("solid")) {
			if (line.substring(6).equals("true")) {
				solid = true;
			} else {
				solid = false;
			}
		} else if (word0.equals("b")) {
			//game.getBlocks().add(new Block(texture, Integer.valueOf(words[1]), Integer.valueOf(words[2]), solid));
			game.addBlock(new Block(texture, Integer.valueOf(words[1]), Integer.valueOf(words[2]), solid));
		} else if (word0.equals("defgroundtex")) {
			defaultGroundTexture = loadPNGTexture(after(line, "defgroundtex") + ".png");
		} else if (word0.equals("defwalltex")) {
			defaultWallTexture = loadPNGTexture(after(line, "defwalltex") + ".png");
		} else if (word0.equals("size")) {
			game.setBlockArray(new Block[toInt(words[1])][toInt(words[2])]);
		} else if (word0.equals("speed")) {
			game.setTranslationSpeed(toFloat(words[1]));
		} else if (word0.equals("plot")) {
			game.getExtraCommands().add(new GLCommand(Command.plot, toFloat(words[1]), toFloat(words[2])));
		} else if (word0.equals("tex")){
			game.getExtraCommands().add(new GLCommand(Command.tex, toFloat(words[1]),toFloat(words[2])));
		} else if (word0.equals("texPlot")) {
			game.getExtraCommands().add(new GLCommand(Command.texPlot, toFloat(words[1]),toFloat(words[2]),toFloat(words[3]),toFloat(words[4]),toFloat(words[5])));
		} else if (word0.equals("color")) {
			game.getExtraCommands().add(new GLCommand(Command.color, toFloat(words[1]), toFloat(words[2]), toFloat(words[3])));
		} else if (word0.equals("resetColor")) {
			game.getExtraCommands().add(new GLCommand(Command.resetColor));
		} else if (word0.equals("triangles")) {
			game.getExtraCommands().add(new GLCommand(Command.triangles));
		} else if (word0.equals("quads")) {
			game.getExtraCommands().add(new GLCommand(Command.quads));
		} else if (word0.equals("end")) {
			game.getExtraCommands().add(new GLCommand(Command.end));
		} else if (word0.equals("loadTexture")) {
			Texture textureName = loadPNGTexture(appendFile(resourceLocation,after(line,"loadTexture")+".png"));
			game.addTexture(textureName, after(line,"loadTexture"));
		} else if (word0.equals("line")) {
			for (Vector2f vec : line(vec(toFloat(words[1]),toFloat(words[2])),vec(toFloat(words[3]),toFloat(words[4])))) {
				game.addBlock(block(vec));
			}
		} else {
			
		}
	}
	public static ArrayList<Vector2f> line(Vector2f vec1, Vector2f vec2) {
		ArrayList<Vector2f> vecs = new ArrayList<Vector2f>();
		vec1.x = toInt(vec1.x);
		vec1.y = toInt(vec1.y);
		vec2.x = toInt(vec2.x);
		vec2.y = toInt(vec2.y);
		int width = toInt(vec2.x - vec1.x);
		int height = toInt(vec2.y - vec1.y);
		if (abs(width) > abs(height)) {
			float slope = ((float)height) / width;
			for (int i = toInt(vec1.x); abs(i - vec2.x) != 0; i += sign(width)){
				vecs.add(new Vector2f(i, vec1.y + slope * i));
			}
		} else {
			float slope = ((float)width) / height;
			for (int i = toInt(vec1.y); abs(i - vec2.y) != 0; i += sign(height)) {
				vecs.add(new Vector2f(vec1.x + slope * i, i));
			}
		}
		return vecs;
	}
	public static void main(String[] args) {
		Vector2f vec1 = vec(5,0);
		Vector2f vec2 = vec(30,35);
		System.out.println(line(vec1, vec2));
	}
	public static Block block(Vector2f position) {
		return new Block(texture, position, solid);
	}
	public static Block block(int x, int y) {
		return new Block(texture, x, y, solid);
	}
}
