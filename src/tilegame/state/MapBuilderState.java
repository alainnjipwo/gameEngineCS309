package tilegame.state;

import java.awt.Graphics;

import tilegame.*;
import tilegame.debug.Terminal;
import tilegame.input.Input;
import tilegame.worlds.Map;
/**
 * This class acts as the state that allows the user to create and modify the maps in the game.
 * @author Mitchell Hoppe
 *
 */
public class MapBuilderState extends State{

	private Map map;
	
	Terminal t;
	
	public MapBuilderState(Handler handler, String path){
		super(handler);
		//At this point in time the decision for a new map or existing map has been made.
		
		t = new Terminal(handler);
		
		map = new Map(handler, path);
		handler.setWorld(map);
	}
	
	@Override
	public void update() {
		
		map.update();
		
		t.update();
		
		if(handler.getInput().isKeyDown(Input.KEY_ENTER))  t.isOpen = true;
		if(handler.getInput().isKeyDown(Input.KEY_ESCAPE)) t.isOpen = false;
		if(t.isOpen) return;
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
		
		t.render(g);
	}

}