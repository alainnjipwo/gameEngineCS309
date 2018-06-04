package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class CellarWallBR extends Tile{

	public CellarWallBR(int id) {
		super(Assets.cellar_wall[1], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
