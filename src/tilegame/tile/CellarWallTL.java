package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallTL extends Tile{

	public CellarWallTL(int id) {
		super(Assets.cellar_wall[2], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
