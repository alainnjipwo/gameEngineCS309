package tilegame.managers.locators;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import tilegame.Handler;
/**
 * This class manages all entities
 * @author Kenneth Lange
 * M: Engine
 */
public class LocatorManager {
	
	private Handler handler;
	private ArrayList<Locator> locators;

	/**
	 * This method creates an ArrayList that holds all the data of how many locators exist
	 * @param handler
	 */
	public LocatorManager(Handler handler) {
		this.handler = handler;
		locators = new ArrayList<Locator>();
	}
	/**
	 * This method updates the position of locators on a screen and sorts them from top to bottom. 
	 */
	public void update(){
		Iterator<Locator> it = locators.iterator();
		while(it.hasNext()) {
			Locator e = it.next();
			e.update();
			if(!e.isActive())
				it.remove();
		}
	}
	/**
	 * This method renders the updated positions of the locators.
	 * @param g
	 */
	public void render(Graphics g){
		for(Locator e : locators){
			e.render(g);
		}
	}
	
	public void render(Graphics g, double scale){
		for(Locator e : locators){
			e.render(g, scale);
		}
	}
	
	/**
	 * This method adds a locators to the locators ArrayList to be stored.
	 * @param e
	 */
	public void addLocators(Locator e){
		locators.add(e);
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Locator> getLocators() {
		return locators;
	}

	public void setLocators(ArrayList<Locator> locators) {
		this.locators = locators;
	}
	
}
