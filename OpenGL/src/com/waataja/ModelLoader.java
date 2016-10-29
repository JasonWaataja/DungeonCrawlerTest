package com.waataja;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static com.waataja.Utils.*;

import org.lwjgl.util.vector.*;

public class ModelLoader {
	public static CrateWatchingModel loadModel(String fileLocation) {
		CrateWatchingModel m = new CrateWatchingModel();
		BufferedReader reader = null;
		try {
		reader = new BufferedReader(new FileReader(new File(fileLocation)));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] words = line.split(" ");
			if (words[0].equals("texture")) {
				String textureLocation = line.substring(8);
				m.setTexture(loadPNGTexture(textureLocation));
			} else if (words[0].equals("v")) {
				Vector3f vec = new Vector3f();
				vec.x = Float.valueOf(words[1]);
				vec.y = Float.valueOf(words[2]);
				vec.z = Float.valueOf(words[3]);
				m.addVert(vec);
			} else if (words[0].equals("t")) {
				Vector2f vec = new Vector2f();
				vec.x = Float.valueOf(words[1]);
				vec.y = Float.valueOf(words[2]);
				m.addTex(vec);
			} else if (words[0].equals("f")) {
				Vector3f vertices = new Vector3f();
				Vector3f texCoords = new Vector3f();
				if (words.length == 4) {
					for (int i = 1; i <= 3; i++) {
						String[] components = words[i].split("/");
						switch(i) {
						case (1) : vertices.x = Integer.valueOf(components[0]);
						texCoords.x = Integer.valueOf(components[1]);
						case (2) : vertices.y = Float.valueOf(components[0]);
						texCoords.y = Integer.valueOf(components[1]);
						case (3) : vertices.z = Float.valueOf(components[0]);
						texCoords.z = Integer.valueOf(components[1]);
						}
					}
					m.addFace(new TriangleFace(vertices, texCoords));
				} else if (words.length == 5) {
					int[] verts = new int[4];
					int[] texs = new int[4];
					for (int i = 1; i <= 4; i++) {
						String[] components = words[i].split("/");
						verts[i-1] = Integer.valueOf(components[0]);
						texs[i-1] = Integer.valueOf(components[1]);
					}
					m.addFaces(splitQuad(verts, texs));
				}
			}
		}
		reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return m;
	}
}
