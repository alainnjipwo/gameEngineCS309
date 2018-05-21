package tilegame.state;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.worlds.Map;
/**
 * This class acts as the state that allows the user to create and modify the maps in the game.
 * @author Mitchell Hoppe
 *
 */
public class MapBuilderState extends State{

	private Map map;
	
	public MapBuilderState(Handler handler){
		super(handler);
		//At this point in time the decision for a new map or existing map has been made.
		map = new Map(handler,"res/worlds/world1.txt");
		handler.setWorld(map);
	}
	
	@Override
	public void update() {
		map.update();
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
	}

}