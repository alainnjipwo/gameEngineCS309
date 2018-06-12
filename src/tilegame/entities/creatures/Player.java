package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.debug.Debug;
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
	
	//Coordinates
	private int xmouse, ymouse;
	private int gox, goy;
	private boolean travelling;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		speed =  ATHLETCS[2];
			
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
		if (handler.getMouse().isButtonPressed(Mouse.LEFT_MOUSE)) {
			gox = xmouse; goy = ymouse;
			travelling = true;
		} else if (travelling) {
			findPath(xlocation, ylocation, gox, goy);
		} else if (xlocation / 64 == gox + .5 && ylocation / 64 == goy + .5) {
			travelling = false;
			xMove = 0;
			yMove = 0;
		}
		getPlayerInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		if (handler.getInput().isKeyDown(Input.KEY_F))
			attacking = true;
		else
			attacking = false;
		checkAttacks();
		
		//Inventory
		inventory.update();
		
		//DEBUGMODE
		if(handler.getInput().isKeyPressed(Input.KEY_F3))
			Debug.setDEBUGMODE();
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
			g.setColor(Color.WHITE);
			DEBUGMODE_render(g);
			//Player specific DEBUGMODE
			g.setColor(Color.WHITE);
			String c = "X: " + (int)(xlocation / 64) + " Y: " + (int)(ylocation / 64) + "   Actual: X: " + xlocation + " Y: " + ylocation;
			g.drawString(c, 5, 12);
			String mc = "X: " + xmouse + " Y: " + ymouse;
			g.drawString(mc, 5, 24);
			String entities = "Entities: " + (handler.getWorld().getEntityManager().getEntities().size() - 1);
			g.drawString(entities, 5, 36);
			String hp = "Health: " + health;
			g.drawString(hp, 5, 48);
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
	private void getPlayerInput(){
		if(!travelling) {
			xMove = 0;
			yMove = 0;
			if (path != null)
				path.clear();
		}
		
		//Prevents movement while in the inventory
		if(inventory.isActive())
			return;
		
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
