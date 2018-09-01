package engine.managers.entities.nonmoving;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import engine.gfx.Assets;
import engine.tile.Tile;

public enum NonmovingType {
	
	CellarWall(1, 0, Assets.cellar_wall),
	Rock(2, 0, Assets.rock),
	Tree(3, 0, Assets.tree);
	
	int id, type;
	float xoffset, yoffset, width, height;
	BufferedImage texture;
	protected Rectangle bounds;
	
	//Single-type
	NonmovingType(int id, int type, BufferedImage texture){
		//Identifiers
		this.id = id;
		//Textures
		this.texture = texture;
		
		Setup(type);
	}
	//Multi-type
	NonmovingType(int id, int type, BufferedImage[] texture){
		//Identifiers
		this.id = id;
		//Textures
		this.texture = texture[type];
		
		Setup(type);
	}
	
	void Setup(int type){
		
		/*CellarWall Specific*/
		/*----------------------------------------------------------------------------------*/
		
		/* Types:
		0 = Bottom Left, 1 = Bottom Right, 2 = Top Left, 3 = Top Right,
		4 = Left, 5 = Right, 6 = Bottom, 7 = Top, 8 = Corner Bottom Left,
		9 = Corner Bottom Right, 10 = Corner Top Left, 11 = Corner Top Right
		*/
		
		if (id == 1){
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -.8f*Tile.TILEHEIGHT;
			this.width = Tile.TILEWIDTH;
			this.height = 1.8f*Tile.TILEHEIGHT;
			/*Bounds*/			
			if(type < 0 || type > 5)
				this.type = 0;
			else
				this.type = type;
			if(type < 3) {
				bounds.x = 1;
				bounds.y = (int) (Tile.TILEHEIGHT*1.1f);
				bounds.width = Tile.TILEWIDTH - 2;
				bounds.height = (int) (Tile.TILEHEIGHT*.7f);
			} else if (type == 3) {
				bounds.x = 1;
				bounds.y = (int) (Tile.TILEHEIGHT*.8f + 1);
				bounds.width = (int) (Tile.TILEWIDTH*.5f - 2);
				bounds.height = (int) (Tile.TILEHEIGHT - 2);
			} else if (type == 4) {
				bounds.x = (int) (Tile.TILEWIDTH*.5f + 1);
				bounds.y = (int) (Tile.TILEHEIGHT*.8f + 1);
				bounds.width = (int) (Tile.TILEWIDTH*.5f - 2);
				bounds.height = (int) (Tile.TILEHEIGHT - 2);
			}
		}
		/*----------------------------------------------------------------------------------*/
		
		/*Rock Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 2){
			/*Tile centering*/
			this.xoffset = 0.0f*Tile.TILEWIDTH;
			this.yoffset = -2f/64f*Tile.TILEHEIGHT;
			this.width = Tile.TILEWIDTH;
			this.height = Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int)((4f / 64f) * Tile.TILEWIDTH);
			bounds.y = (int)((15f / 64f) * Tile.TILEHEIGHT);
			bounds.width = (int)((56f / 64f) * Tile.TILEWIDTH) ;
			bounds.height = (int)((36f / 64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
		
		/*Tree Specific*/
		/*----------------------------------------------------------------------------------*/
		else if (id == 3){
			/*Tile centering*/
			this.xoffset = (-2 + 9f/64f) * Tile.TILEWIDTH;
			this.yoffset = (-3 - 32f/64f) * Tile.TILEHEIGHT;
			this.width = 5*Tile.TILEWIDTH;
			this.height = 5*Tile.TILEHEIGHT;
			/*Bounds*/
			bounds.x = (int) ((128f / 64f) * Tile.TILEWIDTH);
			bounds.y = (int) ((240f/64f) * Tile.TILEHEIGHT);
			bounds.width = (int)((48f /64f) * Tile.TILEWIDTH);
			bounds.height = (int) ((48f/64f) * Tile.TILEHEIGHT);
		}
		/*----------------------------------------------------------------------------------*/
	}

}
