package tilegame.worlds;

import java.awt.Color;
import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.EntityManager;
import tilegame.entities.creatures.Guard;
import tilegame.entities.creatures.Player;
import tilegame.entities.statics.Rock;
import tilegame.entities.statics.Tree;
import tilegame.gfx.Assets;
import tilegame.gfx.Text;
import tilegame.input.Input;
import tilegame.items.ItemManager;
import tilegame.staticobjects.Checkpoint;
import tilegame.staticobjects.GuardSpawner;
import tilegame.staticobjects.PrisonerSpawner;
import tilegame.staticobjects.StaticObjectManager;
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
	private int tile_mode = 0;
	

	public Map(Handler handler, String path){
//		super(handler, path);
		this.handler = handler;
		this.path = path;
		loadWorld(path);getClass();
//		
		init_ent();

	}
	
	private void init_ent() {
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));
		itemManager = new ItemManager(handler);
		staticObjectManager = new StaticObjectManager(handler);
		
		
		staticObjectManager.addStaticObject(new GuardSpawner(handler, 5, 5));
		staticObjectManager.addStaticObject(new PrisonerSpawner(handler, 7, 5));
		staticObjectManager.addStaticObject(new Checkpoint(handler, 18, 9));
		
		//Entities
		entityManager.addEntity(new Tree(handler, 3, 2));
		entityManager.addEntity(new Tree(handler, 1, 8));
		entityManager.addEntity(new Rock(handler, 9, 11));
		entityManager.addEntity(new Guard(handler, 5, 3));

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

		staticObjectManager.render(g, scale);
		itemManager.render(g);
		entityManager.render(g, scale);
		
		Text.drawString(g, "scale> " + scale, 200, 30, true, Color.WHITE, Assets.font28);
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
		
		if(handler.getMouse().isButtonPressed(Input.LEFT_MOUSE)) {
			
			int x = (int) (handler.getMouse().getX() + handler.getGameCamera().getxOffset());
			int y = (int) (handler.getMouse().getY() + handler.getGameCamera().getyOffset());
			
			if(true) {
				System.out.println(x + " " + y);
			}
			
			int mod = (int)(Tile.TILEWIDTH * scale);
			
			int tile_x = (int)(x / mod);
			int tile_y = (int)(y / mod);
						
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
		
		
		if(handler.getInput().isKeyDown(Input.KEY_0))
			tile_mode = 0;
		if(handler.getInput().isKeyDown(Input.KEY_1))
			tile_mode = 1;
		if(handler.getInput().isKeyDown(Input.KEY_2))
			tile_mode = 2;
		if(handler.getInput().isKeyDown(Input.KEY_3))
			tile_mode = 3;
		if(handler.getInput().isKeyDown(Input.KEY_4))
			tile_mode = 4;
		if(handler.getInput().isKeyDown(Input.KEY_5))
			tile_mode = 5;
		
		if(handler.getInput().isKeyDown(Input.KEY_CONTROL) && handler.getInput().isKeyDown(Input.KEY_S))
			Utils.saveWorld(path, super.location, super.spawnX, super.spawnY);

		
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
