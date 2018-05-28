package tilegame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static cellar entity
 * @author Kenneth Lange
 *
 */
public class CellarWall extends StaticEntity{
	
	private int type;
	
	public CellarWall(Handler handler, float x, float y, int type) {
		super(handler, x * Tile.TILEWIDTH, y * Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT);
		this.type = type;
		/*NEEDS TO BE CHANGED*/
			bounds.x = 0;
			bounds.y = 0;
			bounds.width = Tile.TILEWIDTH;
			bounds.height = Tile.TILEHEIGHT;
		/*------------------*/
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
		
	}
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.cellar_wall[type], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (x + bounds.x -handler.getGameCamera().getxOffset()), (int) (y + bounds.y -handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
		}
		
	}
	@Override
	public BufferedImage getTexture() {
		return Assets.cellar_wall[type];
	}

}
