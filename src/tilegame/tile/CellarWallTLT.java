package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a CellarWallTLT
 * @author Kenneth Lange
 *
 */
public class CellarWallTLT extends Tile{

	public CellarWallTLT(int id) {
		super(Assets.cellar_wall[10], id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
