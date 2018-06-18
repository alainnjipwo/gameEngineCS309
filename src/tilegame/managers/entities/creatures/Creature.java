package tilegame.managers.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import tilegame.Handler;
import tilegame.ai.Pathfinding;
import tilegame.debug.Debug;
import tilegame.gfx.Animation;
import tilegame.input.Input;
import tilegame.input.Mouse;
import tilegame.inventory.Inventory;
import tilegame.managers.entities.Entity;
import tilegame.managers.locators.Locator;
import tilegame.tile.Tile;
import tilegame.utils.Vector2i;
import tilegame.worlds.Node;
/**
 * This class is a template for all creatures and creature used methods.
 * @author Kenneth Lange
 *
 */
public class Creature extends Entity{

	public static final double[] ATHLETCS = {2, 128/61, 128/57, 128/54, 128/51, 128/48, 128/45, 128/42, 128/38, 128/35, 128/32};

	public static final double DEFAULT_SPEED = ATHLETCS[0];
	public static final int DEFAULT_CREATURE_WIDTH = 64;
	public static final int DEFAULT_CREATURE_HEIGHT = 64;
	
	protected double speed;
	protected double xMove, yMove;
	
	//CreatureType
	CreatureType creature;
	//Attack
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown, at, lat; //Timers
	private boolean attacking;
	//Coordinates 
	private float xlocation, ylocation;
	//Animations
	private Animation animUp, animDown, animLeft, animRight;
	private int lastDirection = 2; //Set default start direction to be down
	//Inventory
	private Inventory inventory;
	//Pathfinding
	private List<Node> path = null;
	private Pathfinding pathfinder;
	private boolean travelling;
	private long lastPathCheck, pathCheckCooldown= 800, pathCheck= pathCheckCooldown;
	
	/*Things to add to the Controls Class*/
	private int xmouse, ymouse; //Controls
	private int gox, goy; //Controls
	
