package it.uniroma1.mdp.juno.model;

import java.io.Serializable;

/**
 * This class implements a game record
 * @author Sebastiano Deodati
 */
public class GameRecord implements Serializable {
	private static final long serialVersionUID = -3263961192244957234L;
	/**
	 * Victory points earned
	 */
	private int victoryPoints;
	/**
	 * Adversaries for this game
	 */
	private String[] adversaries;

	/**
	 * Constructs the record
	 * @param victoryPoints victory points earned (0=loser)
	 * @param adversaries adversaries for this game
	 */
	public GameRecord(int victoryPoints, String[] adversaries) {
		this.victoryPoints = victoryPoints;
		this.adversaries = adversaries;
	}

	/**
	 * Returns victory points earned
	 * @return victory points earned
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}

	/**
	 * Returns the adversaries
	 * @return an array containing the adversaries
	 */
	public String[] getAdversaries() {
		return adversaries;
	}
}
