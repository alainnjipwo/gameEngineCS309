package tilegame.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.input.Input;
import tilegame.items.Item;
/**
 * This class is responsible for handling an entity's inventory.
 * @author Kenneth Lange
 *
 */
public class Inventory {

	private Handler handler;
	private boolean active = false;
	private ArrayList<Item> inventoryItems;
	
	private int invWidth = 512, invHeight = 384,
			invListSpacing = 30,
			invListCenterX = 550,
			invListCenterY = 245 + 4 * invListSpacing,
			
			invImageX = 772, invImageY = 200,
			invImageWidth = 64, invImageHeight = 64,
			
			invCountX = 805, invCountY = 292,
			//Index of currently selected item
			selectedItem = 0;
	/**
	 * This constructor takes in a handler and creates a new arraylist for held items within the inventory.
	 * @param handler
	 */
	public Inventory(Handler handler) {
		this.handler = handler;
		inventoryItems = new ArrayList<Item>();
	}
	/**
	 * This method updates the inventory and decides whether a user is accessing it or not.
	 */
	public void update() {
		if(handler.getInput().isKeyPressed(Input.KEY_E))
			active = !active;
		if(!active)
			return;
		
		if(handler.getInput().isKeyPressed(Input.KEY_W))
			selectedItem--;
		if(handler.getInput().isKeyPressed(Input.KEY_S))
			selectedItem++;
		
		if(selectedItem < 0)
			selectedItem = inventoryItems.size() - 1;
		else if(selectedItem >= inventoryItems.size())
			selectedItem = 0;
	}
	/**
	 * This method renders the updated inventory.
	 * @param g
	 */
	public void render(Graphics g) {
		if(!active)
			return;
		//Draws inventory to the middle of the screen
		g.drawImage(Assets.inventoryScreen, handler.getWidth()/2 - invWidth / 2, handler.getHeight()/2 - invHeight / 2, 512, 384, null);
		
		int length = inventoryItems.size();
		if (length == 0)
			return;
		
		for(int i = -5; i < 6; i ++) {
			if(selectedItem + i < 0 || selectedItem + i >= length)
				continue;
			if(i==0) {
			Text.drawString(g, "> " + inventoryItems.get(selectedItem + i).getName() + " <", invListCenterX,
					invListCenterY + i * invListSpacing, true, Color.YELLOW, Assets.font28);
			}else{
			Text.drawString(g, inventoryItems.get(selectedItem + i).getName(), invListCenterX,
					invListCenterY + i * invListSpacing, true, Color.WHITE, Assets.font28);
			}
		}
		
		Item item = inventoryItems.get(selectedItem);
		g.drawImage(item.getTexture(), invImageX, invImageY, invImageWidth, invImageHeight, null);
		Text.drawString(g, Integer.toString(item.getCount()), invCountX, invCountY, true, Color.WHITE, Assets.font28);
	}
	
	//Inventory Methods
	/**
	 * This method adds an item to the inventory.
	 * @param item
	 */
	public void addItem(Item item) {
		for(Item i: inventoryItems) {
			if(i.getId() == item.getId()) {
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		inventoryItems.add(item);
	}

	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
