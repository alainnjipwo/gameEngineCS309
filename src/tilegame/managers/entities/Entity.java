package tilegame.managers.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import tilegame.Handler;
import tilegame.ai.Pathfinding;
import tilegame.gfx.Animation;
import tilegame.inventory.Inventory;
import tilegame.managers.locators.Locator;
import tilegame.tile.Tile;
import tilegame.utils.Vector2i;
import tilegame.worlds.Node;
/**
 * This class is a template for all entities
 * @author Kenneth Lange
 *
 */
public abstract class Entity {
	//DEBUGMODE
	public static boolean DEBUGMODE = true; //Shows DEBUGMODE for entities
	
	public static final int DEFAULT_HEALTH = 5;
	public static final double[] ATHLETCS = {128/64, 128/61, 128/57, 128/54, 128/51, 128/48, 128/45, 128/42, 128/38, 128/35, 128/32};

	public static final double DEFAULT_SPEED = ATHLETCS[0];
	public static final int DEFAULT_CREATURE_WIDTH = 64;
	public static final int DEFAULT_CREATURE_HEIGHT = 64;
	
	protected double speed;
	protected double xMove, yMove;
	
	//Attack
	protected long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown, at, lat; //Timers
	protected boolean attacking;
	//Coordinates 
	protected float xlocation, ylocation;
	//Animations
	protected Animation animUp, animDown, animLeft, animRight;
	protected int lastDirection = 2; //Set default start direction to be down
	//Inventory
	protected Inventory inventory;
	//Pathfinding
	protected List<Node> path = null;
	private Pathfinding pathfinder;
	protected boolean standing = true;
	
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
		
		pathfinder = new Pathfinding(handler);
		health = DEFAULT_HEALTH;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void render(Graphics g, double scale);
	
