package tilegame.managers.entities.nonmoving;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.tile.Tile;
/**
 * This class holds the data for a static rock entity
 * @author Kenneth Lange
 *
 */
public class CellarWall extends Nonmoving{
	/* Types:
	0 = Bottom Left, 1 = Bottom Right, 2 = Top Left, 3 = Top Right,
	4 = Left, 5 = Right, 6 = Bottom, 7 = Top, 8 = Corner Bottom Left,
	9 = Corner Bottom Right, 10 = Corner Top Left, 11 = Corner Top Right
	*/
	private int type;
	
	public CellarWall(Handler handler, float x, float y, int type) {
		super(handler, x * Tile.TILEWIDTH, (y-2) * Tile.TILEHEIGHT - 2f/64f * Tile.TILEHEIGHT, Tile.TILEWIDTH, Tile.TILEHEIGHT*3);
		if(type < 0 || type > 5)
			this.type = 0;
		else
			this.type = type;
		
		if(type < 3) {
			bounds.x = 0;
			bounds.y = (int) (Tile.TILEHEIGHT*2.5);
			bounds.width = Tile.TILEWIDTH;
			bounds.height = (int) (Tile.TILEHEIGHT*.5);
		} else if (type == 3) {
			bounds.x = 0;
			bounds.y = (int) (Tile.TILEHEIGHT*2);
			bounds.width = (int) (Tile.TILEWIDTH*.5);
			bounds.height = (int) (Tile.TILEHEIGHT);
		} else if (type == 4) {
			bounds.x = (int) (Tile.TILEWIDTH*.5);
			bounds.y = (int) (Tile.TILEHEIGHT*2);
			bounds.width = (int) (Tile.TILEWIDTH*.5);
			bounds.height = (int) (Tile.TILEHEIGHT);
		}
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
		
	}
	/**
	 * This method is responsible for rendering an object's new location and collision box.
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.wall[type], (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
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
		
		g.drawImage(Assets.wall[type], x, y, width, height, null);
		
		if(DEBUGMODE){
			g.setColor(Color.WHITE);
			g.drawRect((int) (this.x*scale + bounds.x*scale -handler.getGameCamera().getxOffset()), (int) (this.y*scale + bounds.y*scale -handler.getGameCamera().getyOffset()), (int)(bounds.width*scale), (int)(bounds.height*scale));
			g.drawRect(x, y, width, height);
		}
	}
}
