package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallL extends Tile{

	public CellarWallL(int id) {
		super(Assets.cellar_wall[4], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
