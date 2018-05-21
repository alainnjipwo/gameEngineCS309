package tilegame.staticobjects;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import tilegame.Handler;
/**
 * This class manages all entities
 * @author Kenneth Lange
 *
 */
public class StaticObjectManager {
	
	private Handler handler;
	private ArrayList<StaticObject> staticobjects;

	/**
	 * This method creates an ArrayList that holds all the data of how many static objects exist
	 * @param handler
	 */
	public StaticObjectManager(Handler handler) {
		this.handler = handler;
		staticobjects = new ArrayList<StaticObject>();
	}
	/**
	 * This method updates the position of static objects on a screen and sorts them from top to bottom. 
	 */
	public void update(){
		Iterator<StaticObject> it = staticobjects.iterator();
		while(it.hasNext()) {
			StaticObject e = it.next();
			e.update();
			if(!e.isActive())
				it.remove();
		}
	}
	/**
	 * This method renders the updated positions of the static objects.
	 * @param g
	 */
	public void render(Graphics g){
		for(StaticObject e : staticobjects){
			e.render(g);
		}
	}
	/**
	 * This method adds a static objects to the staticobjects ArrayList to be stored.
	 * @param e
	 */
	public void addStaticObject(StaticObject e){
		staticobjects.add(e);
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<StaticObject> getStaticObject() {
		return staticobjects;
	}

	public void setStaticObject(ArrayList<StaticObject> staticobjects) {
		this.staticobjects = staticobjects;
	}
	
}
