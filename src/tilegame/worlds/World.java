package tilegame.worlds;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.items.ItemManager;
import tilegame.managers.entities.EntityManager;
import tilegame.managers.entities.creatures.Guard;
import tilegame.managers.entities.creatures.Player;
import tilegame.managers.entities.nonmoving.Rock;
import tilegame.managers.entities.nonmoving.Tree;
import tilegame.managers.locators.LocatorManager;
import tilegame.managers.locators.other.Checkpoint;
import tilegame.managers.locators.spawners.GuardSpawner;
import tilegame.managers.locators.spawners.PrisonerSpawner;
import tilegame.tile.Tile;
import tilegame.utils.Utils;
/**
 * This class hold all the data needed to load in a world and render it to the game screen.
 * @author Kenneth Lange
 *
 */
public class World {

	protected Handler handler;
	protected int width, height; //Size of map
	protected int spawnX, spawnY;
	protected int[][] location;
	//Locators
	protected LocatorManager locatorManager;
	//Entities
	protected EntityManager entityManager;
	//Item
	protected ItemManager itemManager;
	
	protected World() {}

	/**
	 * This constructor sets the handler and loads in the world properly.
	 * @param handler
	 * @param path
	 */
	public World(Handler handler, String path){
		this.handler = handler;
		//Managers
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);
		locatorManager = new LocatorManager(handler);
		
		//Locators
		locatorManager.addLocators(new GuardSpawner(handler, 5, 5));
		locatorManager.addLocators(new PrisonerSpawner(handler, 7, 5));
		locatorManager.addLocators(new Checkpoint(handler, 6, 6));
		
		//Static entities
		entityManager.addEntity(new Tree(handler, 15, 20));		
		entityManager.addEntity(new Rock(handler, 17, 23));
		entityManager.addEntity(new Rock(handler, 10, 20));
		
		//Entities
		entityManager.addEntity(new Guard(handler, 3, 3));
//		entityManager.addEntity(new Paramedic(handler, 3, 4));
//		entityManager.addEntity(new Prisoner(handler, 3, 5));
		
		loadWorld(path);getClass();
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	/**
	 * This method updates all items and entities in the world.
	 */
	public void update(){
		locatorManager.update();
		itemManager.update();
		entityManager.update();
	}
	/**
	 * This method is responsible for rendering everything in the world.
	 * @param g
	 */
	public void render(Graphics g){
		/*Render optimization*/
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);									// Renders only the
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);		// tiles that can
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);									// be seen by the
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);	// player's camera
		/*-------------------*/
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				getTile(x, y).render(g, (int) (x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y*Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()), 1.0);
			}
		}
		//Locators
		locatorManager.render(g);
		//Items
		itemManager.render(g);
		//Entities
		entityManager.render(g);
	}
	/**
	 * This method loads in the world from a file path.
	 * @param path
	 */
	protected void loadWorld(String path){
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]) * Tile.TILEWIDTH + 1; //Sets the spawn to size of tiles.
		spawnY = Utils.parseInt(tokens[3]) * Tile.TILEHEIGHT - 22;//and centers the player on the tile
		
		location = new int[width][height];
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				location[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	//Getters and Setters
	public Tile getTile(int x, int y){
		if( x < 0 || y < 0 || x >= width || y >= height){
			return Tile.airTile; //If any tile is not set, it is defaulted to a air tile.
		}
		
		Tile t = Tile.tiles[location[x][y]];
		if(t == null)
			return Tile.dirtTile; //If tile doesn't exist replace with dirt tile.
		return t;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Handler getHandler() {
		return handler;
	}
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public LocatorManager getLocatorManager() {
		return locatorManager;
	}

	public void setLocatorManager(LocatorManager locatorManager) {
		this.locatorManager = locatorManager;
	}
}