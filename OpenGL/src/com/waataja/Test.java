package com.waataja;
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
import java.util.ArrayList;

import org.newdawn.slick.opengl.*;
public class Test {
	
	private Vector3f position = vec(0,2,0);
	private Vector3f velocity = vec(0,0,0);
	private Vector3f acceleration = vec(0,0,0);
	private Vector3f rotation = vec(0,0,0);
	private int delta;
	private int lastFrame;
	private float walkingSpeed = 10;
	private boolean inAir = false;
	private float jumpSpeed = 7;
	private float sprintingSpeed = 50;
	private boolean flying = false;
	private Vector3f projectileVelocity = vec(0,0,0);
	private boolean isCrouching = false;
	private float flyingSpeed = 7;
	private ArrayList<CrateWatchingModel> bullets = new ArrayList<CrateWatchingModel>();
	private ArrayList<CrateWatchingModel> models = new ArrayList<CrateWatchingModel>();
	private Vector3f[] points = {vec(1,1,-4),vec(1,-1,-4),vec(-1,-1,-4),vec(-1,1,-4)};
	
	public Test() {
		super();
		setUpDisplay();
		setUpOpenGL();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Vector3f vector = vec(1,1,1);
		/*vector = rotateX(vector, 90);
		System.out.println(vector);
		vector = rotateX(vector, 90);
		System.out.println(vector);
		vector = rotateX(vector, 90);
		System.out.println(vector);
		vector = rotateX(vector, 90);
		System.out.println(vector);*/
		/*for (int i = 0; i < 360; i++) {
			vector = rotateX(vector, 1);
			System.out.println(vector);
		}*/
		/*for (int i = 0; i < 4; i++) {
		vector = rotateX(vector, (float) PI / 2);
		System.out.println(vector);
		}*/
		
		
		Test test = new Test();
		test.loop();
		test.cleanUp();
	}

