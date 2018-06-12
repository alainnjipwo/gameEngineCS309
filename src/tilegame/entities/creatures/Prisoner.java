package tilegame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Animation;
import tilegame.gfx.Assets;
import tilegame.inventory.Inventory;
import tilegame.tile.Tile;
/**
 * This class is a guard NPC class. It is designed to render and display a guard creature that can be moved around the screen with a built in AI
 * @author Kenneth Lange
 *
 */
public class Prisoner extends Creature{

	/**
	 * This constructor passes along the handler and float location to the extended Creature parent class and sets the bounds of the collision box of the player.
	 * It is also responsible for setting the proper animation textures that will be later used in other classes to simulate animation.
	 * @param handler
	 * @param x
	 * @param y
	 */
	public Prisoner(Handler handler, float x, float y) {
		super(handler, (x-.5f) * Tile.TILEWIDTH + 33, (y -.5f) * Tile.TILEHEIGHT + 11, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		
		//Must be set to the exact pixel x and y beginning and the width and height of the character
		//ie set it to be around the body of character only
		bounds.x = 20;
		bounds.y = 45;
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
		findPath(xlocation, ylocation, 19, 28);
		move();
		//Attack
		checkAttacks();
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
			g.setColor(Color.ORANGE);
			DEBUGMODE_render(g);
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
	 * This method is called when the guard creature is killed (health = 0).
	 * Anything that is done in this class is the last thing that is done prior to removing the prisoner creature.
	 */
	@Override
	public void destroy() {
		
	}
}
