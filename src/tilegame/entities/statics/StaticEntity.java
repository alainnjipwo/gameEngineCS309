package tilegame.entities.statics;

import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.entities.Entity;
/**
 * This class is a template for all static entities
 * @author Kenneth Lange
 *
 */
public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
	public abstract BufferedImage getTexture();

}
