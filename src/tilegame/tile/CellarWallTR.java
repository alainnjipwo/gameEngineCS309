package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a CellarWallTR
 * @author Kenneth Lange
 *
 */
public class CellarWallTR extends Tile{

	public CellarWallTR(int id) {
		super(Assets.cellar_wall[3], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
