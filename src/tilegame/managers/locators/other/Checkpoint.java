package tilegame.managers.locators.other;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.managers.locators.Locator;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static checkpoint object
 * @author Kenneth Lange
 *
 */
public class Checkpoint extends Locator{
	
	public Checkpoint(Handler handler, float x, float y) {
		super (handler, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = Tile.TILEWIDTH;
		bounds.height = Tile.TILEHEIGHT;
	}
	/**
	 * This method is responsible for updating the position of the object
	 */
	@Override
	public void update() {
		
	}
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 */
	@Override
	public void render(Graphics g) {
		if(DEBUGMODE){
			g.drawImage(Assets.checkpoint, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			g.setColor(Color.GREEN);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
	}
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 * This method is specific to the map editor
	 */
	@Override
	public void render(Graphics g, double scale) {
		
		int x = (int) ((this.x/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getxOffset());
		int y = (int) ((this.y/Tile.TILEWIDTH)*(int)(Tile.TILEWIDTH*scale)-handler.getGameCamera().getyOffset());
		
		if(DEBUGMODE) {
			g.drawImage(Assets.checkpoint, x, y, (int)(bounds.width * scale), (int)(bounds.width * scale), null);
			g.setColor(Color.GREEN);
			g.drawRect(x, y, (int)(bounds.height * scale), (int)(bounds.height * scale));
		}	
	}
	
	@Override
	public BufferedImage getTexture() {
		return Assets.checkpoint;
	}
}
