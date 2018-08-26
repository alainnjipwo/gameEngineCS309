package tilegame.gfx;

import java.awt.image.BufferedImage;
/**
 * This class is responsible for animating a selected texture.
 * @author Kenneth Lange
 * M: Engine
 */
public class Animation {

	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	/**
	 * This constructor takes in the animation speed and the frames that need to be animated
	 * @param speed
	 * @param frames
	 */
	public Animation(int speed, BufferedImage[] frames) {
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	/**
	 * This method updates the current frame being animated
	 */
	public void update(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length){
				index = 0;
			}
		}
	}
	/**
	 * This method gets the current frame.
	 * @return
	 */
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}

}
