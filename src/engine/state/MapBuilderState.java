package engine.state;

import java.awt.Graphics;

import engine.*;
import engine.debug.Terminal;
import engine.input.Input;
import engine.managers.entities.creatures.*;
import engine.managers.entities.nonmoving.*;
import engine.tile.Tile;
import engine.worlds.Map;
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
		
		t.update();
		pollCommands();
		
		if(handler.getInput().isKeyPressed(Input.KEY_ENTER))  t.setOpen(true);
		if(handler.getInput().isKeyPressed(Input.KEY_ESCAPE)) t.setOpen(false);
		if(t.isOpen()) return;
		
		map.update();
	}

	private void pollCommands() {
		if(!t.isOpen()) return;
		
		String command = t.poll();
		if(command == null) return;
		String a[]= command.split(" ");
		if(a.length == 2) {
			if(a[0].equals("settile")) {settile(a[1]);}
			if(a[0].equals("toggle"))  {toggle(a[1]);}
		} if(a.length == 4) {
			if(a[0].equals("spawn")) {spawn(a[1], a[2], a[3]);}
		}
		
	}
	private void toggle(String s) {
		if(s.equals("renscale")) {map.setREN_SCALE(!map.isREN_SCALE());}
		if(s.equals("renpointer")) {map.setREN_POINTER(!map.isREN_POINTER());}
	}

	private void spawn(String s, String s2, String s3) {
		try {
			if(s.equals("rock")) 		{map.getEntityManager().addEntity(new Rock		(handler, 	Integer.parseInt(s2), Integer.parseInt(s3)));return;}
			if(s.equals("tree")) 		{map.getEntityManager().addEntity(new Tree		(handler, 	Integer.parseInt(s2), Integer.parseInt(s3)));return;}
			if(s.equals("guard")) 		{map.getEntityManager().addEntity(new Creature	(handler, 	Integer.parseInt(s2), Integer.parseInt(s3), CreatureType.Guard));return;}
			if(s.equals("paramedic")) 	{map.getEntityManager().addEntity(new Creature	(handler, 	Integer.parseInt(s2), Integer.parseInt(s3), CreatureType.Paramedic));return;}
			if(s.equals("player")) 		{map.getEntityManager().addEntity(new Creature	(handler, 	Integer.parseInt(s2), Integer.parseInt(s3), CreatureType.Player));return;}
			if(s.equals("prisoner")) 	{map.getEntityManager().addEntity(new Creature	(handler,	Integer.parseInt(s2), Integer.parseInt(s3), CreatureType.Prisoner));return;}
		}catch(NumberFormatException nfe) {t.print("invalid location");}
		
		t.print("invalid command");
	}
	private void settile(String s) {
		int id = getId(s);
		if(id == -1)
			t.print("invalid tile");
		else {
			map.set_tile_mode(id);
			t.setOpen(false);
		}
	}
	/**
	 * This method gets the ID of a specific tile.
	 * @return
	 */
	public int getId(String name){
		for (int i = 0; i < Tile.tile_names.length; i++)
			if(name.equals(Tile.tile_names[i]))
				return i;
		return -1;
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
		
		t.render(g);
	}

}