package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.debug.Debug;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.input.Input;
import tilegame.tile.Tile;
/**
 * This class is a guard NPC class. It is designed to render and display a guard creature that can be moved around the screen with a built in AI
 * @author Kenneth Lange
 *
 */
public class Guard extends Creature{
	//Animations
	private Animation animUp, animDown, animLeft, animRight;
	private int lastDirection = 2; //Set default start direction to be down
	
	//Coordinates
	private float xlocation, ylocation;

	/**
	 * This constructor passes along the handler and float location to the extended Creature parent class and sets the bounds of the collision box of the player.
	 * It is also responsible for setting the proper animation textures that will be later used in other classes to simulate animation.
	 * @param handler
	 * @param x
	 * @param y
	 */
	public Guard(Handler handler, float x, float y) {
		super(handler, (x-.5f) * Tile.TILEWIDTH + 33, (y -.5f) * Tile.TILEHEIGHT + 11, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		this.speed = ATHLETCS[0];
		
		//Must be set to the exact pixel x and y beginning and the width and height of the character
		//ie set it to be around the body of character only
		bounds.x = 20;
		bounds.y = 45;
		bounds.width = 23;
		bounds.height = 16;
		
		//Animations
		animUp = new Animation(150, Assets.guard_up);
		animDown = new Animation(150, Assets.guard_down);
		animLeft = new Animation(150, Assets.guard_left);
		animRight = new Animation(150, Assets.guard_right);		
	}
	/**
	 * This method updates the position of the guard, the animation updates, and a DEBUGMODE setting.
	 */
	@Override
	public void update() {
		xlocation = (x + bounds.x + bounds.width / 2);
		ylocation = (y + bounds.y + bounds.height / 2);
		//Animations
		animUp.update();
		animDown.update();
		animLeft.update();
		animRight.update();
		//Movement
		findPath(xlocation, ylocation, 13, 16);
//		goToCheckpoint(xlocation, ylocation, handler.getWorld().getCheckpoints().getStaticObject().get(0));
		move();
		//DEBUGMODE
		if(handler.getInput().isKeyPressed(Input.KEY_F3))
			Debug.setDEBUGMODE();
	}

	/**
	 * This method renders that previously updated positions.
	 * It also has a built in function for DEBUGMODE to show debug details.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null); //render player
		//DEBUGMODE
		/*-------------------------------------------*/
		if(DEBUGMODE){	
			//NPC collision box
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
		/*-------------------------------------------*/
	}
	
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
		} else return Assets.guard_notmoving[lastDirection];
	}
	/**
	 * This method is called when the guard creature is killed (health = 0).
	 * Anything that is done in this class is the last thing that is done prior to removing the guard creature.
	 */
	@Override
	public void destroy() {
		
	}
}
