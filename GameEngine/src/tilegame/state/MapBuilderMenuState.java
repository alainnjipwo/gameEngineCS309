package tilegame.state;

import java.awt.Graphics;

import tilegame.Handler;

/**
 * This class acts as the state that allows the user to choose if they want to create a new map or modify an existing file.
 * @author Mitchell Hoppe
 *
 */

public class MapBuilderMenuState extends State{
//	private UIImageButton startButton, exitButton;
	
	public MapBuilderMenuState(Handler handler) {
		super(handler);
		
//		startButton = new UIImageButton(handler, handler.getWidth()/2 - 128/*height of button / 2*/, handler.getHeight()/2 - 128 - 64/*width of button / 2*/, 256, 128, Assets.start_button);
//		exitButton = new UIImageButton(handler, handler.getWidth()/2 - 128/*height of button / 2*/, handler.getHeight()/2 + 128 - 64/*width of button / 2*/, 256, 128, Assets.exit_button);
	}

	@Override
	public void update() {
//		startButton.update();
//		exitButton.update();
//		if(startButton.isActivated())
//			State.setState(handler.getGame().gameState);
//		if(exitButton.isActivated())
//			System.exit(1);
	}

	@Override
	public void render(Graphics g) {
//		startButton.render(g);
//		exitButton.render(g);
	}
}