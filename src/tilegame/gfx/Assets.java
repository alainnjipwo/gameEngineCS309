package tilegame.gfx;

import java.awt.Font;
import java.awt.image.BufferedImage;
/**
 * This class is responsible for loading in the resources for the game.
 * @author Kenneth Lange
 *
 */
public class Assets {
	
	//Widths and Heights in pixels
	private static final int tileWidth = 128, tileHeight = 128;
	private static final int playerWidth = 64, playerHeight = 64;
	private static final int guardWidth = 64, guardHeight = 64;
	private static final int buttonWidth = 256, buttonHeight = 128;
	private static final int itemWidth = 128, itemHeight = 128;
	
	//Fonts
	public static Font font28;	
	
	/*Textures*/
	//Tiles
	public static BufferedImage grass, dirt, sand, stonepath, shallowwater, deepwater;
	//Static Entities
	public static BufferedImage rock, tree;
	public static BufferedImage[] cellar_wall;
	//Items
	public static BufferedImage stick;
	//Character Models
	public static BufferedImage[] player_up, player_down, player_left, player_right, player_notmoving;
	public static BufferedImage[] guard_up, guard_down, guard_left, guard_right, guard_notmoving;
	//Buttons
	public static BufferedImage[] start_button, exit_button, map_builder_button, load_button, new_button, back_button;
	//Static Objects
	public static BufferedImage guardspawner, prisonerspawner, checkpoint;
	//Screens
	public static BufferedImage inventoryScreen;
	
	/**
	 * Loads in all resources once for the game
	 */
	public static void init(){
		font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);
		
		SpriteSheet playerImages = new SpriteSheet(ImageLoader.loadImage("/textures/Character.png"));
		SpriteSheet guardImages = new SpriteSheet(ImageLoader.loadImage("/textures/Guard.png"));
		SpriteSheet tileTextures = new SpriteSheet(ImageLoader.loadImage("/textures/Tiles.png"));
		SpriteSheet menuButtons = new SpriteSheet(ImageLoader.loadImage("/textures/MenuButtons.png"));
		SpriteSheet itemTextures = new SpriteSheet(ImageLoader.loadImage("/textures/Items.png"));
		SpriteSheet InventoryScreen = new SpriteSheet(ImageLoader.loadImage("/textures/InventoryScreen.png"));
		SpriteSheet Tree = new SpriteSheet(ImageLoader.loadImage("/textures/Tree.png"));
		SpriteSheet ObjectIcons = new SpriteSheet(ImageLoader.loadImage("/textures/ObjectIcons.png"));
		SpriteSheet CellarWall = new SpriteSheet(ImageLoader.loadImage("/textures/CellarWall.png"));
		SpriteSheet LargeObjects = new SpriteSheet(ImageLoader.loadImage("/textures/LargeObjects.png"));
		SpriteSheet SmallObjects = new SpriteSheet(ImageLoader.loadImage("/textures/SmallObjects.png"));
		
		//Menu Buttons
		start_button = new BufferedImage[2];
		exit_button = new BufferedImage[2];
		map_builder_button = new BufferedImage[2];
		map_builder_button = new BufferedImage[2];
		load_button = new BufferedImage[2];
		new_button = new BufferedImage[2];
		back_button = new BufferedImage[2];
		start_button[0] 		= menuButtons.crop(0, 0, buttonWidth, buttonHeight);
		start_button[1] 		= menuButtons.crop(0, 1, buttonWidth, buttonHeight);
		exit_button[0] 			= menuButtons.crop(0, 2, buttonWidth, buttonHeight);
		exit_button[1] 			= menuButtons.crop(0, 3, buttonWidth, buttonHeight);
		map_builder_button[0]	= menuButtons.crop(0, 4, buttonWidth, buttonHeight);
		map_builder_button[1] 	= menuButtons.crop(0, 5, buttonWidth, buttonHeight);
		load_button[0] 			= menuButtons.crop(0, 6, buttonWidth, buttonHeight);
		load_button[1] 			= menuButtons.crop(0, 7, buttonWidth, buttonHeight);
		new_button[0] 			= menuButtons.crop(0, 8, buttonWidth, buttonHeight);
		new_button[1] 			= menuButtons.crop(0, 9, buttonWidth, buttonHeight);
		back_button[0] 			= menuButtons.crop(0, 10, buttonWidth, buttonHeight);
		back_button[1] 			= menuButtons.crop(0, 11, buttonWidth, buttonHeight);
		
