package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a CellarWallBRT
 * @author Kenneth Lange
 *
 */
public class CellarWallBRT extends Tile{

	public CellarWallBRT(int id) {
		super(Assets.cellar_wall[9], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
