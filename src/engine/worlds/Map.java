package engine.worlds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import engine.Handler;
import engine.gfx.Assets;
import engine.gfx.Text;
import engine.input.Input;
import engine.items.ItemManager;
import engine.managers.entities.EntityManager;
import engine.managers.entities.nonmoving.Rock;
import engine.managers.entities.nonmoving.Tree;
import engine.managers.locators.LocatorManager;
import engine.managers.locators.other.Checkpoint;
import engine.managers.locators.spawners.GuardSpawner;
import engine.managers.locators.spawners.PrisonerSpawner;
import engine.tile.Tile;
import engine.utils.Utils;
/**
 * 
 * @author Mitchell Hoppe
 *
 */
public class Map extends World{
	
	private  boolean REN_POINTER 		= true;
	private  String REN_POINTER_FONT 	= "Courier New";
	private  int REN_POINTER_FONT_SIZE 	= 28;
	private  int REN_POINTER_X 			= 20;
	private  int REN_POINTER_Y 			= 60;
	
	
	private boolean REN_SCALE 		= true;
	private String REN_SCALE_FONT 	= "Courier New";
	private int REN_SCALE_FONT_SIZE = 28;
	private int REN_SCALE_X 		= 20;
	private int REN_SCALE_Y 		= 30;
	
	private String path;

	private double scale = 1.00;
	private int camera_speed = 5;
	private int tile_mode = 5;

	public Map(Handler handler, String path){

		this.handler = handler;
		this.path = path;
		loadWorld(path);
		getClass();
		init_ent();

	}
	
	public int get_tile_mode() {
		return tile_mode;
	}

	public void set_tile_mode(int tile_mode) {
		if(!(tile_mode > 18 && tile_mode < 0))
			this.tile_mode = tile_mode;
	}

	private void init_ent() {
		entityManager = new EntityManager(handler);
		itemManager = new ItemManager(handler);
		locatorManager = new LocatorManager(handler);
		
		//Locators
		locatorManager.addLocators(new GuardSpawner(handler, 5, 5));
		locatorManager.addLocators(new PrisonerSpawner(handler, 7, 5));
		locatorManager.addLocators(new Checkpoint(handler, 18, 9));
		
		//Static Objects
		entityManager.addEntity(new Tree(handler, 3, 2));
		entityManager.addEntity(new Tree(handler, 1, 8));
		entityManager.addEntity(new Rock(handler, 9, 11));
		
		//Entities
//		entityManager.addCreatureEntity(new Guard(handler, 5, 3));

//		entityManager.getPlayer().setX(spawnX);
//		entityManager.getPlayer().setY(spawnY);
	}

