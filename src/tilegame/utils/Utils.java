package tilegame.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
/**
 * This method is a utilities class which loads in the world file which is converted into an actual world.
 * @author Kenneth Lange
 *
 */
public class Utils {
	/**
	 * This method tries to load in a file path for a world to be built based on. If it fails the game closes. 
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
	 * Saves the world as a text file given the tile IDs
	 * @param path
	 * @param locations
	 * @param spawn_x
	 * @param spawn_y
	 */
	public static void saveWorld(String path, int[][] locations, int spawn_x, int spawn_y){
		try{
			FileWriter fw = new FileWriter(new File(path));
			BufferedWriter bw = new BufferedWriter(fw);

			bw.append(locations.length + " " + locations[0].length + "\n");
			bw.append(((spawn_x - 1) / 64) + " " + ((spawn_y + 22) / 64) + "\n");
			for (int i = 0; i < locations.length; i++) {
				for (int j = 0; j < locations[i].length; j++) {
					bw.append(locations[i][j] + " ");
				}
				bw.append("\n");
			}
			
			bw.close();
			fw.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static File pickFile() {
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(null);
		File file = fc.getSelectedFile();
		return file;
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
