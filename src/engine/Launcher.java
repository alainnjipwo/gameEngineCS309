package engine;

/**
 * This class launches the game
 * @author Kenneth Lange, Alain Njipwo, Max Medberry, Mitch Hope
 *
 */
public class Launcher {
	/**
	 * This method sets the window name, canvas and window size, then starts the game.	
	 * @param args
	 */
	public static void main(String[] args){
		Engine engine = new Engine("ProjectX", 1280); //Creates window size and title
		engine.start(); //Starts game
	}
}