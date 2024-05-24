package it.uniroma1.mdp.juno.model;

import java.util.List;

import it.uniroma1.mdp.juno.model.Card.Color;
import it.uniroma1.mdp.juno.model.Card.Type;

/**
 * Interface for player card
 * picking behavior
 * @author Sebastiano Deodati
 */
public interface PlayStrategy {
	/**
	 * Checks if a compatible card with the last dropped is present
	 * @param cards list of cards currently held
	 * @param lastDropped last dropped card for compatibility check
	 * @return true if there is at least one compatible card, false otherwise
	 */
	static boolean hasCompatible(List<Card> cards, Card lastDropped) {
		return hasCompatibleExceptDraw4(cards, lastDropped) || cards.contains(new Card(Color.BLACK, Type.DRAW4));
	}

	/**
	 * Checks if a compatible card with the last dropped is present
	 * except for draw4
	 * @param cards list of cards currently held
	 * @param lastDropped last dropped card for compatibility check
	 * @return true if there is at least one compatible card, false otherwise
	 */
	static boolean hasCompatibleExceptDraw4(List<Card> cards, Card lastDropped) {
		for(Card c: cards)
			if(c.compatible(lastDropped) && c.getType() != Type.DRAW4)
				return true;
		return false;
	}

	/**
	 * Selects a card to play
	 * @param cards list of cards currently held
	 * @param lastDropped card played by previous player
	 * @return selected card
	 * @throws NoCompatibleCardsException when no compatible cards are found
	 */
	Card play(List<Card> cards, Card lastDropped) throws NoCompatibleCardsException;

	/**
	 * Chooses a color when a jolly is played
	 * @return the chosen color
	 */
	Color pickColor();
}
