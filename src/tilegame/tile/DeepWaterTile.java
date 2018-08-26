package tilegame.tile;

import tilegame.gfx.Assets;
/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 * M: Game
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
