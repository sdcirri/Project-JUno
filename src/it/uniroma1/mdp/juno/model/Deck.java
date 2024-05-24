package it.uniroma1.mdp.juno.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import it.uniroma1.mdp.juno.model.Card.Color;
import it.uniroma1.mdp.juno.model.Card.Type;

/**
 * This class implements the game deck
 * @author Sebastiano Deodati
 *
 */
public class Deck {
	/**
	 * Class instance
	 */
	private static Deck instance;
	/**
	 * Cards in the deck
	 */
	public Stack<Card> cards;

	/**
	 * Constructs the deck
	 */
	private Deck() {
		reset();
	}

	/**
	 * Returns the instance of the deck
	 * @return a reference to the deck
	 */
	public static Deck getInstance() {
		if(instance == null)
			instance = new Deck();
		return instance;
	}

	/**
	 * "Takes" a card from the deck returning it
	 * and notifies the game if the deck is empty
	 * @return the first card of the deck
	 */
	public Card takeCard() {
		Card card = cards.pop();
		return card;
	}

	/**
	 * Checks whether the deck is finished
	 * @return true if the deck is empty, false otherwise
	 */
	public boolean isEmpty() {
		return cards.size() == 0;
	}

	/**
	 * Resets the deck and shuffles it
	 */
	public void reset() {
		cards = new Stack<Card>();

		for(Color c: Color.values()) {
			List<Type> single = new ArrayList<>(Arrays.asList(Type.ZERO, Type.COLORCHANGE, Type.DRAW4));

			if(c != Color.BLACK) {
				for(Type t: Type.values()) {
					Color cardColor = (t == Type.COLORCHANGE || t == Type.DRAW4) ? Color.BLACK : c;
					cards.add(new Card(cardColor, t));
					if(!single.contains(t))
						cards.add(new Card(cardColor, t));
				}
			}
		}

		Collections.shuffle(cards);
	}

	/**
	 * Rebuilds the deck from dropped cards
	 * @param drops list of dropped cards
	 */
	public void fromDrops(List<Card> drops) {
		assert cards.size() == 0;

		Collections.shuffle(drops);
		for(Card c: drops)
			cards.add(c);
	}
}
