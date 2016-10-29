import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import  org.lwjgl.util.vector.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static java.lang.Math.*;
import static com.waataja.Utils.*;

import java.io.*;
import java.nio.*;

import org.newdawn.slick.opengl.*;
public class Test {
	
	private Vector3f position = point(0,2,0);
	private Vector3f velocity = point(0,0,0);
	private Vector3f acceleration = point(0,0,0);
	private Vector3f rotation = point(0,0,0);
	private int delta;
	private int lastFrame;
	private float walkingSpeed = 10;
	private boolean inAir = false;
	private float jumpSpeed = 7;
	private float sprintingSpeed = 50;
	private boolean flying = false;
	private Vector3f[] points = {point(1,1,-4),point(1,-1,-4),point(-1,-1,-4),point(-1,1,-4)};
	
	public Test() {
		super();
		setUpDisplay();
		setUpOpenGL();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Vector3f vector = point(1,1,1);
		for (int i = 0; i < 4; i++) {
		vector = rotateX(vector, (float) PI / 2);
		System.out.println(vector);
		}
		
		
		Test test = new Test();
		test.loop();
		test.cleanUp();
	}

	public void setUpDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Testing");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Mouse.setGrabbed(true);
	}
	
	public void setUpOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(60f, 800f / 600f, .5f, 100f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		//glEnable(GL_DEPTH_TEST);
		//glEnable(GL_SMOOTH);
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void cleanUp() {
		Display.destroy();
	}
	
	public void loop() {
		Texture crate = null;
		try {
			crate = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/Crate.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (!Display.isCloseRequested() && !isKey(Keyboard.KEY_ESCAPE)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glBegin(GL_QUADS);
			//color(0,1,0);
			color(1,0,0);
			plot(position.x+20,0,position.z+20);
			plot(position.x+20,0,position.z-20);
			plot(position.x-20,0,position.z-20);
			plot(position.x-20,0,position.z+20);

			plot(position.x+20,20,position.z+20);
			plot(position.x+20,20,position.z-20);
			plot(position.x-20,20,position.z-20);
			plot(position.x-20,20,position.z+20);
			
			plot(position.x+20,0,position.z+20);
			plot(position.x+20,20,position.z+20);
			plot(position.x+20,20,position.z-20);
			plot(position.x+20,0,position.z-20);
			
			plot(position.x+20,0,position.z-20);
			plot(position.x+20,20,position.z-20);
			plot(position.x-20,20,position.z-20);
			plot(position.x-20,0,position.z-20);
			
			plot(position.x-20,0,position.z-20);
			plot(position.x-20,20,position.z-20);
			plot(position.x-20,20,position.z+20);
			plot(position.x-20,0,position.z+20);
			
			plot(position.x-20,0,position.z+20);
			plot(position.x-20,20,position.z+20);
			plot(position.x+20,20,position.z+20);
			plot(position.x+20,0,position.z+20);
			
			resetColor();
			
			for (int i = -1000; i <= 1000; i++) {
				plot(i,.25f,.25f);
				plot(i,.25f,-.25f);
				plot(i,-.25f,-.25f);
				plot(i,-.25f,.25f);
				
				plot(.25f,.25f,i);
				plot(.25f,-.25f,i);
				plot(-.25f,-.25f,i);
				plot(-.25f,.25f,i);
				
				plot(.25f,i,.25f);
				plot(.25f,i,-.25f);
				plot(-.25f,i,-.25f);
				plot(-.25f,i,.25f);
			}
			
			glEnd();
			glBindTexture(GL_TEXTURE_2D, crate.getTextureID());
			glBegin(GL_TRIANGLES);
			for (Vector3f point : points) {
				point = rotateX(point, 1f);
			}
			//texPoint(0,0,points[0]);
			texPoint(0,1,points[1]);
			texPoint(1,1,points[2]);
			texPoint(1,0,points[3]);
			glEnd();
			glBindTexture(GL_TEXTURE_2D, 0);
			texPoint(0,0,-1,-1,-2);
			updateInput();
			updateTime();
			//velocity = point(.01f,.01f,.01f);
			//rotation.x += .01f;
			Vector3f.add(getScaledVector(acceleration), velocity, velocity);
			Vector3f.add(getScaledVector(velocity), position, position);
			//System.out.println("" + velocity.x + velocity.y + velocity.z);
			//System.out.println(scaledVelocity(velocity));
			if (position.y < 2) {
				position.y = 2;
				velocity.y = 0;
				acceleration.y = 0;
				inAir = false;
			}
			glLoadIdentity();
			glRotatef(rotation.x, 1, 0, 0);
			glRotatef(rotation.y, 0, 1, 0);
			glRotatef(rotation.z, 0, 0, 1);
			glTranslatef(-position.x, -position.y, -position.z);
			Display.update();
			Display.sync(60);
		}
	}
	public static boolean isKey(int key) {
		return Keyboard.isKeyDown(key);
	}
	public static Vector3f point(float x, float y, float z) {
		return new Vector3f(x,y,z);
	}
	public static void plot(float x, float y, float z) {
		glVertex3f(x,y,z);
	}
	public static void plot(Vector3f point) {
		plot(point.x, point.y, point.z);
	}
	public static void color(float x, float y, float z) {
		glColor3f(x, y, z);
	}
	public static void color(Vector3f color) {
		color(color.x, color.y, color.z);
	}
	public static void tex(float x, float y) {
		glTexCoord2f(x, y);
	}
	public static void texPoint(float x1, float y1, float x2, float y2, float z2) {
		tex(x1, y1);
		plot(x2, y2, z2);
	}
	public static void texPoint(float x, float y, Vector3f point) {
		tex(x, y);
		plot(point);
	}
	public static void resetColor() {
		color(1,1,1);
	}
	public int getTime() {
		return (int) ((Sys.getTime() * 1000) / Sys.getTimerResolution());
	}
	public int getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		return delta;
	}
	public void updateTime() {
		delta = getDelta();
	}
	public Vector3f getScaledVector(Vector3f vector) {
		float scale = (float) delta / 1000;
		Vector3f newVector = new Vector3f(vector);
		newVector.scale(scale);
		return newVector;
	}
	
	public static Vector3f rotateX(Vector3f vector, float angle) {
		Vector3f newVector = new Vector3f(vector);
		FloatBuffer rotationMatrixData = createBuffer(new float[]{1,0,0,0,(float) cos(angle),(float) sin(angle),0,(float) -sin(angle),(float) cos(angle)});
		Matrix3f transformation = new Matrix3f();
		transformation.load(rotationMatrixData);
		Matrix3f.transform(transformation, newVector, newVector);
		return newVector;
	}
	public static FloatBuffer createBuffer(float[] list) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.length);
		buffer.put(list);
		buffer.flip();
		return buffer;
	}
	
	public void updateInput() {
		rotation.y += .5f * Mouse.getDX();
		rotation.x -= .5f * Mouse.getDY();
		rotation.y = rotation.y % 360;
		if (rotation.x < -90) {
			rotation.x = -90;
		}
		if (rotation.x > 90) {
			rotation.x = 90;
		}
		double angle = toRadians(rotation.y);
		float speed = velocity.length();
		velocity.z = 0;
		velocity.x = 0;
		if (isKey(Keyboard.KEY_W)) {
			velocity.z -= cos(angle);
			velocity.x += sin(angle);
		}
		if (isKey(Keyboard.KEY_S)) {
			velocity.z += cos(angle);
			velocity.x -= sin(angle);
		}
		if (isKey(Keyboard.KEY_A)) {
			velocity.z -= sin(angle);
			velocity.x -= cos(angle);
		}
		if (isKey(Keyboard.KEY_D)) {
			velocity.z += sin(angle);
			velocity.x += cos(angle);
		}
		while(Mouse.next()) {
			if (Mouse.getEventButton() == 0) {
				if (Mouse.getEventButtonState()) {
					if (flying) {
						flying = false;
					} else {
						flying = true;
					}
					//System.out.println((flying));
				}
			}
		}
		if (Mouse.isButtonDown(1)) {
			walkingSpeed = sprintingSpeed;
		} else {
			walkingSpeed = 10;
		}
		if (isKey(Keyboard.KEY_LSHIFT)) {
			if (!inAir) {
				walkingSpeed = 5;
			} else {
				if (flying) {
					velocity.y = -jumpSpeed;
				}
			}
		} //else {
		//	position.y = 2;
		//}
		velocity.z *= walkingSpeed;
		velocity.x *= walkingSpeed;
		if (isKey(Keyboard.KEY_SPACE)) {
			if (!flying) {
				if (!inAir) {
					velocity.y = jumpSpeed;
					acceleration.y += -10;
					inAir = true;
				}
			} else {
				velocity.y = jumpSpeed;
				inAir = true;
			}
		}
		if (flying && !isKey(Keyboard.KEY_SPACE)) {
			velocity.y = 0;
		}
	}
}
