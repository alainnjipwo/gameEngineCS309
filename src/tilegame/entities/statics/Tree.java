package tilegame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.items.Item;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static tree entity
 * @author Kenneth Lange
 *
 */
public class Tree extends StaticEntity{
	
	public Tree(Handler handler, float x, float y) {
		super (handler, (x-1 + 19f/64f) * Tile.TILEWIDTH, (y - 1 - 32f/64f) * Tile.TILEHEIGHT, (int) (Tile.TILEWIDTH*2.5), (int)(Tile.TILEHEIGHT*2.5));
		
		bounds.x = (int) ((66f / 64f) * Tile.TILEWIDTH);
		bounds.y = (int) ((118f/64f) * Tile.TILEHEIGHT);
		bounds.width = (int)((20f /64f) * Tile.TILEWIDTH);
		bounds.height = (int) ((25f/64f) * Tile.TILEHEIGHT);
	}
	/**
	 * This method is responsible for updating the position of the object
	 */
	@Override
	public void update() {
		
	}
	/**
	 * This method is responsible for removing a destroyed object
	 */
	@Override
	public void destroy() {
		handler.getWorld().getItemManager().addItem(Item.stickItem.createNew((int) x, (int) y));
	}
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
	}
	@Override
	public BufferedImage getTexture() {
		return Assets.tree;
	}

}
