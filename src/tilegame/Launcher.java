package tilegame;

/**
 * This class launches the game
 * @author Kenneth Lange
 * M: Engine
 */
public class Launcher {
	/**
	 * This method sets the window name, canvas and window size, then starts the game.	
	 * @param args
	 */
	public static void main(String[] args){
		Game game = new Game("ProjectX", 1280); //Creates window size and title
		game.start(); //Starts game
	}
}