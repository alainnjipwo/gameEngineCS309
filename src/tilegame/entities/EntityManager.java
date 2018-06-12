package tilegame.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import tilegame.Handler;
import tilegame.entities.creatures.Player;
/**
 * This class manages all entities
 * @author Kenneth Lange
 *
 */
public class EntityManager {
	
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}

	};
	/**
	 * This method creates an ArrayList that holds all the data of how many entities exist
	 * @param handler
	 * @param player
	 */
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}
	/**
	 * This method updates the position of entities on a screen and sorts them from top to bottom. 
	 */
	public void update(){
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			e.update();
			if(!e.isActive())
				it.remove();
		}
		entities.sort(renderSorter);
	}
	/**
	 * This method renders the updated positions of the entities.
	 * @param g
	 */
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
//		player.postRender(g);
	}
	public void render(Graphics g, double scale){
		for(Entity e : entities){
			e.render(g, scale);
		}
	}
	/**
	 * This method adds an entity to the entities ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}
