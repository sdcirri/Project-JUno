package it.uniroma1.mdp.juno.view;

import java.util.Observable;

/**
 * This class implements a game reset event
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class GameResetEvent extends Observable {
	/**
	 * Notifies the game to reset
	 * @param keepRunning whether the game has to keep running
	 */
	public void reset(boolean keepRunning) {
		setChanged();
		notifyObservers(keepRunning);
	}
}
