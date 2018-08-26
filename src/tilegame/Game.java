package tilegame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import tilegame.display.Display;
import tilegame.gfx.Assets;
import tilegame.gfx.GameCamera;
import tilegame.input.Input;
import tilegame.input.Mouse;
import tilegame.state.GameState;
import tilegame.state.MenuState;
import tilegame.state.State;

/**
 * Holds all of the base code for the game.
 * @author Kenneth Lange
 * M: Engine
 */
public class Game implements Runnable{ //Must implement Runnable in order for it to use a thread
	private Display display;
	private int width, height;
	public String title;
		
	private boolean running = false; //Determines whether the game is running or not
	private Thread thread; 
	
	private BufferStrategy bs;
	private Graphics g;
	
	//States
	public State gameState;
	public State menuState;
	
	//Input
	private Input input;
	private Mouse mouse;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	public Game(String title, int width){
		this.width = width;
		height = width / 16 * 9;
		this.title = title;
		input = new Input();
		mouse = new Mouse();
	}
	/**
	 * This method initializes the graphics for the game
	 */
	private void init(){
		display = new Display(title, width, height);//creates new display
		//Inputs
		display.getFrame().addKeyListener(input);
		display.getFrame().addMouseListener(mouse);
		display.getFrame().addMouseMotionListener(mouse);
		display.getCanvas().addMouseListener(mouse);
		display.getCanvas().addMouseMotionListener(mouse);
		//Pictures
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		
		//Initialize States
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		State.setState(menuState);//Sets the initial state of the window upon starting the game
	}
	/**
	 * This method acts as an updater keeping track of objects' and window's new locations/
	 */
	private void update(){//updates positions etc.		
		if(State.getState() != null)
			State.getState().update();
	}
	/**
	 * This method takes the updated locations and draws them to the screen.
	 */
	private void render(){//draws updated positions to the screen
		bs = display.getCanvas().getBufferStrategy(); //Basically a screen before the actual screen.
		if(bs == null){
			display.getCanvas().createBufferStrategy(3); //Sets screen to have 3 screens premade before user sees it.
			return;
		}
		g = bs.getDrawGraphics(); //Allows drawing to canvas
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//DRAW HERE!
		
		if(State.getState() != null)
			State.getState().render(g);
		
		//END DRAWING!
		bs.show(); //draws objects
		g.dispose(); //completes graphics properly
	}
	/**
	 * This method runs the game and sets a base for how often it can update and render.
	 */
	public void run() {
		
		init();
		
		int fps = 60;
		double timePerTick = 1E9 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime(); //Current time on computer in nanoseconds.
		long timer = 0;
		@SuppressWarnings("unused")
		int ticks = 0;
		
		while(running){
			now = System.nanoTime(); //Checks currect time
			delta += (now - lastTime) / timePerTick;
			timer+= now - lastTime;
			lastTime = now; //Updates last time
			if(delta >= 1){
				update(); //updates positions of game objects and window etc.
				render(); //renders updated positions
				ticks++;
				delta--;
			}
			if(timer >= 1E9){
				//Debug.Log("Ticks and Frames: " + ticks); //Displays the Frames per second in the console
				ticks = 0;
				timer = 0;
			}
		}
		stop();
	}
	/**
	 * This method starts the thread and sets the boolean running to true
	 */
	public synchronized void start(){
		if(running) return; //If game is already running, don't reinitialize
		running = true; //Set engine to know game is running
		thread = new Thread(this);
		thread.start(); //Calls run() method by default
	}
	/**
	 * This method stops the thread and sets the boolean running to false
	 */
	public synchronized void stop(){
		if(!running) return; //If game is already stopped, don't try to stop
		running = false;
		try {
			thread.join(); //Properly closes thread 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Getters and Setters
	public Input getInput(){
		return input;
	}
	
	public Mouse getMouse() {
		return mouse;
	}
	
	public GameCamera getGameCamera(){
		return gameCamera;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
}
