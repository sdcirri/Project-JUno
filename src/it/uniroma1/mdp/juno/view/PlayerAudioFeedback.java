package it.uniroma1.mdp.juno.view;

import java.util.Observable;
import java.util.Observer;

import it.uniroma1.mdp.juno.model.GameManager;

/**
 * This class gives audio feedback on player actions
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class PlayerAudioFeedback implements Observer {
	private static PlayerAudioFeedback instance;

	/**
	 * Prevents instantiation
	 */
	private PlayerAudioFeedback() {

	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GameManager && arg != null) {
			int sound = (int)arg;
			switch(sound) {
				case GameManager.PLAY_CARD:
					AudioManager.playSound(PlayerAudioFeedback.class.getResource("/res/card.wav"));
					break;
	
				case GameManager.PLAY_UNO:
					AudioManager.playSound(PlayerAudioFeedback.class.getResource("/res/uno.wav"));
					break;

				case GameManager.PLAY_WIN:
					AudioManager.playSound(PlayerAudioFeedback.class.getResource("/res/win.wav"));
					break;
			}
		}
	}

	/**
	 * Returns the instance of the audio feedback system
	 * @return a reference to the audio feedback system
	 */
	public static PlayerAudioFeedback getInstance() {
		if(instance == null)	instance = new PlayerAudioFeedback();
		return instance;
	}
}
