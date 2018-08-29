package engine.managers.entities.nonmoving;

import engine.Handler;
import engine.managers.entities.Entity;
/**
 * This class is a template for all static entities
 * @author Kenneth Lange
 *
 */
public abstract class Nonmoving extends Entity{

	public Nonmoving(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		health = DEFAULT_HEALTH;
	}
}