	public abstract void destroy();
	/**
	 * This method uses the methods moveX() and moveY() to move a creature around a screen.
	 * It also checks for collision with other entities.
	 */
	public void move(){
		if(!checkCollisions((float) xMove, 0f))
			moveX();
		if(!checkCollisions(0f, (float) yMove))
			moveY();
	}
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
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	/**
	 * This helper method allows for a creature to move in the X-Axis if not colliding with an obstacle.
	 */
	public void moveX(){
		if (xMove < 0){ //Moving Left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx,(int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x+= xMove;
			} else {
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}
		}else if (xMove > 0){ //Moving Right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			if(!collisionWithTile(tx,(int) (y + bounds.y) / Tile.TILEHEIGHT) &&
					!collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)){
				x+= xMove;
			} else {
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}
		}
	}
	
	/**
	 * This helper method allows for a creature to move in the Y-Axis if not colliding with an obstacle.
	 */
	public void moveY(){
		if (yMove < 0){ //Moving Up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
				!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}
		}else if (yMove > 0){ //Moving Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;
			if(!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty) &&
				!collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)){
				y += yMove;
			} else {
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}	
	/**
	 * This helper method checks if creature entity is colliding with a tile.
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean collisionWithTile(int x, int y){
		return handler.getWorld().getTile(x, y).isSolid();
	}	
	/**
	 * This method uses the Pathfinding class to navigate the creature a location.
	 * @param ylocation 
	 * @param xlocation 
	 */
	public void findPath(float xlocation, float ylocation, int x, int y) {
		xMove = 0;
		yMove = 0;
		Vector2i start = new Vector2i((int) (xlocation) / Tile.TILEWIDTH, (int) (ylocation) / Tile.TILEHEIGHT);
		Vector2i destination = new Vector2i(x, y);
				// Follow player : ((int) (handler.getWorld().getEntityManager().getPlayer().getXlocation() >> 6), (int)(handler.getWorld().getEntityManager().getPlayer().getYlocation() >> 6));
		if((xlocation / Tile.TILEWIDTH -.5) % 1 == 0 && (ylocation / Tile.TILEHEIGHT - .5) % 1 == 0) path = pathfinder.findPath(start, destination);
		else if (standing) { //For player because not all players will be centered on tile upon call
			path = pathfinder.findPath(start, destination);
			standing = false;
		}

		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (xlocation / Tile.TILEWIDTH < vec.getX() + .5) xMove = speed;
				else if (xlocation / Tile.TILEWIDTH > vec.getX() + .5) xMove = -speed;
				if (ylocation / Tile.TILEHEIGHT < vec.getY() + .5) yMove = speed;
				else if (ylocation / Tile.TILEHEIGHT > vec.getY() + .5) yMove = -speed;
			} else {
				standing = true;
			}
		}
	}
	/**
	 * This helper method sends an entity to a specified checkpoint
	 * @param xlocation
	 * @param ylocation
	 * @param checkpoint
	 */
	public void goToCheckpoint(float xlocation, float ylocation, Locator checkpoint) {
		findPath(xlocation, ylocation, (int) (checkpoint.getX()/Tile.TILEWIDTH), (int)(checkpoint.getY()/Tile.TILEHEIGHT));
	}
	/**
	 * This method is responsible for checking whether an entity is attacking or not and whether it hits something or not.
	 * It also has the ability to prevent attacking if a player has the inventory screen active.
	 */
	protected void checkAttacks() {
		//Cooldown timing code
		//----------------------------------------------------------
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		if(attackTimer < attackCooldown)
			return;
		//----------------------------------------------------------
		
		//Prevents attacking while in the inventory
		if(inventory.isActive())
			return;
		
		Rectangle cb = getCollisionBounds(0, 0);// Collision Bounds of Player
		Rectangle ar = new Rectangle(); // Attack Rectangle
		int arSize = 23; //Size of Attack Rectangle
		ar.width = arSize;
		ar.height = arSize;
		
		if(attacking) {
			if(lastDirection == 1) { //Attack Left
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}else if(lastDirection == 3) { //Attack Right
				ar.x = cb.x + arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}else if(lastDirection == 0) { //Attack Up
				ar.x = cb.x;
				ar.y = cb.y - arSize;
			}else if(lastDirection == 2) { //Attack Down
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			}
		} else return;
		
		attackTimer = 0;
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1); //Entity receives damage value of 1					
				return;
			}
		}
	}
	/**
	 * This method is responsible for rendering DEBUGMODE related rendering
	 * @param g
	 */
	public void DEBUGMODE_render(Graphics g) {
		//Draw path
		if (path != null) {
			if (path.size() > 0) {
				for (int i = 0; i<path.size()-1; i++) {
					g.drawLine((int) (path.get(i).tile.getX()*Tile.TILEWIDTH + Tile.TILEWIDTH/2 - handler.getGameCamera().getxOffset()),
							  (int) (path.get(i).tile.getY()*Tile.TILEHEIGHT + Tile.TILEHEIGHT/2 - handler.getGameCamera().getyOffset()),
							 (int) (path.get(i+1).tile.getX()*Tile.TILEWIDTH + Tile.TILEWIDTH/2 - handler.getGameCamera().getxOffset()),
							(int) (path.get(i+1).tile.getY()*Tile.TILEHEIGHT + Tile.TILEHEIGHT/2 - handler.getGameCamera().getyOffset()));
				}
				g.drawLine((int) (path.get(path.size()-1).tile.getX()*Tile.TILEWIDTH + Tile.TILEWIDTH/2 - handler.getGameCamera().getxOffset()),
						(int) (path.get(path.size()-1).tile.getY()*Tile.TILEWIDTH + Tile.TILEWIDTH/2 - handler.getGameCamera().getyOffset()),
						(int) (xlocation - handler.getGameCamera().getxOffset()), (int) (ylocation - handler.getGameCamera().getyOffset()));
			}
		}
		//NPC collision box
		g.setColor(Color.WHITE);
		g.drawRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()), (int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		
		//Attack box
		at += System.currentTimeMillis() - lat;
		lat = System.currentTimeMillis();
		if(at < attackCooldown)
			return;
		
		Rectangle cb = getCollisionBounds(0, 0);// Collision Bounds of Player
		Rectangle ar = new Rectangle(); // Attack Rectangle
		int arSize = 23; //Size of Attack Rectangle
		ar.width = arSize;
		ar.height = arSize;
		g.setColor(Color.GREEN);
		if(attacking) {
			if(lastDirection == 1) { //Attack Left
				ar.x = cb.x - arSize;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}else if(lastDirection == 3) { //Attack Right
				ar.x = cb.x + cb.width;
				ar.y = cb.y + cb.height / 2 - arSize / 2;
			}else if(lastDirection == 0) { //Attack Up
				ar.x = cb.x;
				ar.y = cb.y - arSize;
			}else if(lastDirection == 2) { //Attack Down
				ar.x = cb.x + cb.width / 2 - arSize / 2;
				ar.y = cb.y + cb.height;
			} else return;
			at = 0;
			for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
				if (e.equals(this))
					continue;
				if(e.getCollisionBounds(0, 0).intersects(ar))
					g.setColor(Color.RED);
			}
			g.fillRect((int) (ar.x - handler.getGameCamera().getxOffset()), (int) (ar.y - handler.getGameCamera().getyOffset()), arSize, arSize);
		}
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
	public double getxMove() {
		return xMove;
	}
	public void setxMove(float xMove) {
		this.xMove = xMove;
	}
	public double getyMove() {
		return yMove;
	}
	public void setyMove(float yMove) {
		this.yMove = yMove;
	}
	public int getHealth() {
		return health;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}