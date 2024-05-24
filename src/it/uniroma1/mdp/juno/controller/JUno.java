package it.uniroma1.mdp.juno.controller;

import java.io.FileNotFoundException;

import it.uniroma1.mdp.juno.model.GameManager;
import it.uniroma1.mdp.juno.model.PlayerProfile;
import it.uniroma1.mdp.juno.view.Frame;
import it.uniroma1.mdp.juno.view.PlayerAudioFeedback;

/**
 * This class contains the main method
 * @author Sebastiano Deodati
 */
public class JUno {
	/**
	 * Main method
	 * @param args command line arguments (ignored)
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		PlayerProfile profile;
		try {
			profile = PlayerProfile.restoreFromFile("player.ser");
		}
		catch (FileNotFoundException e) {
			System.err.println("Warning! No player profile save found, creating new profile");
			profile = new PlayerProfile("Player");
		}

		GameManager gm = GameManager.getInstance();
		gm.setHumanPlayer(profile);
		gm.addObserver(PlayerAudioFeedback.getInstance());

		new Frame(profile);
	}
}
