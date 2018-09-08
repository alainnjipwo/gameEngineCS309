package engine.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.debug.Debug;
/**
 * This class is responsible for loading in images
 * @author Kenneth Lange
 *
 */
public class ImageLoader {
	/**
	 * This method tries to load in the selected image from the path given.
	 * @param path
	 * @return
	 */
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(ImageLoader.class.getResource(path)); //Loads in image
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1); //If the image cannot be loaded, the window closes
			Debug.LogError(path + " was not loaded.");
		}
		return null;
	}
	
}
