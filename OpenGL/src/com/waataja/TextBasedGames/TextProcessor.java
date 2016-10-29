package com.waataja.TextBasedGames;

public interface TextProcessor {
	public void processLine(String line);
	public void write(String message);
	public TextSystem getTextSystem();
	public void setTextSystem(TextSystem system);
}
