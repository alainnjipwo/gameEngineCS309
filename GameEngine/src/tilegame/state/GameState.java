package tilegame.state;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.worlds.World;
/**
 * This class acts as the game state of the game. It is responsible for loading in the world and updating and rendering it.
 * @author Kenneth Lange
 *
 */
public class GameState extends State{

	private World world;
	
	public GameState(Handler handler){
		super(handler);
		world = new World(handler,"res/worlds/world1.txt");
		handler.setWorld(world);
	}
	
	@Override
	public void update() {
		world.update();
	}

	@Override
	public void render(Graphics g) {
		world.render(g);
	}

}