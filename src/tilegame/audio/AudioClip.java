package tilegame.audio;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import tilegame.debug.Debug;

/**
 * 
 * @author Kenny
 * M: Engine
 */

public class AudioClip {

	private File file;
	/**
	 * This constructor uses the file path to find and use the audio in the location of the parameter path.
	 * It then checks to make sure that the file exists.
	 * @param path
	 */
	public AudioClip(String path) {
		file = new File(path);
		if(!file.exists()){
			Debug.LogError("Error >> AudioClip Not Found");
		}
	}
	/**
	 * THis method gets and returns file.
	 * @return
	 */
	public File getFile(){
		return file;
	}
	/**
	 * This method tries to get the audio input stream.
	 * If the stream does not exist it throws and error.
	 * @return
	 */
	public AudioInputStream getAudioStream(){
		try{
			return AudioSystem.getAudioInputStream(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
