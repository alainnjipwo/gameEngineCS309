package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a CellarWallTRT
 * @author Kenneth Lange
 *
 */
public class CellarWallTRT extends Tile{

	public CellarWallTRT(int id) {
		super(Assets.cellar_wall[11], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