	@Override
	public void render(Graphics g){
		/*Render optimization*/
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH / scale);											// Renders only the
		int xEnd = (int) Math.min(width * scale, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH * (scale < 1 ? 1 / scale : scale) + 1);		// tiles that can
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT / scale);										// be seen by the
		int yEnd = (int) Math.min(height * scale, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT * (scale < 1 ? 1 / scale : scale) + 1);	// player's camera
		/*-------------------*/
		for(int y = yStart; y < (int)(yEnd / scale); y++){
			for(int x = xStart; x < (int)(xEnd / scale); x++){
				getTile(x, y).render(g, (int) (x*(int)(Tile.TILEWIDTH * scale) - handler.getGameCamera().getxOffset()), (int) (y*(int)(Tile.TILEHEIGHT * scale) - handler.getGameCamera().getyOffset()), scale);
			}
		}

		locatorManager.render(g, scale);
		itemManager.render(g);
		entityManager.render(g, scale);
		
		int tile_x = (int) getPointerTile().getX();
		int tile_y = (int) getPointerTile().getY();
		
		g.setColor(Color.WHITE);

		if(REN_SCALE) {
			g.setFont(new Font(REN_SCALE_FONT, Font.BOLD, REN_SCALE_FONT_SIZE));
			g.drawString("scale> " + scale, REN_SCALE_X, REN_SCALE_Y);
		}
		if(REN_POINTER) {
			g.setFont(new Font(REN_POINTER_FONT, Font.BOLD, REN_POINTER_FONT_SIZE));
			g.drawString("pointer> "+tile_x+" , "+tile_y, REN_POINTER_X, REN_POINTER_Y);
		}
	}
	
	@Override
	public void update(){
		getInput();
	}
	
	private void getInput() {
		if(handler.getInput().isKeyDown(Input.KEY_W))
			handler.getGameCamera().move(0,-camera_speed, scale);
		if(handler.getInput().isKeyDown(Input.KEY_S) && !handler.getInput().isKeyDown(Input.KEY_CONTROL))
			handler.getGameCamera().move(0,camera_speed, scale);
		if(handler.getInput().isKeyDown(Input.KEY_A))
			handler.getGameCamera().move(-camera_speed,0, scale);
		if(handler.getInput().isKeyDown(Input.KEY_D))
			handler.getGameCamera().move(camera_speed,0, scale);
		
		if(handler.getMouse().isButtonDown(Input.LEFT_MOUSE)) {		
			int tile_x = (int) getPointerTile().getX();
			int tile_y = (int) getPointerTile().getY();
			if(		tile_x >= 0 && tile_x < location.length && 
					tile_y >= 0 && tile_y < location[0].length)
				location[tile_x][tile_y] = tile_mode;
		}
		if(handler.getInput().isKeyDown(Input.KEY_F1)) {
			scale = 0.97;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		if(handler.getInput().isKeyDown(Input.KEY_F2)) {
			scale = 1.00;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		if(handler.getInput().isKeyDown(Input.KEY_F3)) {
			scale = 1.03;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		if(handler.getInput().isKeyDown(Input.KEY_F4)) {
			scale = 1.12;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		
		//Increment through arraylist
		if(handler.getInput().isKeyPressed(Input.KEY_PAGE_UP)) {
			tile_mode++;
			if(tile_mode > 5)
				tile_mode = 0;
		}
		if(handler.getInput().isKeyPressed(Input.KEY_PAGE_DOWN)) {
			tile_mode--;
			if(tile_mode < 0)
				tile_mode = 18;
		}
		//Save
		if(handler.getInput().isKeyDown(Input.KEY_CONTROL) && handler.getInput().isKeyDown(Input.KEY_S))
			Utils.saveWorld(path, super.location, super.spawnX, super.spawnY);

		//Scale
		if(handler.getInput().isKeyDown(Input.KEY_F12) && scale < 1.25) {
			scale += 0.01;
			scale = Math.round(scale * 100.0) / 100.0;
		}
		if(handler.getInput().isKeyDown(Input.KEY_F11) && scale > 0.75) {
			scale -= 0.01;
			scale = Math.round(scale * 100.0) / 100.0;
		}
	}
	
	private Point getPointerTile() {
		int x = (int) (handler.getMouse().getX() + handler.getGameCamera().getxOffset());
		int y = (int) (handler.getMouse().getY() + handler.getGameCamera().getyOffset());			
		int tile_x = (int)(x / (Tile.TILEWIDTH * scale));
		int tile_y = (int)(y / (Tile.TILEHEIGHT * scale));
		
		return new Point((int)tile_x, (int)tile_y);
	}
	
	public boolean 	isREN_SCALE() 										{return REN_SCALE;}
	public boolean 	isREN_POINTER() 									{return REN_POINTER;}
	public String 	getREN_POINTER_FONT() 								{return REN_POINTER_FONT;}
	public String 	getREN_SCALE_FONT() 								{return REN_SCALE_FONT;}
	public int 		getREN_POINTER_FONT_SIZE() 							{return REN_POINTER_FONT_SIZE;}
	public int 		getREN_SCALE_FONT_SIZE() 							{return REN_SCALE_FONT_SIZE;}
	public int 		getREN_POINTER_X() 									{return REN_POINTER_X;}
	public int 		getREN_POINTER_Y() 									{return REN_POINTER_Y;}
	public int 		getREN_SCALE_X() 									{return REN_SCALE_X;}
	public int 		getREN_SCALE_Y() 									{return REN_SCALE_Y;}
	public void 	setREN_SCALE			(boolean rEN_SCALE)	 		{REN_SCALE = rEN_SCALE;}
	public void 	setREN_POINTER			(boolean rEN_POINTER) 		{REN_POINTER = rEN_POINTER;}
	public void 	setREN_POINTER_FONT		(String rEN_POINTER_FONT) 	{REN_POINTER_FONT = rEN_POINTER_FONT;}
	public void 	setREN_SCALE_FONT		(String rEN_SCALE_FONT) 	{REN_SCALE_FONT = rEN_SCALE_FONT;}
	public void 	setREN_POINTER_FONT_SIZE(int rEN_POINTER_FONT_SIZE) {REN_POINTER_FONT_SIZE = rEN_POINTER_FONT_SIZE;}
	public void 	setREN_SCALE_FONT_SIZE	(int rEN_SCALE_FONT_SIZE) 	{REN_SCALE_FONT_SIZE = rEN_SCALE_FONT_SIZE;}
	public void 	setREN_POINTER_X		(int rEN_POINTER_X) 		{REN_POINTER_X = rEN_POINTER_X;}
	public void 	setREN_POINTER_Y		(int rEN_POINTER_Y) 		{REN_POINTER_Y = rEN_POINTER_Y;}
	public void 	setREN_SCALE_X			(int rEN_SCALE_X) 			{REN_SCALE_X = rEN_SCALE_X;}
	public void 	setREN_SCALE_Y			(int rEN_SCALE_Y) 			{REN_SCALE_Y = rEN_SCALE_Y;}
	
}