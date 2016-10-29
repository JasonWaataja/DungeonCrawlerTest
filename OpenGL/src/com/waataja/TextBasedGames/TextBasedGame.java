package com.waataja.TextBasedGames;

import java.util.ArrayList;

public abstract class TextBasedGame implements TextProcessor {
	private TextSystem textSystem;
	private ArrayList<Action> availableActions;
	
	public TextBasedGame() {
		super();
		setTextSystem(new TextSystem(this));
		availableActions = new ArrayList<Action>();
		setUpGameState();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	public TextSystem getTextSystem() {
		return textSystem;
	}
	public void setTextSystem(TextSystem system) {
		textSystem = system;
	}
	public void write(String message) {
		textSystem.write(message);
	}
	public void processLine(String line) {
		String[] lineWords = line.split(" ");
		boolean isACommand = false;
		for (Action action : availableActions) {
			boolean isCommand = true;
			String[] commandWords = action.getCommand().split(" ");
			for (int i = 0; i < commandWords.length; i++) {
				try {
					if (!commandWords[i].equals(lineWords[i])) {
						isCommand = false;
					}
				} catch (IndexOutOfBoundsException e) {
					isCommand = false;
				}
			}
			if (isCommand) {
				isACommand = true;
				int beginIndex = commandWords.length - 1;
				for (String word : commandWords) {
					beginIndex += word.length();
				}
				String subject = line.substring(beginIndex);
				action.performAction(subject);
			}
		}
		if (!isACommand) {
			ifNotCommand(line);
		}
	}
	public ArrayList<Action> getAvailableActions() {
		return availableActions;
	}
	public void clearActions() {
		for (Action action : availableActions) {
			availableActions.remove(action);
		}
	}
	public abstract void ifNotCommand(String line);
	public abstract void setUpGameState();
}
