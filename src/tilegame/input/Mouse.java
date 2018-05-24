package tilegame.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
/**
 * This class is responsible for reading mouse action that is received.
 * @author Kenneth Lange
 *
 */
public class Mouse extends InputCodes implements MouseListener, MouseMotionListener {
	
	private ArrayList<Integer> PressedButtons = new ArrayList<Integer>();
	private ArrayList<Integer> DownButtons = new ArrayList<Integer>();
	
	private boolean isInside;
	
	public static int x, y;
	/**
	 * This method checks whether a button is being pressed or not.
	 * If it is, it then adds that button to an ArrayList based on it's ID.
	 */
	public void mousePressed(MouseEvent e) {
		int button = e.getButton();
		if(DownButtons.indexOf(button) == -1){
			PressedButtons.add(button);
			DownButtons.add(button);
			//Debug.Log("ADDED BUTTON " + button); //Used for debugging
		}
	}
	/**
	 * This method checks whether a button has been pressed release or not after being pressed.
	 * If it is, it then removes that button from the ArrayList based on it's ID.
	 */
	public void mouseReleased(MouseEvent e) {
		int button = e.getButton();
		if(PressedButtons.indexOf(button) != -1)
			PressedButtons.remove(PressedButtons.indexOf(button));
		if(DownButtons.indexOf(button) != -1)
			DownButtons.remove(DownButtons.indexOf(button));
		
		//Debug.Log("REMOVED BUTON " + button); //Used for debugging
	}
	/**
	 * This method keeps track of where the mouse cursor is on the x and y when a button is being held down.
	 */
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	/**
	 * This method keeps track of where the mouse cursor is on the x and y when a button is not being held down.
	 */
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}
	
	//GAME ACCESS
	/**
	 * This method checks whether a button is being pressed or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param button
	 * @return
	 */
	public boolean isButtonPressed(int button){
		if(PressedButtons.indexOf(button) != -1){
			PressedButtons.remove(PressedButtons.indexOf(button));
			return true;
		}
		return false;
	}
	/**
	 * This method checks whether the parameter button is contained in the DownButtons ArrayList and returns false if it is.
	 * @param button
	 * @return
	 */
	public boolean isMouseUp(int button){
		if(DownButtons.contains(button)){
			return false;
		}
		return true;
	}
	
	public boolean isMouseInside(){
		return isInside;
	}
	
	/**
	 * This method checks whether a button is being held down or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param button
	 * @return
	 */
	public boolean isButtonDown(int button){
		if(DownButtons.indexOf(button) != -1){
			return true;
		}
		return false;
	}
	/**
	 * This method returns the location on the x-axis of the mouse
	 * @return
	 */
	public int getX(){
		return x;
	}
	/**
	 * This method returns the location on the y-axis of the mouse
	 * @return
	 */
	public int getY(){
		return y;
	}

	//UNUSED METHODS
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {isInside = true;}
	public void mouseExited(MouseEvent e) {isInside = false;}
}
