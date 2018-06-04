package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallBL extends Tile{

	public CellarWallBL(int id) {
		super(Assets.cellar_wall[0], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
