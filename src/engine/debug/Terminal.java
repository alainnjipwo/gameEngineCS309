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
	
//	long char_cooldown;
	char char_prev;
	
	ArrayList<String> display_list;
	ArrayList<String> command_list;
	String command_line;
	int caret = 0;
	public Handler handler;
	
	public boolean isOpen;
	private boolean new_command;
	
	public Terminal(Handler handler) {
		this.handler = handler;
		
//		char_cooldown = System.currentTimeMillis();
		char_prev = 0;
		
		isOpen 		= false;
		new_command = false;
		
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
		if(handler.getInput().isKeyPressed(char_prev))
			char_prev = 0;
		
		checkInput();
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
	
//	private boolean charOffCooldown() {
//		return System.currentTimeMillis() - char_cooldown > 100000000;
//	}
	
//	private int getCooldown() {
//		return (int) ((int)System.currentTimeMillis() - char_cooldown);
//	}
	
//	private void resetCooldown() {
//		char_cooldown = System.currentTimeMillis();
//	}
	
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
		
		if(handler.getInput().getPressedKeys().size() == 0) {}
		
		pressedEnter();
		
		pressedBackspace();
		
		if(handler.getInput().getPressedKeys().size() != 0) {
			
			int i = (int)(handler.getInput().getPressedKeys().get(handler.getInput().getPressedKeys().size() - 1));
			char pressedKey = (char)( i);
			System.out.println((int)(pressedKey));
			if(pressedKey >= 32 && pressedKey <= 90 && 
					(pressedKey != char_prev)) {
				addChar(pressedKey);
			}
		}
			
	}

	private void pressedEnter() {
		if(handler.getInput().isKeyPressed(Input.KEY_ENTER)	&& command_line.length() != 0) {
			command_list.add(0,command_line);
			print(command_line);
			new_command = true;
			command_line = "";
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
		
		Color c = new Color(255,255,255);
		g.setColor(c);
		g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
		g.drawString("pc:" + char_prev, x, y);
	}
	
}