	public void setUpDisplay() {
		/*try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.setTitle("Testing");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		createDisplay("I'm playing games. (not really)", 800, 600);
		Mouse.setGrabbed(true);
	}
	
	public void setUpOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(30f, 800f / 600f, .5f, 10000f);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		//Display.setResizable(true);
		glEnable(GL_DEPTH_TEST);
		//glEnable(GL_SMOOTH);
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	public void cleanUp() {
		Display.destroy();
	}
	
	public void loop() {
		Texture crate = null;
		/*try {
			crate = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/textures/Crate.png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		crate = loadPNGTexture("res/textures/Crate.png");
		//int vbo = createVBO(asFloats(new Vector3f[]{points[1],points[2],points[3],points[1],points[3],points[0]}));
		/*FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
		buffer.put(new float[]{-1,0,-1,1,0,-1,0,1,-1});
		buffer.flip();*/
		FloatBuffer buffer = asFloatBuffer(new float[]{-1,0,-1,1,0,-1,0,1,-1});
		/*int vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);*/
		CrateWatchingModel m = new CrateWatchingModel(vec(0,0,0));
		/*m.setTexture(crate);
		m.getVertices().add(vec(-1,0,-2));
		m.getVertices().add(vec(1,0,-2));
		m.getVertices().add(vec(0,2,-2));
		m.getTexCoords().add(vec(0,0));
		m.getTexCoords().add(vec(0,1));
		m.getTexCoords().add(vec(1,1));
		m.getFaces().add(new TriangleFace(vec(0,1,2), vec(0,1,2)));*/
		Vector2f[] texCoords = {vec(0,0), vec(0,1), vec(1,1), vec(1,0)};
		CrateWatchingModel m2 = new CrateWatchingModel(vec(0,0,-3));
		Texture crateTexture = loadPNGTexture("res/textures/Crate.png");
		Texture faceTexture = loadPNGTexture("res/textures/TestImage.png");
		Texture alecsTexture = loadPNGTexture("res/textures/cratetexture.png");
		
		m.load("res/Triangle.txt");
		m.addVerts(new Vector3f[]{vec(1,0,-10),vec(-1,0,-10),vec(0,2,-10)});
		m.addVerts(new Vector3f[]{vec(1,0,-11),vec(-1,0,-11),vec(0,2,-11)});
		TriangleFace[] faces = {new TriangleFace(vec(3,4,5),vec(0,1,2)), new TriangleFace(vec(6,7,8),vec(0,1,2))};
		m.addFaces(faces);
		
		m2.load("res/Crate.txt");
		CrateWatchingModel m3 = new CrateWatchingModel(vec(5,5,-5));
		m3.load("res/models/Octahedron.txt");
		Vector3f vector = new Vector3f(1,1,1);
		Crate crateModel = new Crate(vec(5,5,5), 2, faceTexture, new CrateAI(){});
		Crate c2 = new Crate(vec(5,-5,2),7,faceTexture,new CrateAI(){});
		Crate c3 = new Crate(vec(-7,-2,3),1,faceTexture,new CrateAI(){});
		Crate c4 = new Crate(vec(-40,32,94), 27, crateTexture);
		Crate c5 = new Crate(vec(50,100,20), 40, alecsTexture);
		Crate c6 = new Crate(vec(10,10,10), 2, crateTexture);
		models.add(m);
		models.add(m2);
		models.add(m3);
		models.add(crateModel.getModel());
		models.add(c2.getModel());
		models.add(c3.getModel());
		models.add(c6.getModel());
		models.add(c5.getModel());
		models.add(c4.getModel());
		CrateWatchingModel buildingModel = new CrateWatchingModel(vec(30,0,30));
		buildingModel.load("res/models/Building.txt");
		models.add(buildingModel);
		while (!Display.isCloseRequested()) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			/*glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glVertexPointer(3, GL_FLOAT, 0, 0L);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glEnableClientState(GL_VERTEX_ARRAY);
			glDrawArrays(GL_TRIANGLES, 0, 3);
			glDisableClientState(GL_VERTEX_ARRAY);*/
			/*if (Display.wasResized()) {
				glMatrixMode(GL_PROJECTION);
				glLoadIdentity();
				GLU.gluPerspective(30f, (float)((double)Display.getWidth() / Display.getHeight()), .5f, 100f);
				glMatrixMode(GL_MODELVIEW);
			}*/
			
			//color(0,1,0);
			color(1,0,0);
			glBegin(GL_QUADS);
			/*plot(position.x+20,0,position.z+20);
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
			plot(position.x+20,0,position.z+20);*/
			
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
			/*for (Vector3f point : points) {
				point = rotateX(point, 1f);
			}*/
			//texPoint(0,0,points[0]);
			/*texPoint(0,1,points[1]);
			texPoint(1,1,points[2]);
			texPoint(1,0,points[3]);
			texPoint(0,1,points[1]);
			texPoint(1,0,points[3]);
			texPoint(0,0,points[0]);
			glEnd();*/
			/*for (Vector3f vec : m2.getVertices()) {
				System.out.println(vec);
			}*/
			//System.out.println();
			m.rotateModelX(1);
			m2.rotateModelX(1);
			/*for (Vector3f vec : m2.getVertices()) {
				//Vector3f.add(vec, vec(.1f,.1f,.1f), vec);
				//System.out.println(vec);
				//vec = rotateX(vec, 1);
				rotateX(vec, vec(0,0,-6), 1, vec);
				//System.out.println(vec + "\n");
			}*/
			
			/*for (Vector3f vec : m2.getVertices()) {
				System.out.println(vec);
			}
			System.out.println("\n");*/
			//System.out.println(m2.getLocation());
			//m2.setLocation(vec(-5,-5,-5));
			//m2.render();
			/*glBegin(GL_TRIANGLES);
			texPlot(0,0,-1,0,-2);
			texPlot(0,1,1,0,-2);
			texPlot(1,1,0,2,-2);
			glEnd();*/
			//m.render();
			//m3.render();
			glBindTexture(GL_TEXTURE_2D, 0);
			crateModel.getModel().rotateAroundX(vec(5,3,3),1);
			c2.getModel().rotateAroundY(vec(0,3,1), 2);
			//c2.getModel().render();
			c3.getModel().rotateAroundZ(vec(3,-2,-5), 5);
			//c3.getModel().render();
			//crateModel.getModel().render();
			/*glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glVertexPointer(3, GL_FLOAT, 0, 0l);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glDrawArrays(GL_TRIANGLES, 0, 6);*/
			//texPoint(0,0,-1,-1,-2);
			rotateX(vector, 1, vector);
			glBegin(GL_LINES);
			plot(0,0,0);
			plot(position);
			glEnd();
			Vector3f directionVector = constructDirection(rotation);
			Vector3f.add(directionVector, position, directionVector);
			glBegin(GL_LINES);
			//plot(directionVector);
			plot(0,0,0);
			plot(crateModel.getModel().getLocation());
			plot(0,0,0);
			plot(position);
			glEnd();
			for (CrateWatchingModel model : bullets) {
				model.setLocation(translate(model.getLocation(), scaled(scaledToTime(projectileVelocity, delta), 10)));
			//c5.getModel().rotateAroundX(c5.getModel().getLocation(), 1);
			}
			c5.getModel().rotateModelX(1);
			
			
			
			
			for (CrateWatchingModel model1: models) {
				model1.render();
			}
			
			
			
			
			
			updateInput();
			updateTime();
			//velocity = point(.01f,.01f,.01f);
			//rotation.x += .01f;
			Vector3f.add(scaledToTime(acceleration, delta), velocity, velocity);
			Vector3f.add(scaledToTime(velocity, delta), position, position);
			//System.out.println("" + velocity.x + velocity.y + velocity.z);
			//System.out.println(scaledVelocity(velocity));
			if (!isCrouching) {
				if (position.y < 2) {
					position.y = 2;
					velocity.y = 0;
					acceleration.y = 0;
					inAir = false;
				}
			} else {
				if (position.y < 1) {
					position.y = 1;
					velocity.y = 0;
					acceleration.y = 0;
					inAir = false;
				}
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
	/*public static boolean isKey(int key) {
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
	}*/
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
	
	/*public static Vector3f rotateX(Vector3f vector, float angle) {
		Vector3f newVector = new Vector3f(vector);
		FloatBuffer rotationMatrixData = createBuffer(new float[]{1,0,0,0,(float) cos(angle),(float) sin(angle),0,(float) -sin(angle),(float) cos(angle)});
		Matrix3f transformation = new Matrix3f();
		transformation.load(rotationMatrixData);
		Matrix3f.transform(transformation, newVector, newVector);
		return newVector;
	}*/
	public static FloatBuffer createBuffer(float[] list) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.length);
		buffer.put(list);
		buffer.flip();
		return buffer;
	}
	
