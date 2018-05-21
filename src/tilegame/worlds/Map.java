package tilegame.worlds;

import java.awt.Graphics;

import tilegame.Handler;
import tilegame.entities.EntityManager;
import tilegame.entities.creatures.Guard;
import tilegame.entities.creatures.Player;
import tilegame.entities.statics.Rock;
import tilegame.entities.statics.Tree;
import tilegame.input.Input;
import tilegame.items.ItemManager;
import tilegame.tile.Tile;
import tilegame.utils.Utils;
/**
 * 
 * @author Mitchell Hoppe
 *
 */
public class Map extends World{
	
	private double scale = 1.0;
	private int camera_speed = 5;
	
	private int tile_mode = 0;
	

	public Map(Handler handler, String path){
		this.handler = handler;

		loadWorld(path);getClass();
	}
	
	@Override
	public void render(Graphics g){
		/*Render optimization*/
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);									// Renders only the
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);		// tiles that can
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);									// be seen by the
		int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);	// player's camera
		/*-------------------*/
		for(int y = yStart; y < (int)(yEnd / scale); y++){
			for(int x = xStart; x < (int)(xEnd / scale); x++){
				getTile(x, y).renderWithScale(g, (int) (x*(int)(Tile.TILEWIDTH * scale) - handler.getGameCamera().getxOffset()), (int) (y*(int)(Tile.TILEHEIGHT * scale) - handler.getGameCamera().getyOffset()), scale);
			}
		}
	}
	
	@Override
	public void update(){
		getInput();
	}

	@Override
	public Tile getTile(int x, int y){
		if( x< 0 || y < 0 || x >= width || y >= height){
			return Tile.grassTile; //If any tile is not set, it is defaulted to a grass tile.
		}
		
		Tile t = Tile.tiles[location[x][y]];
		if(t == null)
			return Tile.dirtTile; //If tile doesn't exist replace with dirt tile.
		return t;
	}
	
	private void getInput() {
		if(handler.getInput().isKeyDown(Input.KEY_W))
			handler.getGameCamera().move(0,-camera_speed);
		if(handler.getInput().isKeyDown(Input.KEY_S))
			handler.getGameCamera().move(0,camera_speed);
		if(handler.getInput().isKeyDown(Input.KEY_A))
			handler.getGameCamera().move(-camera_speed,0);
		if(handler.getInput().isKeyDown(Input.KEY_D))
			handler.getGameCamera().move(camera_speed,0);
		
		if(handler.getMouse().isButtonPressed(Input.LEFT_MOUSE)) {
			int x = handler.getMouse().getX();
			int y = handler.getMouse().getY();
			int mod = (int)(Tile.TILEWIDTH * scale);
			
			int tile_x = (int)(x / mod);
			int tile_y = (int)(y / mod);
						
			location[tile_x][tile_y] = tile_mode;
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

		
		if(handler.getInput().isKeyDown(Input.KEY_F12) && scale < 1.5)
			scale += 0.01;
		if(handler.getInput().isKeyDown(Input.KEY_F11) && scale > 0.5)
			scale -= 0.01;
	}
	
}