package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.debug.Debug;
import tilegame.entities.Entity;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.input.Input;
import tilegame.input.Mouse;
import tilegame.inventory.Inventory;
import tilegame.tile.Tile;
/**
 * This class is responsible for the player character.
 * It implements an inventory screen and allows for a player to attack and move.
 * @author Kenneth Lange
 *
 */
public class Player extends Creature{
	
	//Animations
	private Animation animUp, animDown, animLeft, animRight;
	private int lastDirection = 2; //Set default start direction to be down
	
	//Attack Timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown, at, lat;
	
	//Inventory
	private Inventory inventory;
	
	//Coordinates	
	private float xlocation, ylocation;
	private int xmouse, ymouse;
	private int gox, goy;
	private boolean travelling;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		this.speed =  ATHLETCS[2];
			
		//Must be set to the exact pixel x and y beginning and the width and height of the character
		//ie set it to be around the body of character only
		bounds.x = 20;
		bounds.y = 46;
		bounds.width = 23;
		bounds.height = 16;
		
		//Animations
		animUp = new Animation(150, Assets.player_up);
		animDown = new Animation(150, Assets.player_down);
		animLeft = new Animation(150, Assets.player_left);
		animRight = new Animation(150, Assets.player_right);
		
		inventory = new Inventory(handler);
	}
	/**
	 * This is the player update method, it updates the position of where a player is on screen.
	 */
	@Override
	public void update() {
		//Coordinates 
		xmouse = (int) ((handler.getGameCamera().getxOffset() + handler.getMouse().getX()) / Tile.TILEWIDTH);
		ymouse = (int) ((handler.getGameCamera().getyOffset() + handler.getMouse().getY()) / Tile.TILEHEIGHT);
		xlocation = (x + bounds.x + bounds.width / 2);
		ylocation = (y + bounds.y + bounds.height / 2);
		
		//Animations
		animUp.update();
		animDown.update();
		animLeft.update();
		animRight.update();
		
		//Movement
//		if (!travelling && handler.getMouse().isButtonPressed(Mouse.LEFT_MOUSE)) {
//			gox = xmouse; goy = ymouse;
//			travelling = true;
//		} else if (travelling) {
//			findPath(xlocation, ylocation, gox, goy);
//		} else if (xlocation / 64 == gox + .5 && ylocation / 64 == goy + .5) {
//			travelling = false;
//			xMove = 0;
//			yMove = 0;
//		}
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		checkAttacks();
		
		//Inventory
		inventory.update();
		
		//DEBUGMODE
		if(handler.getInput().isKeyPressed(Input.KEY_F3))
			Debug.setDEBUGMODE();
	}
	/**
	 * This method is responsible for checking whether a player is attacking or not and whether it hits something or not.
	 * It also has the ability to prevent attacking player is in inventory screen.
	 */
	private void checkAttacks() {
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
		
		if(handler.getInput().isKeyDown(Input.KEY_F)) {
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
	 * This method is used for when a player is killed.
	 */
	public void destroy() {
		System.out.println("Player died!");
	}
	/**
	 * This method is responsible for rendering the newly updated position of the player character.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null); //render player
		
		//DEBUGMODE
		/*-------------------------------------------*/
		if(DEBUGMODE){
			
			//Player collision box
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
			
			//X and Y tile coordinates 
			String c = "X: " + (int)(xlocation / 64) + " Y: " + (int)(ylocation / 64) + "   Actual: X: " + xlocation + " Y: " + ylocation;
			g.drawString(c, 5, 12);
			String mc = "X: " + xmouse + " Y: " + ymouse;
			g.drawString(mc, 5, 24);
			String entities = "Entities: " + (handler.getWorld().getEntityManager().getEntities().size() - 1);
			g.drawString(entities, 5, 36);
			
			//attack box
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
			if(handler.getInput().isKeyDown(Input.KEY_F)) {
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
		/*-------------------------------------------*/
	}
	/**
	 * This method is used to render the player's inventory on top of everything else.
	 * @param g
	 */
	public void postRender(Graphics g) {
		inventory.render(g);
	}
	//Getters and Setters
	/**
	 * This method gets the current animation image which allows for a animated pplayer character.
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
		} else return Assets.player_notmoving[lastDirection];
	}
	/**
	 * This method gets the input that a player has queued and moves the player if the inventory is not active.
	 */
	private void getInput(){
			xMove = 0;
			yMove = 0;
		
		//Prevents movement while in the inventory
		if(inventory.isActive())
			return;
		
		if(handler.getInput().isKeyDown(Input.KEY_W))
			yMove = -speed;
		if(handler.getInput().isKeyDown(Input.KEY_S))
			yMove = speed;
		if(handler.getInput().isKeyDown(Input.KEY_A))
			xMove = -speed;
		if(handler.getInput().isKeyDown(Input.KEY_D))
			xMove = speed;
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
	public float getYlocation() {
		return ylocation;
	}
}
