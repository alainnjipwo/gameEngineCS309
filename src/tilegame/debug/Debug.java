package tilegame.debug;

import tilegame.managers.entities.Entity;
import tilegame.managers.locators.Locator;
import tilegame.tile.Tile;

/**
 * This class is created for debugging purposes.
 * @author Kenneth Lange
 * M: Engine
 */
public class Debug {
	/**
	 * This method displays a message in the log based on the parameter msg.
	 * @param msg
	 */
	public static void Log(String msg){
		System.out.println(msg);
	}
	/**
	 * This method displays an error message in the log based on the parameter msg.
	 * @param msg
	 */
	public static void  LogError(String msg){
		System.err.println(msg);
	}
	/**
	 * This method sets the Debug Mode on/off for convenience of seeing hit boxes and other developer specific details.
	 */
	public static void setDEBUGMODE() {
		Locator.DEBUGMODE =!Locator.DEBUGMODE;
		Entity.DEBUGMODE = !Entity.DEBUGMODE;
		Tile.DEBUGMODE = !Tile.DEBUGMODE;
	}
}