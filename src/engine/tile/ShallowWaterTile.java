package engine.tile;

import engine.gfx.Assets;
/**
 * This class sets the details of a ShallowWaterTile
 * @author Kenneth Lange
 *
 */
public class ShallowWaterTile extends Tile{

	public ShallowWaterTile(int id) {
		super(Assets.shallowwater, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
