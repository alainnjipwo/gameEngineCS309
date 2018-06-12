package tilegame.debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;

import tilegame.Handler;
import tilegame.input.Input;

public class Terminal{
	
	int FONT_SIZE = 16;
	int NUM_LINES = 5;
	int TRANSPARENCY = 50;
	
	int WIDTH = 500;
	
	int X_BORDER = 50;
	int Y_BORDER = 50;
	
	ArrayList<String> display_list;
	ArrayList<String> command_list;
	String command_line;
	int caret = 0;
	public Handler handler;
	
	public boolean isOpen;
	private boolean new_command;
	
	public Terminal(Handler handler) {
		this.handler = handler;
		
		isOpen = false;
		new_command = false;
		
		command_line = "";
		
		display_list = new ArrayList<String>();
		command_list = new ArrayList<String>();
	}
	
	public String pop() {
		if(!new_command) return null;
		new_command = false;
		return command_list.get(0);
	}
	
	public void update() {
		checkInput();
	}

	public void render(Graphics g) {
		
		g.setColor(new Color(255,255,255));
		g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
		g.drawString(isOpen + "", 30, 60);
		
		if(!isOpen) return;
		
		int alpha = (int) (255 * TRANSPARENCY / 100);
		Color c = new Color(0, 0, 0, alpha);
		g.setColor(c);
		
		int x = X_BORDER;
		int y = handler.getHeight() - Y_BORDER - (NUM_LINES * FONT_SIZE);
		int width = WIDTH;
		int height = NUM_LINES * FONT_SIZE;
		
		g.fillRect(x, y, width, height);
		
		c = new Color(255,255,255);
		g.setColor(c);
		g.setFont(new Font("Courier New", Font.BOLD, FONT_SIZE));
		g.drawString(command_line, x, y);
	}
	
	private void checkInput() {
		if(!isOpen) return;
		
		if(handler.getInput().isKeyDown(Input.KEY_UP));
		if(handler.getInput().isKeyDown(Input.KEY_DOWN));
		if(handler.getInput().isKeyDown(Input.KEY_LEFT));
		if(handler.getInput().isKeyDown(Input.KEY_RIGHT));
		
//		if(handler.getInput().isKeyDown(Input.KEY_ENTER));
//		for (int i = 0; i < handler.getInput().getPressedKeys().size(); i++) {
//			System.out.println(handler.getInput().getPressedKeys().get(i));
//			char pressedKey = ((char) (int)(handler.getInput().getPressedKeys().get(i)));
//			if(pressedKey >= 48 && pressedKey <= 90)
//				command_line += pressedKey;
//			
//		}
		
		if(handler.getInput().isKeyDown(Input.KEY_A))command_line += 'a';
		if(handler.getInput().isKeyDown(Input.KEY_B))command_line += 'b';
		if(handler.getInput().isKeyDown(Input.KEY_C))command_line += 'c';
		if(handler.getInput().isKeyDown(Input.KEY_D))command_line += 'd';
		if(handler.getInput().isKeyDown(Input.KEY_E))command_line += 'e';
		if(handler.getInput().isKeyDown(Input.KEY_F))command_line += 'f';
		if(handler.getInput().isKeyDown(Input.KEY_G))command_line += 'g';
		if(handler.getInput().isKeyDown(Input.KEY_H))command_line += 'h';
		if(handler.getInput().isKeyDown(Input.KEY_I))command_line += 'i';
		if(handler.getInput().isKeyDown(Input.KEY_J))command_line += 'j';
		if(handler.getInput().isKeyDown(Input.KEY_K))command_line += 'k';
		if(handler.getInput().isKeyDown(Input.KEY_L))command_line += 'l';
		if(handler.getInput().isKeyDown(Input.KEY_M))command_line += 'm';
		if(handler.getInput().isKeyDown(Input.KEY_N))command_line += 'n';
		if(handler.getInput().isKeyDown(Input.KEY_O))command_line += 'o';
		if(handler.getInput().isKeyDown(Input.KEY_P))command_line += 'p';
		if(handler.getInput().isKeyDown(Input.KEY_Q))command_line += 'q';
		if(handler.getInput().isKeyDown(Input.KEY_R))command_line += 'r';
		if(handler.getInput().isKeyDown(Input.KEY_S))command_line += 's';
		if(handler.getInput().isKeyDown(Input.KEY_T))command_line += 't';
		if(handler.getInput().isKeyDown(Input.KEY_U))command_line += 'u';
		if(handler.getInput().isKeyDown(Input.KEY_V))command_line += 'v';
		if(handler.getInput().isKeyDown(Input.KEY_W))command_line += 'w';
		if(handler.getInput().isKeyDown(Input.KEY_X))command_line += 'x';
		if(handler.getInput().isKeyDown(Input.KEY_Y))command_line += 'y';
		if(handler.getInput().isKeyDown(Input.KEY_Z))command_line += 'z';
		if(handler.getInput().isKeyDown(Input.KEY_0))command_line += '0';
		if(handler.getInput().isKeyDown(Input.KEY_1))command_line += '1';
		if(handler.getInput().isKeyDown(Input.KEY_2))command_line += '2';
		if(handler.getInput().isKeyDown(Input.KEY_3))command_line += '3';
		if(handler.getInput().isKeyDown(Input.KEY_4))command_line += '4';
		if(handler.getInput().isKeyDown(Input.KEY_5))command_line += '5';
		if(handler.getInput().isKeyDown(Input.KEY_6))command_line += '6';
		if(handler.getInput().isKeyDown(Input.KEY_7))command_line += '7';
		if(handler.getInput().isKeyDown(Input.KEY_8))command_line += '8';
		if(handler.getInput().isKeyDown(Input.KEY_9))command_line += '9';
	}
	
}
