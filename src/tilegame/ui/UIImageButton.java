package tilegame.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import tilegame.Handler;
import tilegame.input.Mouse;
/**
 * This class is responsible for creating a UIImage button
 * @author Kenneth Lange
 *
 */
public class UIImageButton extends UIObject{
	
	private BufferedImage[] images;
	private Handler handler;
	private boolean step1, activated;
	/**
	 * This constructor takes in the handler, location, width and height and the image texture array of a button to be added.
	 * @param handler
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param images
	 */
	public UIImageButton(Handler handler, float x, float y, int width, int height, BufferedImage[] images) {
		super(handler, x, y, width, height);
		this.handler = handler;
		this.images = images;
	}
	
	/**
	 * This method updates a button for when it is hovered over and clicked.
	 */
	@Override
	public void update() {
		if(bounds.contains(handler.getMouse().getX(), handler.getMouse().getY())) 
			hovering = true;
		
		else 
			hovering = false;
		
		//This part of the class could use some help because if player releases mouse button on any button, it activates.
		if(hovering && handler.getMouse().isButtonDown(Mouse.LEFT_MOUSE))
			step1 = true;
		if(step1 && hovering && !handler.getMouse().isButtonDown(Mouse.LEFT_MOUSE))
			activated = true;
		
		else
			activated = false;
	}
	/**
	 * This method renders the updated button.
	 */
	@Override
	public void render(Graphics g) {
		if(hovering && handler.getMouse().isMouseInside())
			g.drawImage(images[1],(int) x, (int) y, width, height, null);
		else
			g.drawImage(images[0],(int) x, (int) y, width, height, null);
	}
	//Getters
	public boolean isActivated() {
		return activated;
	}
	
	/*
	 * If the button breaks, this will completley stop false positives.
	 */
	public void hardReset() {
		hovering = false;
		step1 = false;
		activated = false;
	}
}

