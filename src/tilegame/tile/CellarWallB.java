package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallB extends Tile{

	public CellarWallB(int id) {
		super(Assets.cellar_wall[6], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
