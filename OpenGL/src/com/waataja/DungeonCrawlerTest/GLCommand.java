package com.waataja.DungeonCrawlerTest;
import static com.waataja.Utils.*;
import static org.lwjgl.opengl.GL11.*;
public class GLCommand {
	private Command command;
	private float[] numbers;
	public GLCommand(Command command) {
		this.command = command;
	}
	public GLCommand(Command command, float x, float y) {
		this(command);
		numbers = new float[]{x, y};
	}
	public GLCommand(Command command, float x, float y, float z) {
		this(command);
		numbers = new float[]{x, y, z};
	}
	public GLCommand(Command command, float x, float y, float z, float w, float q) {
		this(command);
		numbers = new float[]{x, y, z, w, q};
	}
	public GLCommand(Command command, float[] numbers) {
		this(command);
		this.numbers = numbers;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command type) {
		this.command = type;
	}
	public void performCommand() {
		switch(command) {
		case plot: plot(numbers[0], numbers[1]); break;
		case tex: tex(numbers[0], numbers[1]); break;
		case texPlot: texPlot(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4]); break;
		case triangles: glBegin(GL_TRIANGLES); break;
		case quads: glBegin(GL_QUADS); break;
		case end: glEnd(); break;
		case color: color(numbers[0], numbers[1], numbers[2]); break;
		case resetColor: resetColor();
		}
	}
	public float[] getNumbers() {
		return numbers;
	}
	public void setNumbers(float[] numbers) {
		this.numbers = numbers;
	}
}
