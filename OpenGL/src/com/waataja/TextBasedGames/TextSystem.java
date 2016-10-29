package com.waataja.TextBasedGames;
import static com.waataja.Utils.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class TextSystem implements KeyListener {
	private JFrame frame;
	private JTextArea displayArea;
	private JTextField inputBox;
	private JPanel panel;
	private JScrollPane scroller;
	TextProcessor textProcessor;
	public TextSystem(TextProcessor processor) {
		super();
		textProcessor = processor;
		setUpComponents();
		setUpState();
	}
	public TextSystem() {
		super();
		BasicTextProcessor processor = new BasicTextProcessor();
		processor.setTextSystem(this);
		setTextProcessor(processor);
		setUpComponents();
		setUpState();
		write("This text system was created without a text processor; a default one will be used");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TextSystem();
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyChar() == '\n') {
			textProcessor.processLine(inputBox.getText());
			inputBox.setText("");
		}
	}
	public void setUpComponents() {
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		panel = new JPanel();
		frame.setContentPane(panel);
		panel.setLayout(new BorderLayout());
		displayArea = new JTextArea();
		scroller = new JScrollPane(displayArea);
		displayArea.setLineWrap(true);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(BorderLayout.CENTER, scroller);
		inputBox = new JTextField();
		panel.add(BorderLayout.SOUTH, inputBox);
		displayArea.setEditable(false);
		inputBox.requestFocus();
		frame.setVisible(true);
	}
	public void setUpState() {
		inputBox.addKeyListener(this);
	}
	public void write(String message) {
		displayArea.append(message + "\n");
	}
	public void setTextProcessor(TextProcessor processor) {
		textProcessor = processor;
	}
	public class BasicTextProcessor implements TextProcessor {
		TextSystem system;
		public void processLine(String line) {
			// TODO Auto-generated method stub
			write("You typed: \"" + line + "\".");
		}
		@Override
		public void write(String message) {
			// TODO Auto-generated method stub
			getTextSystem().write(message);
		}
		@Override
		public TextSystem getTextSystem() {
			// TODO Auto-generated method stub
			return system;
		}
		@Override
		public void setTextSystem(TextSystem system) {
			// TODO Auto-generated method stub
			this.system = system;
		}
	}
}