	public void updateInput() {
		if (Mouse.isGrabbed()) {
			rotation.y += .5f * Mouse.getDX();
			rotation.x -= .5f * Mouse.getDY();
			rotation.y = rotation.y % 360;
		}
		if (!Mouse.isGrabbed() && leftMouse() && isOnScreen()) {
			Mouse.setGrabbed(true);
		}
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
		while(Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_LCONTROL) {
				if (Keyboard.getEventKeyState()) {
					if (flying) {
						flying = false;
						acceleration.y += -10;
					} else {
						flying = true;
						acceleration = vec(0,0,0);
					}
					//System.out.println((flying));
				}
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT) {
				if (Keyboard.getEventKeyState()) {
					if (!flying) {
						position.y -= 1f;
						walkingSpeed = 3;
						isCrouching = true;
					}
				} else {
					if (!flying) {
						position.y += 1;
						walkingSpeed = 10;
						isCrouching = false;
					}
				}
			}
		}
		while (Mouse.next()) {
			if (Mouse.getEventButton() == 0) {
				if (Mouse.getEventButtonState()) {
					CrateWatchingModel m = new CrateWatchingModel(position);
					m.load("res/models/Octahedron.txt");
					models.add(m);
					bullets.add(m);
					//models.get(2).setLocation(new Vector3f(position));
					projectileVelocity = scaled(constructDirection(rotation), 5);
				}
			}
		}
		if (Mouse.isButtonDown(1)) {
			walkingSpeed = sprintingSpeed;
			flyingSpeed = 20;
		} else if (!isCrouching) {
			walkingSpeed = 10;
			flyingSpeed = 7;
		}
		if (isKey(Keyboard.KEY_LSHIFT)) {
			if (!inAir) {
				//walkingSpeed = 3;
				//isCrouching = true;
				//position.y -= .5f;
			} else {
				//isCrouching = false;
				
				if (flying) {
					velocity.y = -flyingSpeed;
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
				velocity.y = flyingSpeed;
				inAir = true;
			}
		}
		if (flying && !isKey(Keyboard.KEY_SPACE) && !isKey(Keyboard.KEY_LSHIFT)) {
			velocity.y = 0;
		}
		if (isKey(Keyboard.KEY_ESCAPE)) {
			Mouse.setGrabbed(false);
		}
	}
	public void setPosition(Vector3f vec) {
		this.position = vec;
	}
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	public void setRotation(Vector3f vec) {
		this.rotation = vec;
	}
}
