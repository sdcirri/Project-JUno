package it.uniroma1.mdp.juno.view;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class implements the audio manager
 * to play audio samples
 * @author Sebastiano Deodati
 */
public class AudioManager {
	/**
	 * Prevents instantiation
	 */
	private AudioManager() {

	}

	/**
	 * Plays the sound at the specified URL
	 * Note: only 16bit less than 22KHz Wave files are supported
	 * @param sound URL of the sound to play
	 */
	public static void playSound(URL sound) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		}
		catch(IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
