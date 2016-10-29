package com.waataja.TextBasedGames;
import java.util.ArrayList;

public class TestTextBasedGame extends TextBasedGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestTextBasedGame();
	}

	@Override
	public void ifNotCommand(String line) {
		// TODO Auto-generated method stub
		write("That is not a valid command.");
	}

	@Override
	public void setUpGameState() {
		// TODO Auto-generated method stub
		getAvailableActions().add(new TestAction("Test"));
		getAvailableActions().add(new TestAction("Test Phrase"));
	}
	class TestAction extends Action {

		public TestAction(String command) {
			super(command);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void performAction(String subject) {
			// TODO Auto-generated method stub
			write("You activated a test action with keyword \"" + subject + "\".");
		}
	}
}
