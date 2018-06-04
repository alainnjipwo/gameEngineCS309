package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallR extends Tile{

	public CellarWallR(int id) {
		super(Assets.cellar_wall[5], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
