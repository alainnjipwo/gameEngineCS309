package engine.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import engine.Handler;
import engine.input.Input;

public class Terminal{
	
	int FONT_SIZE = 16;
	int NUM_LINES = 5;
	int TRANSPARENCY = 50;
	
	int WIDTH = 500;
	
	int X_BORDER = 50;
	int Y_BORDER = 50;
	
	int REPEAT_DELAY 	= 500;
	int REPEAT_FREQ 	= 75;
	
	//How long until the char stops repeating
	long char_cooldown;
	//Hoe fast the char repeats
	long char_repeatduration;
	char char_prev;
	
	ArrayList<String> display_list;
	ArrayList<String> command_list;
	String command_line;
	int caret;
	public Handler handler;
	
	private boolean isOpen;
	private boolean new_command;
	
	public Terminal(Handler handler) {
		this.handler = handler;
		
		char_cooldown = System.currentTimeMillis();
		char_prev = 0;
		
		isOpen 		= false;
		new_command = false;
		caret = 0;
		command_line = "";
		
		display_list = new ArrayList<String>();
		command_list = new ArrayList<String>();
	}
	
	public String poll() {
		if(!new_command) return null;
		new_command = false;
		return command_list.get(0);
	}
	
	public void update() {
		checkInput();
		if(char_prev != 0 && !handler.getInput().isKeyDown(char_prev))
			char_prev = 0;
	}
	
	public void print(String s) {
		display_list.add(0,s);
	}

	public void render(Graphics g) {
		if(!isOpen) return;
		
		renderBackground(g);
				
		renderCommandLine(g);
		
		renderHistory(g);
		
		renderDebug(g);
	}
	
	private boolean 	charOffCooldown() 		{	return System.currentTimeMillis() - char_cooldown > REPEAT_DELAY;	}
	private int 		getCooldown() 			{	return (int) ((int)System.currentTimeMillis() - char_cooldown);		}
	private void 		resetCooldown() 		{	char_cooldown = System.currentTimeMillis();							}
	
	private boolean 	charRepeatOffCooldown() {	return System.currentTimeMillis() - char_repeatduration > REPEAT_FREQ;	}
	private int 		getRepeatCooldown() 	{	return (int) ((int)System.currentTimeMillis() - char_repeatduration);	}
	private void 		resetRepeatCooldown() 	{	char_repeatduration = System.currentTimeMillis();						}
	
	private boolean addChar(char c) {
		if(command_line.length() < caret) {return false;}
		command_line = command_line.substring(0, caret) + c + command_line.substring(caret);
		char_prev = c;
		caret++;
		return true;
	}
	
	private boolean removeChar() {
		if(command_line.length() < caret || caret == 0) {return false;}
		command_line = command_line.substring(0, caret - 1) + command_line.substring(caret);
		caret--;
		return true;
	}

	private void checkInput() {
		if(!isOpen) return;
		
		if(handler.getInput().isKeyPressed(Input.KEY_UP));
		if(handler.getInput().isKeyPressed(Input.KEY_DOWN));
		if(handler.getInput().isKeyPressed(Input.KEY_LEFT));
		if(handler.getInput().isKeyPressed(Input.KEY_RIGHT));
		
		if(handler.getInput().getPressedKeys().size() == 0) {
			resetCooldown();
		}
		
		pressedEnter();
		pressedDelete();
		pressedBackspace();
		
		
		if(handler.getInput().getPressedKeys().size() != 0) {
			char pressedKey = (char)((int)(handler.getInput().getPressedKeys().get(handler.getInput().getPressedKeys().size() - 1)));
			if(pressedKey >= 32 && pressedKey <= 90 && 
					((charOffCooldown() && charRepeatOffCooldown()) || pressedKey != char_prev)) {
				addChar(pressedKey);
				resetRepeatCooldown();
				if(pressedKey != char_prev)
					resetCooldown();
				
			}
		}
	}

	private void pressedEnter() {
		if(handler.getInput().isKeyPressed(Input.KEY_ENTER)	&& command_line.length() != 0) {
			command_list.add(0,command_line);
			print(command_line);
			new_command = true;
			caret = 0;
			command_line = "";
		}
	}
	
	private void pressedDelete() {
		if(handler.getInput().isKeyPressed(Input.KEY_DELETE)) {
			command_line = "";
			caret = 0;
		}
	}
	
	private void pressedBackspace() {
		if(handler.getInput().isKeyPressed(Input.KEY_BACKSPACE)) {
			removeChar();
		}
	}
	
	private void renderBackground(Graphics g) {
		int alpha = (int) (255 * TRANSPARENCY / 100);
		int LINE_SPACE = FONT_SIZE / 2;
		
		int width = WIDTH;
		int height = (NUM_LINES + 1) * FONT_SIZE;
		int x = X_BORDER;
		int y = handler.getHeight() - Y_BORDER - height;
		
		Color c = new Color(0, 0, 0, alpha);
		g.setColor(c);
		g.fillRect(x, y, width, height);
		c = new Color(0, 0, 0, alpha);
		g.setColor(c);
		g.fillRect(x, handler.getHeight() - Y_BORDER - FONT_SIZE - (LINE_SPACE / 2), width, FONT_SIZE + LINE_SPACE / 2);
	}
	
	private void renderCommandLine(Graphics g) {
		int LINE_SPACE = FONT_SIZE / 2;
				
		Color c = new Color(255,255,255);
		g.setColor(c);
		g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
		g.drawString(command_line, X_BORDER + LINE_SPACE, handler.getHeight() - Y_BORDER - LINE_SPACE);
	}
	
	private void renderHistory(Graphics g) {
		int LINE_SPACE = FONT_SIZE / 2;
		
		for (int i = 0;i < display_list.size() && i < NUM_LINES; i++) {
			g.drawString(display_list.get(i), X_BORDER + LINE_SPACE, handler.getHeight() - LINE_SPACE - Y_BORDER - (FONT_SIZE * (i + 1)));
		}
	}
	
	private void renderDebug(Graphics g) {
		int height = (NUM_LINES + 1) * FONT_SIZE;
		int x = X_BORDER;
		int y = handler.getHeight() - Y_BORDER - height;
		
		char pressedKey;
		if(handler.getInput().getPressedKeys().size() != 0)
			pressedKey = (char)((int)(handler.getInput().getPressedKeys().get(handler.getInput().getPressedKeys().size() - 1)));
		else 
			pressedKey = ' ';
		
		Color c = new Color(255,255,255);
		g.setColor(c);
		g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
		g.drawString("" + ((charOffCooldown() && charRepeatOffCooldown()) || pressedKey != char_prev) + " " + 
		charOffCooldown() + " " + charRepeatOffCooldown() + " " + (pressedKey != char_prev) + pressedKey + "_" + char_prev, x, y);
	}

	public boolean isOpen() {
		return isOpen;
	}
	
	public void setOpen(boolean b) {
		if(b) {
			char_cooldown = System.currentTimeMillis();
			char_prev = 0;
			new_command = false;
			caret = 0;
			command_line = "";
		}
		else {}
		isOpen = b;
	}
	
}
