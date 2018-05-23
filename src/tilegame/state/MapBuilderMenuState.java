package tilegame.state;

import java.awt.Graphics;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
//			File f = Utils.pickFile();
		}
		if(newButton.isActivated()) {
			newButton.update();
//			State.setState(new MapBuilderState(handler, "res/worlds/world1.txt"));
			try {Thread.sleep(100);}catch(Exception e) {}
			newFile();
		}
		if(exitButton.isActivated()) {
			State.setState(new MenuState(handler));
		}
	}
	
	private File newFile() {
	      JTextField name = new JTextField(5);
	      JTextField width = new JTextField(5);
	      JTextField height = new JTextField(5);
	      
	      width.setText("40");
	      height.setText("40");
	      

	      JPanel fullPanel = new JPanel();
	      JPanel namePanel = new JPanel();
	      JPanel dimPanel = new JPanel();
	      
	      fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.Y_AXIS));
	      namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
	      dimPanel.setLayout(new BoxLayout(dimPanel, BoxLayout.X_AXIS));
	      namePanel.add(new JLabel("Name:"));
	      namePanel.add(name);
	      
	      dimPanel.add(new JLabel("Width:"));
	      dimPanel.add(width);
	      dimPanel.add(Box.createHorizontalStrut(5)); // a spacer
	      dimPanel.add(new JLabel("Height:"));
	      dimPanel.add(height);
	      
	      fullPanel.add(namePanel);
	      fullPanel.add(Box.createVerticalStrut(15)); // a spacer
	      fullPanel.add(dimPanel);

	      int result = JOptionPane.showConfirmDialog(null, fullPanel, 
	               "New File", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	         System.out.println("Name: " + name.getText());
	         System.out.println("Width: " + width.getText());
	         System.out.println("Height: " + height.getText());
	      }
	      
	      return null;
	   }

	@Override
	public void render(Graphics g) {
		loadButton.render(g);
		newButton.render(g);
		exitButton.render(g);
	}
}