		//Tiles
		grass = tileTextures.crop(0, 0, tileWidth, tileHeight);
		dirt = tileTextures.crop(1, 0, tileWidth, tileHeight);
		sand = tileTextures.crop(2, 0, tileWidth, tileHeight);
		stonepath = tileTextures.crop(3, 0, tileWidth, tileHeight);
		shallowwater = tileTextures.crop(4, 0, tileWidth, tileHeight);
		deepwater = tileTextures.crop(5, 0, tileWidth, tileHeight);
		//Multiple types
		cellar_wall = new BufferedImage[12];
		for (int i = 0; i < 12; i++){
			cellar_wall[i] = CellarWall.crop(i, 0, tileWidth, tileHeight);
		}
		
		/*Characters*/
		//Player
		player_up = new BufferedImage[8];
		player_right = new BufferedImage[8];
		player_left = new BufferedImage[8];
		player_down = new BufferedImage[8];
		player_notmoving = new BufferedImage[4];
		for (int i = 0; i < 8; i++){
			player_up[i] = playerImages.crop(i + 1, 0, playerWidth, playerHeight);
		}
		for (int i = 0; i < 8; i++){
			player_right[i] = playerImages.crop(i + 1, 3, playerWidth, playerHeight);
		}
		for (int i = 0; i < 8; i++){
			player_left[i] = playerImages.crop(i + 1, 1, playerWidth, playerHeight);
		}
		for (int i = 0; i < 8; i++){
			player_down[i] = playerImages.crop(i + 1, 2, playerWidth, playerHeight);
		}
		for (int i = 0; i < 4; i++){
			player_notmoving[i] = playerImages.crop(0, i, playerWidth, playerHeight);
		}
		//Guard
		guard_up = new BufferedImage[8];
		guard_right = new BufferedImage[8];
		guard_left = new BufferedImage[8];
		guard_down = new BufferedImage[8];
		guard_notmoving = new BufferedImage[4];
		for (int i = 0; i < 8; i++){
			guard_up[i] = guardImages.crop(i + 1, 0, guardWidth, guardHeight);
		}
		for (int i = 0; i < 8; i++){
			guard_right[i] = guardImages.crop(i + 1, 3, guardWidth, guardHeight);
		}
		for (int i = 0; i < 8; i++){
			guard_left[i] = guardImages.crop(i + 1, 1, guardWidth, guardHeight);
		}
		for (int i = 0; i < 8; i++){
			guard_down[i] = guardImages.crop(i + 1, 2, guardWidth, guardHeight);
		}
		for (int i = 0; i < 4; i++){
			guard_notmoving[i] = guardImages.crop(0, i, guardWidth, guardHeight);
		}
		
		
		/*Static Entities*/
		//Solo types
		rock = itemTextures.crop(0, 1, itemWidth, itemHeight);
		tree = LargeObjects.crop(0, 0, 128, 128);
		
		//Inventory
		inventoryScreen = InventoryScreen.crop(0, 0, 512, 384);
		
		//Items
		stick = itemTextures.crop(0, 0, itemWidth, itemHeight);
		/*rock item uses static entity rock's picture*/
		
		//Objects (Spawners, Checkpoints, Etc)
		prisonerspawner = ObjectIcons.crop(0, 0, tileWidth, tileHeight);
		guardspawner = ObjectIcons.crop(1, 0, tileWidth, tileHeight);
		checkpoint = ObjectIcons.crop(2, 0, tileWidth, tileHeight);
		
	}
}