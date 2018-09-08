package engine.tile;

/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 *
 */
public class AirTile extends Tile{

	public AirTile(int id) {
		super(null, id);
	}

	@Override
	public boolean isSolid(){
		return true;
	}
}
