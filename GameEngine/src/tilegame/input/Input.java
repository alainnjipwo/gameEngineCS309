package tilegame.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
/**
 * This class uses the inputs of the keyboard and implements them into the game for it to use.
 * @author Kenneth Lange
 *
 */
public class Input extends InputCodes implements KeyListener{

	private ArrayList<Integer> PressedKeys = new ArrayList<Integer>();
	private ArrayList<Integer> DownKeys = new ArrayList<Integer>();
	/**
	 * This method checks whether a key is being pressed or not.
	 * If it is, it then adds that key to an ArrayList based on it's ID.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(DownKeys.indexOf(key) == -1){
			PressedKeys.add(key);
			DownKeys.add(key);
			//Debug.Log("ADDED KEY " + key); //Used for debugging
		}
	}
	/**
	 * This method checks whether a key has been pressed release or not after being pressed.
	 * If it is, it then removes that key from the ArrayList based on it's ID.
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(PressedKeys.indexOf(key) != -1)
			PressedKeys.remove(PressedKeys.indexOf(key));
		if(DownKeys.indexOf(key) != -1)
			DownKeys.remove(DownKeys.indexOf(key));
		
		//Debug.Log("REMOVED KEY " + key); //Used for debugging
	}
	
	//GAME ACCESS
	/**
	 * This method checks whether a key is being pressed or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param key
	 * @return
	 */
	public boolean isKeyPressed(int key){
		if(PressedKeys.indexOf(key) != -1){
			PressedKeys.remove(PressedKeys.indexOf(key));
			return true;
		}
		return false;
	}
	/**
	 * This method checks whether a key is being held down or not.
	 * If it is, it then returns true, otherwise it returns false.
	 * @param key
	 * @return
	 */
	public boolean isKeyDown(int key){
		if(DownKeys.indexOf(key) != -1){
			return true;
		}
		return false;
	}


	//UNUSED METHODS
	public void keyTyped(KeyEvent e) {}
}
