package com.waataja.DungeonCrawlerTest;
import static com.waataja.Utils.*;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import static com.waataja.Utils.*;

public class InputTaker {
	private DungeonCrawlerTest game;
	private ArrayList<Listener> listeners;
	private Listener w;
	private Listener s;
	private Listener a;
	private Listener d;
	private Listener up;
	private Listener down;
	private Listener left;
	private Listener right;
	private Listener leftMouse;
	private Listener rightMouse;
	private MagicPattern pattern;
	public InputTaker(DungeonCrawlerTest game) {
		this.game = game;
		listeners = new ArrayList<Listener>();
		w = new Listener(Keyboard.KEY_W);
		s = new Listener(Keyboard.KEY_S);
		a = new Listener(Keyboard.KEY_A);
		d = new Listener(Keyboard.KEY_D);
		up = new Listener(Keyboard.KEY_UP);
		down = new Listener(Keyboard.KEY_DOWN);
		left = new Listener(Keyboard.KEY_LEFT);
		right = new Listener(Keyboard.KEY_RIGHT);
		
		leftMouse = new Listener(0, false);
		rightMouse = new Listener(1, false);
		listeners.add(w);
		listeners.add(s);
		listeners.add(a);
		listeners.add(d);
		listeners.add(up);
		listeners.add(down);
		listeners.add(left);
		listeners.add(right);
		listeners.add(leftMouse);
		listeners.add(rightMouse);
	}
	public void setGame(DungeonCrawlerTest game) {
		this.game = game;
	}
	public void takeInput() {
		updateListeners();
		//float amount = scaledToTime(game.getTranslationSpeed(), game.getDelta());
		float amount = scaledToTime(30, game.getDelta());
		ArrayList<Listener> dirs = new ArrayList<Listener>();
		dirs.add(up);
		dirs.add(down);
		dirs.add(left);
		dirs.add(right);
		ArrayList<Listener> wasd = new ArrayList<Listener>();
		wasd.add(w);
		wasd.add(a);
		wasd.add(s);
		wasd.add(d);
		boolean wasMoved = false;
		if (w.onlyDown(wasd)) {
			//game.getTranslation().y -= amount;
			//game.getPlayer().getPosition().y -= amount;
			game.getPlayer().setVelocity(withLength(vec(0,-1),game.playerVelocity));
			wasMoved = true;
		}
		if (s.onlyDown(wasd)) {
			//game.getTranslation().y += amount;
			//game.getPlayer().getPosition().y += amount;
			game.getPlayer().setVelocity(withLength(vec(0,1),game.playerVelocity));
			wasMoved = true;
		}
		if (a.onlyDown(wasd)) {
			//game.getTranslation().x -= amount;
			//game.getPlayer().getPosition().x -= amount;
			game.getPlayer().setVelocity(withLength(vec(-1,0),game.playerVelocity));
			wasMoved = true;
		}
		if (d.onlyDown(wasd)) {
			//game.getTranslation().x += amount;
			//game.getPlayer().getPosition().x += amount;
			game.getPlayer().setVelocity(withLength(vec(1,0),game.playerVelocity));
			wasMoved = true;
		}
		if (w.onlyTwoDown(d, wasd)) {
			game.getPlayer().setVelocity(withLength(vec(1,-1),game.playerVelocity));
			wasMoved = true;
		}
		if (w.onlyTwoDown(a, wasd)) {
			game.getPlayer().setVelocity(withLength(vec(-1,-1),game.playerVelocity));
			wasMoved = true;
		}
		if (s.onlyTwoDown(d, wasd)) {
			game.getPlayer().setVelocity(withLength(vec(1,1),game.playerVelocity));
			wasMoved = true;
		}
		if (s.onlyTwoDown(a, wasd)) {
			game.getPlayer().setVelocity(withLength(vec(-1,1),game.playerVelocity));
			wasMoved = true;
		}
		if (!wasMoved) {
			game.getPlayer().setVelocity(vec(0,0));
		}
		Vector2f vel = vec(0,0);
		if (up.onlyPressed(dirs)) {
			System.out.println("up");
			game.getSpell().up(game);
		}
		if (down.onlyPressed(dirs)) {
			game.getSpell().down(game);
			System.out.println("down");
		}
		if (left.onlyPressed(dirs)) {
			game.getSpell().left(game);
			System.out.println("left");
		}
		if (right.onlyPressed(dirs)) {
			game.getSpell().right(game);
			System.out.println("right");
		}
		if (up.onlyTwoPressed(right, dirs)) {
			game.getSpell().upRight(game);
			System.out.println("upRight");
		}
		if (up.onlyTwoPressed(left, dirs)) {
			game.getSpell().upLeft(game);
			System.out.println("upLeft");
		}
		if (down.onlyTwoPressed(right, dirs)) {
			game.getSpell().downRight(game);
			System.out.println("downRight");
		}
		if (down.onlyTwoPressed(left, dirs)) {
			game.getSpell().downLeft(game);
			System.out.println("downLeft");
		}
		if (!equalVecs(vel, vec(0,0))) {
			BasicFireballEntity ent = new BasicFireballEntity(game.getPlayer().getCenter(), vel);
			game.getEntities().add(ent);
		}
		if (leftMouse.down && !rightMouse.down) {
			if (!game.mouseOver(game.getPlayer())) {
				Vector2f velocity = sub(game.getMousePosition(), game.onScreenCoord(game.getPlayer().getCenter()));
				velocity = withLength(velocity, scaledToTime(game.getMovementSpeed(), game.getDelta()));
				game.getPlayer().setPosition(add(game.getPlayer().getPosition(), velocity));
			}
		}
		if (rightMouse.down && leftMouse.wasPressed) {
			pattern = new MagicPattern();
		}
		if (leftMouse.down && leftMouse.wasPressed) {
			pattern = new MagicPattern();
		}
		if (rightMouse.down && leftMouse.down) {
			Vector2f mousePos = game.getWorldMousePosition();
			if (!equalVecs(mousePos, pattern.lastPoint())) {
				pattern.addPoint(mousePos);
			}
		}
		if ((rightMouse.down && leftMouse.wasReleased) || (leftMouse.down && rightMouse.wasReleased)) {
			if (pattern.getPoints().size() > 1) {
				pattern.process(game);
				for (Vector2f vec : pattern.getPoints()) {
					System.out.println(vec);
				}
				for (float angle : pattern.getAngles()) {
					System.out.println(angle);
				}
			}
		}
		game.mouseOver(game.getPlayer());
		if (game.onScreenCoord(game.getPlayer()).x < 5) {
			game.getTranslation().x = game.getPlayer().getX() - 5;
		}
		if (game.onScreenCoord(game.getPlayer()).y < 5) {
			game.getTranslation().y = game.getPlayer().getY() - 5;
		}
		if (game.onScreenCoord(game.getPlayer()).x+game.getPlayer().getWidth()>game.getWidth()-5) {
			game.getTranslation().x = game.getPlayer().getX()+game.getPlayer().getWidth()+5 - game.getWidth();
		}
		
		if (game.onScreenCoord(game.getPlayer()).y+game.getPlayer().getHeight()>game.getHeight()-5) {
			game.getTranslation().y = game.getPlayer().getY()+game.getPlayer().getHeight()+5 - game.getHeight();
		}
		if (game.getPlayer().getX() < 0) {
			game.getPlayer().setX(0);
		}
		if (game.getPlayer().getY() < 0) {
			game.getPlayer().setY(0);
		}
	}
	public void updateListeners() {
		for (Listener lis : listeners) {
			lis.updateState();
			lis.wasPressed = false;
			lis.wasReleased = false;
		}
		while (Keyboard.next()) {
			for (Listener lis : listeners) {
				lis.updatePressed();
			}
		}
		while (Mouse.next()) {
			for (Listener lis : listeners) {
				lis.updatePressed();
			}
		}
	}
	class Listener {
		int key;
		boolean onKeyboard;
		boolean down;
		boolean wasPressed;
		boolean wasReleased;
		Listener(int key) {
			this(key, true);
		}
		Listener(int key, boolean onKeyboard) {
			this.key = key;
			this.onKeyboard = onKeyboard;
		}
		public void updateState() {
			if (onKeyboard) {
				if (Keyboard.isKeyDown(key)) {
					down = true;
				} else {
					down = false;
				}
			} else {
				if (Mouse.isButtonDown(key)) {
					down = true;
				} else {
					down = false;
				}
			}
		}
		public void updatePressed() {
			if (onKeyboard) {
				if (Keyboard.getEventKey() == key) {
					if (Keyboard.getEventKeyState() == true) {
						wasPressed = true;
					} else {
						wasReleased = true;
					}
				}
			} else {
				if (Mouse.getEventButton() == key) {
					if (Mouse.getEventButtonState() == true) {
						wasPressed = true;
					} else {
						wasReleased = true;
					}
				}
			}
		}
		public boolean onlyDown(ArrayList<Listener> list) {
			boolean only = true;
			if (down) {
				for (Listener listener : list) {
					if (listener != this) {
						if (listener.down) {
							only = false;
						}
					}
				}
			} else {
				only = false;
			}
			return only;
		}
		public boolean onlyTwoDown(Listener otherList, ArrayList<Listener> list) {
			boolean only2 = true;
			if (down && otherList.down) {
				for (Listener lis : list) {
					if (lis != this && lis != otherList) {
						if (lis.down) {
							only2 = false;
						}
					}
				}
			} else {
				only2 = false;
			}
			return only2;
		}
		public boolean onlyPressed(ArrayList<Listener> list) {
			boolean only = true;
			if (wasPressed) {
				for (Listener lis : list) {
					if (lis != this) {
						if (lis.wasPressed) {
							only = false;
						}
					}
				}
			} else {
				only = false;
			}
			return only;
		}
		public boolean onlyTwoPressed(Listener otherList, ArrayList<Listener> list) {
			boolean only2 = true;
			if (wasPressed && otherList.wasPressed) {
				for (Listener lis : list) {
					if (lis != this && lis != otherList) {
						if (lis.wasPressed) {
							only2 = false;
						}
					}
				}
			} else {
				only2 = false;
			}
			return only2;
		}
	}
}
