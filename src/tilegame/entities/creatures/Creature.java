package tilegame.entities.creatures;

import java.util.List;

import tilegame.Handler;
import tilegame.ai.Pathfinding;
import tilegame.entities.Entity;
import tilegame.tile.Tile;
import tilegame.utils.Vector2i;
import tilegame.worlds.Node;
/**
 * This class is a template for all creatures and creature used methods.
 * @author Kenneth Lange
 *
 */
public abstract class Creature extends Entity{

	public static final double DEFAULT_SPEED = 1.0;
	public static final int DEFAULT_CREATURE_WIDTH = 64;
	public static final int DEFAULT_CREATURE_HEIGHT = 64;
	
	protected double speed;
	protected double xMove, yMove;
	//Player location
	private float PlayerX, PlayerY;
	
	private List<Node> path = null;
	protected boolean standing = true;
	private Pathfinding pathfinder;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		pathfinder = new Pathfinding(handler);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}
	/**
	 * This method uses the methods moveX() and moveY() to move a creature around a screen.
	 */
	public void move(){
		if(!checkEntityCollisions((float) xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, (float) yMove))
			moveY();
	}
	/**
	 * This method allow for a creature to move in the X-Axis.
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
	 * This method allow for a creature to move in the Y-Axis.
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
		Vector2i start = new Vector2i((int) (xlocation) >> 6, (int) (ylocation) >> 6);
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
	
	//GETTERS AND SETTERS

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
	public float getPlayerX() {
		return PlayerX;
	}
	public void setPlayerX(float playerX) {
		PlayerX = playerX;
	}
	public float getPlayerY() {
		return PlayerY;
	}
	public void setPlayerY(float playerY) {
		PlayerY = playerY;
	}
	
}