	public Creature(Handler handler, float x, float y, CreatureType creature) {
		super(handler, (x-1.5f) * Tile.TILEWIDTH + 33, (y -1.5f) * Tile.TILEHEIGHT + 11, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		pathfinder = new Pathfinding(handler);
		speed = DEFAULT_SPEED;
		health = DEFAULT_HEALTH;
		xMove = 0;
		yMove = 0;
		
		//Must be set to the exact pixel x and y beginning and the width and height of the character
		//ie set it to be around the body of character only
		bounds.x = creature.bounds.x;
		bounds.y = creature.bounds.y;
		bounds.width = creature.bounds.width;
		bounds.height = creature.bounds.height;
		
		//Animations
		animUp = creature.animUp;
		animDown = creature.animDown;
		animLeft = creature.animLeft;
		animRight = creature.animRight;
		
		inventory = new Inventory(handler);
		this.creature = creature;
	}
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/ //TODO Work on this stuff
	/**
	 * This is the update method, it updates everything in regards to each creature.
	 */
	@Override
	public void update() {
		//Coordinates -- //Controls TODO move to Controls() class
		xmouse = (int) ((handler.getGameCamera().getxOffset() + handler.getMouse().getX()) / Tile.TILEWIDTH);
		ymouse = (int) ((handler.getGameCamera().getyOffset() + handler.getMouse().getY()) / Tile.TILEHEIGHT);
		
		//Coordinates 
		xlocation = (x + bounds.x + bounds.width / 2);
		ylocation = (y + bounds.y + bounds.height / 2);
		
		//Animations
		animUp.update();
		animDown.update();
		animLeft.update();
		animRight.update();
		
		//Movement
		if (creature.player_controlled)
			getPlayerInput();
		else
			getAIInput();
		move();
		if (creature == CreatureType.Player)
			handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		if (handler.getInput().isKeyDown(Input.KEY_F)) //Controls
			attacking = true;
		else
			attacking = false;
		checkAttacks();
		
		//Inventory
		inventory.update();
		
		//DEBUGMODE
		if(handler.getInput().isKeyPressed(Input.KEY_F3)) //Controls
			Debug.setDEBUGMODE();
	}

	/**
	 * This method is used for when a player is killed.
	 */
	public void destroy() {
		System.out.println("Player died!");
	}
	/**
	 * This method is responsible for rendering the newly updated creature.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null); //render player
		
		//DEBUGMODE
		/*-------------------------------------------*/
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			DEBUGMODE_render(g);
			//player specific DEBUGMODE
			if (creature == CreatureType.Player) {
				g.setColor(Color.WHITE);
				String c = "X: " + (int)(xlocation / Tile.TILEWIDTH) + " Y: " + (int)(ylocation / Tile.TILEHEIGHT) + "   Actual: X: " + xlocation + " Y: " + ylocation;
				g.drawString(c, 5, 12);
				String mc = "X: " + xmouse + " Y: " + ymouse;
				g.drawString(mc, 5, 24);
				String entities = "Entities: " + (handler.getWorld().getEntityManager().getEntities().size() - 1);
				g.drawString(entities, 5, 36);
				String hp = "Health: " + health;
				g.drawString(hp, 5, 48);
			}
		}
		/*-------------------------------------------*/
	}
	public void render(Graphics g, double scale) {
		int x = (int) (this.x*scale - handler.getGameCamera().getxOffset());
		int y = (int) (this.y*scale - handler.getGameCamera().getyOffset());
		int width = (int)(this.width*scale);
		int height = (int)(this.height*scale);
		
		g.drawImage(getCurentAnimationFrame(), x, y, width, height, null);
		if(DEBUGMODE){
			//Player collision box
			g.setColor(Color.WHITE);
			g.drawRect((int) (this.x*scale + bounds.x*scale -handler.getGameCamera().getxOffset()), (int) (this.y*scale + bounds.y*scale -handler.getGameCamera().getyOffset()), (int)(bounds.width*scale), (int)(bounds.height*scale));
			g.drawRect(x, y, width, height);
		}
	}
	/**
	 * This method is used to render the creature's inventory on top of everything else.
	 * @param g
	 */
	public void postRender(Graphics g) {
		inventory.render(g);
	}
	//Getters and Setters
	/**
	 * This method gets the current animation image which allows for a animated creature character.
	 * @return
	 */
	private BufferedImage getCurentAnimationFrame(){
		if(xMove < 0){ //Moving Left
			lastDirection = 1;
			return animLeft.getCurrentFrame();
		}else if(xMove > 0){ //Moving Right
			lastDirection = 3;
			return animRight.getCurrentFrame();
		}else if(yMove < 0){ //Moving Up
			lastDirection = 0;
			return animUp.getCurrentFrame();
		}else if(yMove > 0){ //Moving Down
			lastDirection = 2;
			return animDown.getCurrentFrame();
		} else return creature.notMoving[lastDirection];
	}
	/**
	 * This method gets the input that a creature has queued (both artificially and human input based on whether the creature is being controlled by human input or not) and moves the creature if the inventory is not active.
	 */
	private void getPlayerInput(){ //TODO change all of this so that controls are separated from this class
		//Mouse
		if (handler.getMouse().isButtonPressed(Mouse.LEFT_MOUSE)) {
			gox = xmouse; goy = ymouse;
			travelling = true;
		} else if (travelling) {
			findPath(xlocation, ylocation, gox, goy);
			if(path == null) {
				travelling = false;
				xMove = 0;
				yMove = 0;
			}
		} else if (xlocation / Tile.TILEWIDTH == gox + .5 && ylocation / Tile.TILEHEIGHT == goy + .5) {
			travelling = false;
			xMove = 0;
			yMove = 0;
		}
		
		if(!travelling) {
			xMove = 0;
			yMove = 0;
			if (path != null)
				path.clear();
		}
		//Prevents movement while in the inventory
		if(inventory.isActive())
			return;
		//Keyboard
		if(handler.getInput().isKeyDown(Input.KEY_W)) {
			yMove = -speed;
			travelling = false;
		}else if(handler.getInput().isKeyDown(Input.KEY_S)) {
			yMove = speed;
			travelling = false;
		}if(handler.getInput().isKeyDown(Input.KEY_A)) {
			xMove = -speed;
			travelling = false;
		}else if(handler.getInput().isKeyDown(Input.KEY_D)) {
			xMove = speed;
			travelling = false;
		}
	}
	/**
	 * This method gets an artificial input that a creature has queued and moves the creature if the inventory is not active.
	 */
	private void getAIInput() {
		findPath(xlocation, ylocation, 19, 28);
	}
	/*-----------------------------------------------------------------------------------------------------------------------------------------------------*/
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
		//Path check timer
		/*-------------------------------------------------------------*/
		pathCheck += System.currentTimeMillis() - lastPathCheck;
		lastPathCheck = System.currentTimeMillis();
		if(pathCheck < pathCheckCooldown)
			return;
		/*-------------------------------------------------------------*/
		xMove = 0;
		yMove = 0;
		
		Vector2i start = new Vector2i((int) (xlocation / Tile.TILEWIDTH), (int) (ylocation / Tile.TILEHEIGHT));
		Vector2i destination = new Vector2i(x, y);
				// Follow player : ((int) (handler.getWorld().getEntityManager().getPlayer().getXlocation() >> 6), (int)(handler.getWorld().getEntityManager().getPlayer().getYlocation() >> 6));
		path = pathfinder.findPath(start, destination);
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (xlocation / Tile.TILEWIDTH < vec.getX() + .5) xMove = speed;		//Right
				else if (xlocation / Tile.TILEWIDTH > vec.getX() + .5) xMove = -speed;	//Left		/
				if (ylocation / Tile.TILEHEIGHT < vec.getY() + .5) yMove = speed;		//Down
				else if (ylocation / Tile.TILEHEIGHT > vec.getY() + .5) yMove = -speed;	//Up		/
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

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public Inventory getInventory() {
		return inventory;
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public float getXlocation() {
		return xlocation;
	}
	public void setXlocation(float xlocation) {
		this.xlocation = xlocation;
	}
	public float getYlocation() {
		return ylocation;
	}
	public void setYlocation(float ylocation) {
		this.ylocation = ylocation;
	}
	public CreatureType getCreature() {
		return creature;
	}
	public void setCreature(CreatureType creature) {
		this.creature = creature;
	}
}