package com.waataja.TextBasedGames;

public abstract class Action {
	private String command;
	TextBasedGame game;
	
	public Action(String command) {
		super();
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
	public void setCommand(TextBasedGame game, String newCommand) {
		this.game = game;
		command = newCommand;
	}
	public abstract void performAction(String subject);
}
