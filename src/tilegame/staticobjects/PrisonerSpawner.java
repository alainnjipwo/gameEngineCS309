package tilegame.staticobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static prison spawner object
 * @author Kenneth Lange
 *
 */
public class PrisonerSpawner extends StaticObject{
	
	public PrisonerSpawner(Handler handler, float x, float y) {
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
			g.drawImage(Assets.prisonerspawner, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			g.setColor(Color.GREEN);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
	}
	@Override
	public BufferedImage getHiddenTexture() {
		return Assets.prisonerspawner;
	}
	
	@Override
	public void render(Graphics g, double scale) {
		render(g, 1.0);
	}

}
