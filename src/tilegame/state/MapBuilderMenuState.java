package tilegame.state;

import java.awt.Graphics;
import java.io.File;

import tilegame.Handler;
import tilegame.gfx.Assets;
import tilegame.ui.UIImageButton;
import tilegame.utils.Utils;

/**
 * This class acts as the state that allows the user to choose if they want to create a new map or modify an existing file.
 * @author Mitchell Hoppe
 *
 */

public class MapBuilderMenuState extends State{
	private UIImageButton loadButton, newButton, exitButton;
	
	public MapBuilderMenuState(Handler handler) {
		super(handler);
		
		loadButton 	= new UIImageButton(handler, handler.getWidth()/2 - 128/*height of button / 2*/, handler.getHeight()/2 - 128 - 64/*width of button / 2*/, 256, 128, Assets.load_button);
		newButton 	= new UIImageButton(handler, handler.getWidth()/2 - 128/*height of button / 2*/, handler.getHeight()/2 - 000 - 64/*width of button / 2*/, 256, 128, Assets.new_button);
		exitButton 	= new UIImageButton(handler, handler.getWidth()/2 - 128/*height of button / 2*/, handler.getHeight()/2 + 128 - 64/*width of button / 2*/, 256, 128, Assets.exit_button);
	}

	@Override
	public void update() {
		loadButton.update();
		newButton.update();
		exitButton.update();
		if(loadButton.isActivated()) {
			File f = Utils.pickFile();
		}
		if(newButton.isActivated())
			State.setState(new MapBuilderState(handler, "res/worlds/world1.txt"));
		if(exitButton.isActivated())
			State.setState(new MenuState(handler));
	}

	@Override
	public void render(Graphics g) {
		loadButton.render(g);
		newButton.render(g);
		exitButton.render(g);
	}
}
