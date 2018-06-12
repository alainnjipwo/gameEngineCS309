package tilegame.debug;

import tilegame.entities.Entity;
import tilegame.staticobjects.StaticObject;
import tilegame.tile.Tile;

/**
 * This class is created for debugging purposes.
 * @author Kenneth Lange
 *
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
		StaticObject.DEBUGMODE =!StaticObject.DEBUGMODE;
		Entity.DEBUGMODE = !Entity.DEBUGMODE;
		Tile.DEBUGMODE = !Tile.DEBUGMODE;
	}
}