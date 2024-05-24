package it.uniroma1.mdp.juno.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the pile of
 * dropped cards
 * @author Sebastiano Deodati
 *
 */
public class DropPile {
	/**
	 * Class instance
	 */
	private static DropPile instance;
	/**
	 * Cards in pile
	 */
	private List<Card> cards;
	/**
	 * Last and second last dropped card
	 */
	private Card lastCard, secondLastCard;

	/**
	 * Constructs an empty pile of cards
	 */
	private DropPile() {
		cards = new ArrayList<Card>();
	}

	/**
	 * Returns the instance of the pile
	 * @return a reference to the pile
	 */
	public static DropPile getInstance() {
		if(instance == null)	instance = new DropPile();
		return instance;
	}

	/**
	 * Adds the card c to the dropped cards
	 * @param c card to add
	 */
	public void addCard(Card c) {
		secondLastCard = lastCard;
		lastCard = c;
		cards.add(c);
	}

	/**
	 * Returns the last card dropped
	 * @return the last card dropped
	 */
	public Card getLastCard() {
		return lastCard;
	}

	/**
	 * Returns the second last card dropped
	 * @return the second last card dropped
	 */
	public Card getSecondLastCard() {
		return secondLastCard;
	}

	/**
	 * Empties the pile and then return
	 * all of the dropped cards
	 * @return list of the dropped cards
	 */
	public List<Card> getDroppedCards() {
		List<Card> result = new ArrayList<>(cards);
		cards.clear();
		return result;
	}
}
