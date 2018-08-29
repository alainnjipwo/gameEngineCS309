package tilegame.state;

import java.awt.Graphics;

import tilegame.Handler;
/**
 * This is an abstract state template class. All states can use these methods and have to have abstract methods in them. 
 * @author Kenneth Lange
 *
 */
public abstract class State {

	private static State currentState = null;
	
	//STATE MANAGER
	public static void setState(State state){
		currentState = state;
	}
	
	public static State getState(){
		return currentState;
	}
	
	//CLASS
	protected Handler handler;
	
	public State(Handler handler){
		this.handler = handler;
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
}
