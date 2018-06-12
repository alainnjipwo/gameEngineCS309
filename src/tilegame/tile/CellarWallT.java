package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a CellarWallT
 * @author Kenneth Lange
 *
 */
public class CellarWallT extends Tile{

	public CellarWallT(int id) {
		super(Assets.cellar_wall[7], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
