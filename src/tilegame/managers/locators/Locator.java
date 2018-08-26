package tilegame.managers.locators;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tilegame.Handler;
/**
 * This class is a template for all locators
 * @author Kenneth Lange
 * M: Engine
 */
public abstract class Locator {
	//DEBUGMODE
	public static boolean DEBUGMODE = true; //Shows DEBUGMODE for locators
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected boolean active = true;
	protected Rectangle bounds;
	
	public Locator(Handler handler, float x, float y, int width, int height){
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
	
	public abstract BufferedImage getTexture();
	/**
	 * Checks the bounds of locators and sees if it collides with another locator
	 * @param xOffset
	 * @param yOffset
	 * @return	
	 */
	public boolean checkLocatorsCollisions(float xOffset, float yOffset){
		for(Locator e : handler.getWorld().getLocatorManager().getLocators()){
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
	
}