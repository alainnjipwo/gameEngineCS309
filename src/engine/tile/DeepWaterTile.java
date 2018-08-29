package engine.tile;

import engine.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class DeepWaterTile extends Tile{

	public DeepWaterTile(int id) {
		super(Assets.deepwater, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
