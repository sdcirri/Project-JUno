package it.uniroma1.mdp.juno.model;

import it.uniroma1.mdp.juno.model.Card.Color;

/**
 * This class implements the human player
 * @author Sebastiano Deodati
 *
 */
public class HumanPlayer extends Player {
	/**
	 * Class instance
	 */
	private static HumanPlayer instance;
	/**
	 * Played card
	 */
	private Card played;

	/**
	 * Initializes the player
	 * @param profile human player profile
	 */
	private HumanPlayer(PlayerProfile profile) {
		super(profile, new PlayerControlled());
	}

	/**
	 * Returns whether the player has to play
	 * @return true if player has to play, false otherwise
	 */
	public boolean hasToPlay() {
		if(played == null)	return true;
		return hasToPickColor();
	}

	/**
	 * Returns whether the player has to pick a color
	 * @return true if the player has to pick a color,
	 * 			false otherwise
	 */
	public boolean hasToPickColor() {
		if(played == null)	return false;
		return played.getColor() == Color.BLACK;
	}

	/**
	 * Plays the card at the specified index
	 * @param ind index of the card to play
	 * @param lastPlayed card played by previous player
	 * @throws IncompatibleCardException when the selected card is not playable
	 */
	public void playCardByIndex(int ind, Card lastPlayed) throws IncompatibleCardException {
		if(!cards.get(ind).compatible(lastPlayed))
			throw new IncompatibleCardException();

		if(played == null)
			played = cards.remove(ind);
	}

	/**
	 * Sets the given color to the played
	 * card ONLY IF it's a jolly and a color
	 * hasn't been picked yet
	 * @param color color to set
	 */
	public void pickColor(Color color) {
		if(hasToPickColor())
			played.setColor(color);
	}

	@Override
	public Card playCard(Card lastDropped) throws NoCompatibleCardsException {
		if(!PlayStrategy.hasCompatible(cards, lastDropped) && played == null)
			throw new NoCompatibleCardsException();

		if(hasToPickColor())
			return null;

		Card playedCard = played;
		played = null;
		return playedCard;
	}

	/**
	 * Returns the instance of the human player
	 * @param pp human player profile
	 * @return a reference to the instance of the human player
	 */
	public static HumanPlayer getInstance(PlayerProfile pp) {
		if(instance == null)	instance = new HumanPlayer(pp);
		return instance;
	}
}
