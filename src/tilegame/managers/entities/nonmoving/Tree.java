package tilegame.managers.entities.nonmoving;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.items.Item;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static tree entity
 * @author Kenneth Lange
 * M: Game
 */
public class Tree extends Nonmoving{
	
	public Tree(Handler handler, float x, float y) {
		super (handler, (x-2 + 9f/64f) * Tile.TILEWIDTH, (y-3 - 32f/64f) * Tile.TILEHEIGHT, (int) (Tile.TILEWIDTH*5), (int)(Tile.TILEHEIGHT*5));
		
		bounds.x = (int) ((128f / 64f) * Tile.TILEWIDTH);
		bounds.y = (int) ((240f/64f) * Tile.TILEHEIGHT);
		bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
		bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
	}
	/**
	 * This method is responsible for updating the object
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
	
	public void render(Graphics g, double scale) {
		
		int x = (int) ((this.x/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getxOffset());
		int y = (int) ((this.y/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getyOffset());
		int width = (int)(this.width*scale);
		int height = (int)(this.height*scale);
		
		g.drawImage(Assets.tree, x, y, width, height, null);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (this.x*scale + bounds.x*scale -handler.getGameCamera().getxOffset()), (int) (this.y*scale + bounds.y*scale -handler.getGameCamera().getyOffset()), (int)(bounds.width*scale), (int)(bounds.height*scale));
			g.drawRect(x, y, width, height);
		}
	}
}
