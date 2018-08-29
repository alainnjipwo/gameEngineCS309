package engine.state;

import java.awt.Graphics;

import engine.Handler;
import engine.gfx.Assets;
import engine.ui.UIImageButton;
/**
 * This class acts as the menu state of the game. It is responsible for loading in button images and making them go to to somewhere when clicked.
 * @author Kenneth Lange
 *
 */
public class MenuState extends State{
	
	private UIImageButton startButton, mapBuilderButton, exitButton;
	
	public MenuState(Handler handler) {
		super(handler);
		//Buttons are 2 pixels Apart
		startButton 		= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 - 130 - 64/*height of button / 2 */, 256, 128, Assets.start_button);
		mapBuilderButton 	= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 - 000 - 64/*height of button / 2 */, 256, 128, Assets.map_builder_button);
		exitButton 			= new UIImageButton(handler, handler.getWidth()/2 - 128/*width of button / 2 */, handler.getHeight()/2 + 130 - 64/*height of button / 2 */, 256, 128, Assets.exit_button);
	}

	@Override
	public void update() {
		startButton.update();
		mapBuilderButton.update();
		exitButton.update();
		if(startButton.isActivated())
			State.setState(handler.getEngine().gameState);
		if(mapBuilderButton.isActivated())
			State.setState(new MapBuilderMenuState(handler));
		if(exitButton.isActivated())
			System.exit(1);
	}

	@Override
	public void render(Graphics g) {
		startButton.render(g);
		mapBuilderButton.render(g);
		exitButton.render(g);
	}

}