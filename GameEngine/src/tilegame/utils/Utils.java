package tilegame.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * This method is a utilities class which loads in the world file which is converted into an actual world.
 * @author Kenneth Lange
 *
 */
public class Utils {
	/**
	 * This method tries to load in a file path for a world to be built based on. IF it fails the game closes. 
	 * @param path
	 * @return
	 */
	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	/**
	 * This method is responsible for grabbing an integer from the loaded file path
	 * @param number
	 * @return
	 */
	public static int parseInt(String number){
		try{
			return Integer.parseInt(number);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}

}
