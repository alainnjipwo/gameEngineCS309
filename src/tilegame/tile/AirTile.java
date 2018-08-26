package tilegame.tile;

/**
 * This class sets the details of a DeepWaterTile
 * @author Kenneth Lange
 * M: Game
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
