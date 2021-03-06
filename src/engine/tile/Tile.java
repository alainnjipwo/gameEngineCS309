package engine.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This class is responsible for taking in all the different types of tiles and creating a template for any additional tiles that may be created.
 * @author Kenneth Lange
 *
 */
public class Tile {
	//DEBUG MODE
	public static boolean DEBUGMODE = true; //Change to true for tile grid
	
	//STATIC STUFF
	public static Tile[] tiles = new Tile[256]; //Holds one type of every single tile in the game (Increase as needed)
	public static Tile airTile = new AirTile(0);
	public static Tile shallowWaterTile = new ShallowWaterTile(1);
	public static Tile sandTile = new SandTile(2);
	public static Tile dirtTile = new DirtTile(3);
	public static Tile grassTile = new GrassTile(4);
	public static Tile rockTile = new RockTile(5);
	
	public static String[] tile_names = {
			"air",
			"deepwater",
			"shallowwater",
			"sand",
			"dirt",
			"grass",
			"rock",
			};
	
	
	//CLASS STUFF
	public static final int TILEWIDTH = 32, TILEHEIGHT = 32;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id){
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	/**
	 * This method is responsible for updating rendered tiles.
	 */
	public void update(){
		//Something that may be added here is animated tiles.
	}
	/**
	 * This method is responsible for rendering updated tiles.
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y){
		if (texture != null)
			g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
		if(DEBUGMODE) {
			Color oldcolor = g.getColor();
			g.setColor(new Color(0,0,0,127));
			g.drawRect(x, y, TILEWIDTH, TILEHEIGHT);
			g.setColor(oldcolor);
		}
	}
	
	/**
	 * This method is responsible for rendering updated tiles to the scale provided
	 * @param g
	 * @param x
	 * @param y
	 */
	public void render(Graphics g, int x, int y, double scale){
		g.drawImage(texture, x, y, (int)(TILEWIDTH * scale), (int)(TILEHEIGHT * scale), null);
		if(DEBUGMODE) {
			Color oldcolor = g.getColor();
			g.setColor(new Color(0,0,0,127));
			g.drawRect(x, y, (int)(TILEWIDTH * scale), (int)(TILEHEIGHT * scale));
			g.setColor(oldcolor);
		}
	}
	/**
	 * This method decides whether the tile is solid.
	 * It is by default set to false.
	 * @return
	 */
	public boolean isSolid(){
		return false;
	}
	/**
	 * This method gets the ID of a specific tile.
	 * @return
	 */
	public int getId(){
		return id;
	}
}
