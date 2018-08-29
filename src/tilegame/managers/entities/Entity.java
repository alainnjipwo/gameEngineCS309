package tilegame.managers.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import tilegame.Handler;
/**
 * This class is a template for all entities
 * @author Kenneth Lange
 *
 */
public abstract class Entity {
	//DEBUGMODE
	public static boolean DEBUGMODE = true; //Shows DEBUGMODE for entities
	
	public static final int DEFAULT_HEALTH = 5;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected int health;
	protected boolean active = true;
	protected Rectangle bounds;
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void render(Graphics g, double scale);
	
	public abstract void destroy();
	/**
	 * This method receives the amount of damage done to an entity and removes the health accordingly
	 * @param amount
	 */
	public void hurt(int amount) {
		health -= amount;
		if(health <= 0) {
			health = 0; //I will be using this later when entity is knocked out
			active = false;
			destroy();
		}
	}
	/**
	 * Checks the bounds of an entities and sees if it collides with another entity
	 * @param xOffset
	 * @param yOffset
	 * @return
	 */
	public boolean checkCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getNonmoving()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	
	//Getters and Setters
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	
}