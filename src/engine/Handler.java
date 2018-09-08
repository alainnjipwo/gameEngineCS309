package engine;

import engine.gfx.GameCamera;
import engine.input.Input;
import engine.input.Mouse;
import engine.worlds.World;
/**
 * This class is a handler for the game for ease of access throughout the entire game.
 * It consists of getters and setters.
 * @author Kenneth Lange
 *
 */
public class Handler {

	private Engine engine;
	private World world;
	
	public Handler(Engine engine) {
		this.engine = engine;
	}
	
	public GameCamera getGameCamera(){
		return engine.getGameCamera();
	}
	
	public Input getInput(){
		return engine.getInput();
	}
	
	public Mouse getMouse(){
		return engine.getMouse();
	}
	
	public int getWidth(){
		return engine.getWidth();
	}
	
	public int getHeight(){
		return engine.getHeight();
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
