package tilegame.state;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.worlds.World;
/**
 * This class acts as the state that allows the user to create and modify the maps in the game.
 * @author Mitchell Hoppe
 *
 */
public class MapBuilderState extends State{

	private World world;
	
	public MapBuilderState(Handler handler){
		super(handler);
		//At this point in time the decision for a new map or existing map has been made.
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