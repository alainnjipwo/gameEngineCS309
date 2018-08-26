package tilegame.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.input.Input;
import tilegame.managers.entities.creatures.Creature;
/**
 * This class is responsible for items  in the game.
 * @author Kenneth Lange
 * M: Game
 */
public class Item {

	//Handler
	public static Item[] items = new Item[256];
	public static Item stickItem = new Item(Assets.stick, "Stick", 0);
	public static Item rockItem = new Item(Assets.rock, "rock", 1);
	
	//Class
	public static final int ITEMWIDTH =32, ITEMHEIGHT = 32;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected Rectangle bounds;
	
	protected int x, y, count;
	protected boolean pickedUp = false;
	/**
	 * This constructor takes in a texture, a name, and an id of a new item and sets it's bounds and places it in an item array.
	 * @param texture
	 * @param name
	 * @param id
	 */
	public Item(BufferedImage texture, String name, int id) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		
		bounds = new Rectangle(x, y, ITEMWIDTH, ITEMHEIGHT);
		
		items[id] = this;
	}
	/**
	 * The method updates the items to be rendered in the world.
	 * It is also used for a player to pick up an item and removes it from the world.
	 */
	public void update() {
		//Checks if player is on top of the item and if they are pressing SPACE picks the item up.
		if (handler.getInput().isKeyDown(Input.KEY_SPACE)) {
			for(Creature e : handler.getWorld().getEntityManager().getCreatures()){
				if(e.getCollisionBounds(0f, 0f).intersects(bounds)) {
					pickedUp = true;
					e.getInventory().addItem(this);
					return;
				}
			}
		}
	}
	/**
	 * Item is being rendered in the world the ground.
	 * @param g
	 */
	public void render(Graphics g) {
		if(handler == null)
			return;
		render(g, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	/**
	 * Item is being rendered in the player's inventory screen.
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, ITEMWIDTH, ITEMHEIGHT, null);
	}

	/**
	 * This method creates a new item in the given location.
	 * @param x
	 * @param y
	 * @return
	 */
	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id);
		i.setPosition(x, y);
		return i;
	}
	/**
	 * This method sets the location of an image.
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
		bounds.x = x;
		bounds.y = y;
	}

	//Getters and setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}
	
}
