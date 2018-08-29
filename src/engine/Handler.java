package tilegame;

import tilegame.gfx.GameCamera;
import tilegame.input.Input;
import tilegame.input.Mouse;
import tilegame.worlds.World;
/**
 * This class is a handler for the game for ease of access throughout the entire game.
 * It consists of getters and setters.
 * @author Kenneth Lange
 *
 */
public class Handler {

	private Game game;
	private World world;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public Input getInput(){
		return game.getInput();
	}
	
	public Mouse getMouse(){
		return game.getMouse();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
