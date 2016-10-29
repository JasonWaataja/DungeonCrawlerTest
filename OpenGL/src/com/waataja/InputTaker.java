package com.waataja;

public interface InputTaker {
	public CrateWatcher getGame();
	public void setGame(CrateWatcher watcher);
	public void processMouse();
	public void processKeyboard();
}
