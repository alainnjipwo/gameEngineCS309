package tilegame.worlds;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.input.Input;
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
 * 
 * @author Mitchell Hoppe
 *
 */
public class Map extends World{
	
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
	
	private void init_ent() {
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);
		locatorManager = new LocatorManager(handler);
		
		//Locators
		locatorManager.addLocators(new GuardSpawner(handler, 5, 5));
		locatorManager.addLocators(new PrisonerSpawner(handler, 7, 5));
		locatorManager.addLocators(new Checkpoint(handler, 18, 9));
		
		//Static Objects
		entityManager.addNonmovingEntity(new Tree(handler, 3, 2));
		entityManager.addNonmovingEntity(new Tree(handler, 1, 8));
		entityManager.addNonmovingEntity(new Rock(handler, 9, 11));
		
		//Entities
		entityManager.addCreatureEntity(new Guard(handler, 5, 3));

		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
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

		Text.drawString(g, "scale> " + scale, 100, 30, true, Color.WHITE, Assets.font28);
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
			
			int x = (int) (handler.getMouse().getX() + handler.getGameCamera().getxOffset());
			int y = (int) (handler.getMouse().getY() + handler.getGameCamera().getyOffset());
			
			if(true) {
				System.out.println(x + " " + y);
			}
			
			int tile_x = (int)(x / (Tile.TILEWIDTH * scale));
			int tile_y = (int)(y / (Tile.TILEHEIGHT * scale));
						
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
		System.out.println(tile_mode);
		
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
	
}