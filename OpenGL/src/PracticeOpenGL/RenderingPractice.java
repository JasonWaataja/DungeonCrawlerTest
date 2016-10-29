package PracticeOpenGL;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.*;

import java.nio.FloatBuffer;
import java.io.*;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class RenderingPractice {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Rendering Practice");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Texture crate = null;
		try {
			crate = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/textures/textures/magic/fireR.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0,1,1,0,-1,1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		FloatBuffer verts = BufferUtils.createFloatBuffer(12);
		FloatBuffer texs = BufferUtils.createFloatBuffer(12);
		float[] vertData = new float[]{0,0,.5f,.5f,0,.5f,0,0,.5f,.5f,.5f,0};
		float[] texsData = new float[]{0,0,1,1,0,1,0,0,1,1,1,0};
		verts.put(vertData);
		texs.put(texsData);
		verts.flip();
		texs.flip();
		
		int vao = glGenVertexArrays();
		int vertsVbo = glGenBuffers();
		int texsVbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
		glBufferData(GL_ARRAY_BUFFER, verts, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
		glBufferData(GL_ARRAY_BUFFER, texs, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		

        int shaderProgram = glCreateProgram();
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        StringBuilder vertexShaderSource = new StringBuilder();
        StringBuilder fragmentShaderSource = new StringBuilder();
        
        
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/PracticeOpenGL/shader.vert"));
            String line;
            while ((line = reader.readLine()) != null) {
                vertexShaderSource.append(line).append('\n');
            }
        } catch (IOException e) {
            System.err.println("Vertex shader wasn't loaded properly.");
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        BufferedReader reader2 = null;
        try {
            reader2 = new BufferedReader(new FileReader("src/PracticeOpenGL/shader.frag"));
            String line;
            while ((line = reader2.readLine()) != null) {
                fragmentShaderSource.append(line).append('\n');
            }
        } catch (IOException e) {
            System.err.println("Fragment shader wasn't loaded properly.");
            Display.destroy();
            System.exit(1);
        } finally {
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        if (glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Vertex shader wasn't able to be compiled correctly.");
        }
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        if (glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Fragment shader wasn't able to be compiled correctly.");
        }
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        glValidateProgram(shaderProgram);
        
        int texLoc = glGetUniformLocation(shaderProgram, "texture1");
        int numLoc = glGetUniformLocation(shaderProgram, "num");
        int lightIntensityLoc = glGetUniformLocation(shaderProgram, "light.intensity");
        int lightColorLoc = glGetUniformLocation(shaderProgram, "light.color");
        int lightPositionLoc = glGetUniformLocation(shaderProgram, "light.position");
        System.out.println(texLoc);
        System.out.println(lightIntensityLoc);
        System.out.println(lightColorLoc);
        System.out.println(lightPositionLoc);
        System.out.println(numLoc);
        
        Vector2f lightPosition = new Vector2f(0,0);
		
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			
			
			glUseProgram(shaderProgram);
			
			
			if (texLoc >= 1) {
	        	glUniform1i(texLoc, 0);
	        } else {
	        	//System.out.println("The texture loation in shader was " + texLoc);
	        	//Display.destroy();
	        	//System.exit(1);
	        }
	        glUniform1f(numLoc, .5f);
	        glUniform1f(lightIntensityLoc, .5f);
	        glUniform3f(lightColorLoc, 1, 1, 1); 
	        glUniform2f(lightPositionLoc, lightPosition.x, lightPosition.y);
	        //System.out.println(lightPosition.x + " " + lightPosition.y);
	        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
	        	lightPosition.y -= .01f;
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
	        	lightPosition.y += .01f;
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
	        	lightPosition.x -= .01f;
	        }
	        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
	        	lightPosition.x += .01f;
	        }
	        

			glActiveTexture(GL_TEXTURE0);
			glBindTexture(GL_TEXTURE_2D, crate.getTextureID());
	        
	        glBindVertexArray(vao);
			
			glBindBuffer(GL_ARRAY_BUFFER, vertsVbo);
			//glVertexPointer(2, GL_FLOAT, 0, 0);
			glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, texsVbo);
			//glTexCoordPointer(2, GL_FLOAT, 0, 0);
			glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			glBindVertexArray(0);
			
			/*glEnableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glDrawArrays(GL_TRIANGLES, 0, 6);
			glDisableClientState(GL_VERTEX_ARRAY);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);*/
			glBindVertexArray(vao);
			glEnableVertexAttribArray(0);
			glEnableVertexAttribArray(1);
			glDrawArrays(GL_TRIANGLES,0,6);
			glDisableVertexAttribArray(0);
			glDisableVertexAttribArray(1);
			glBindVertexArray(0);
			
			glBindTexture(GL_TEXTURE_2D, 0);
			
			Display.update();
			Display.sync(60);
		}
		
		glDeleteBuffers(vertsVbo);
		glDeleteBuffers(texsVbo);
		Display.destroy();
	}

}
