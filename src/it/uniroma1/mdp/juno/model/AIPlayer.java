package it.uniroma1.mdp.juno.model;

/**
 * This class implements AI player
 * behavior
 * @author Sebastiano Deodati
 */
public class AIPlayer extends Player {
	/**
	 * Constructs the AI player
	 * @param profile player profile to use
	 */
	public AIPlayer(PlayerProfile profile) {
		super(profile, new RandomPicking());
	}

	/**
	 * Checks whether the player has to say UNO
	 * and if so it says that
	 */
	public void checkForUno() {
		if(cards.size() == 1)
			super.uno();
	}
}
