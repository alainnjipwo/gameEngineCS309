package tilegame.managers.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import tilegame.Handler;
import tilegame.managers.entities.creatures.Creature;
import tilegame.managers.entities.creatures.CreatureType;
import tilegame.managers.entities.nonmoving.Nonmoving;
/**
 * This class manages all entities
 * @author Kenneth Lange
 *
 */
public class EntityManager {
	
	private Handler handler;
	private ArrayList<Entity> entities;
	private ArrayList<Creature> creatures;
	private ArrayList<Nonmoving> nonmoving;
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
	public EntityManager(Handler handler) {
		this.handler = handler;
		entities = new ArrayList<Entity>();
		creatures = new ArrayList<Creature>();
		nonmoving = new ArrayList<Nonmoving>();
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
		
		Iterator<Nonmoving> itn = nonmoving.iterator();
		while(itn.hasNext()) {
			Entity e = itn.next();
			e.update();
			if(!e.isActive())
				itn.remove();
		}
	}
	/**
	 * This method renders the updated positions of the entities.
	 * @param g
	 */
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
		for(Creature e : creatures){
			if (e.getCreature() == CreatureType.Player)
				e.postRender(g);
		}
	}
	public void render(Graphics g, double scale){
		for(Entity e : entities){
			e.render(g, scale);
		}
	}
	/**
	 * This method adds an entity to the entities ArrayList and the non-moving ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Nonmoving e){
		entities.add(e);
		nonmoving.add(e);
	}
	/**
	 * This method adds an entity to the entities ArrayList and the creatures ArrayList to be stored.
	 * @param e
	 */
	public void addEntity(Creature e){
		entities.add(e);
		creatures.add(e);
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
	public ArrayList<Creature> getCreatures() {
		return creatures;
	}
	public void setCreatures(ArrayList<Creature> creatures) {
		this.creatures = creatures;
	}
	public ArrayList<Nonmoving> getNonmoving() {
		return nonmoving;
	}
	public void setNonmoving(ArrayList<Nonmoving> nonmoving) {
		this.nonmoving = nonmoving;
	}
}
