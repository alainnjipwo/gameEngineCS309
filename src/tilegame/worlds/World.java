package tilegame.worlds;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.EntityManager;
import tilegame.entities.creatures.Guard;
import tilegame.entities.creatures.Player;
import tilegame.entities.statics.Rock;
import tilegame.entities.statics.Tree;
import tilegame.items.ItemManager;
import tilegame.staticobjects.Checkpoint;
import tilegame.staticobjects.GuardSpawner;
import tilegame.staticobjects.PrisonerSpawner;
import tilegame.staticobjects.StaticObjectManager;
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
	//Static Objects
	protected StaticObjectManager guardspawners;
	protected StaticObjectManager prisonerspawners;
	protected StaticObjectManager checkpoints;
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
		guardspawners = new StaticObjectManager(handler);
		prisonerspawners = new StaticObjectManager(handler);
		checkpoints = new StaticObjectManager(handler);
		
		//Static Objects
		guardspawners.addStaticObject(new GuardSpawner(handler, 5, 5));
		prisonerspawners.addStaticObject(new PrisonerSpawner(handler, 7, 5));
		checkpoints.addStaticObject(new Checkpoint(handler, 6, 6));
		
		//Entities
		entityManager.addEntity(new Tree(handler, 3, 2));		
		entityManager.addEntity(new Rock(handler, 9, 11));
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
		guardspawners.update();
		prisonerspawners.update();
		checkpoints.update();
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
				getTile(x, y).render(g, (int) (x*Tile.TILEWIDTH - handler.getGameCamera().getxOffset()), (int) (y*Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		//Static Objects
		guardspawners.render(g);
		prisonerspawners.render(g);
		checkpoints.render(g);
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

	public StaticObjectManager getGuardspawners() {
		return guardspawners;
	}

	public void setGuardspawners(StaticObjectManager guardspawners) {
		this.guardspawners = guardspawners;
	}

	public StaticObjectManager getPrisonerspawners() {
		return prisonerspawners;
	}

	public void setPrisonerspawners(StaticObjectManager prisonerspawners) {
		this.prisonerspawners = prisonerspawners;
	}

	public StaticObjectManager getCheckpoints() {
		return checkpoints;
	}

	public void setCheckpoints(StaticObjectManager checkpoints) {
		this.checkpoints = checkpoints;
	}
}