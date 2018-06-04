package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallBLT extends Tile{

	public CellarWallBLT(int id) {
		super(Assets.cellar_wall[8], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
