package it.uniroma1.mdp.juno.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import it.uniroma1.mdp.juno.model.Card.Color;

/**
 * This class implements basic player behavior
 * for both AI and human
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public abstract class Player extends Observable {
	/**
	 * Play strategy to pick cards
	 */
	protected PlayStrategy ps;
	/**
	 * Held cards
	 */
	protected List<Card> cards;
	/**
	 * Player profile
	 */
	private PlayerProfile profile;
	/**
	 * Whether has said UNO
	 */
	private boolean uno;

	/**
	 * Constructs a new player with
	 * the specified profile info
	 * @param profile player profile
	 * @param ps play strategy to apply
	 */
	protected Player(PlayerProfile profile, PlayStrategy ps) {
		this.profile = profile;
		this.ps = ps;
		cards = new ArrayList<Card>();
	}

	/**
	 * Returns profile info
	 * @return profile info
	 */
	public PlayerProfile getProfile() {
		return profile;
	}

	/**
	 * Returns the current number of cards in hand
	 * @return the current number of cards in hand
	 */
	public int getCardsCount() {
		return cards.size();
	}

	/**
	 * Determines whether the player has done playing this round
	 * @return true if the player has no cards left, false otherwise
	 */
	public boolean done() {
		return cards.size() == 0;
	}

	/**
	 * Takes a card
	 * @param c the card to take
	 */
	public void takeCard(Card c) {
		uno = false;	// If drew cards, surely has more than 1
		cards.add(c);
	}

	/**
	 * Returns the current cards of the player
	 * @return the current cards of the player as an array
	 */
	public Card[] getCards() {
		return cards.toArray(Card[]::new);
	}

	/**
	 * Completely reset player cards and returns them
	 * @return array of previously held cards
	 */
	public Card[] dropAll() {
		Card[] cardArray = cards.toArray(new Card[0]);
		cards = new ArrayList<Card>();
		return cardArray;
	}

	/**
	 * Plays the first compatible card
	 * between randomly picked ones
	 * @param lastDropped last dropped card for compatibility check
	 * @return the card played
	 * @throws NoCompatibleCardsException when no compatible cards are found
	 */
	public Card playCard(Card lastDropped) throws NoCompatibleCardsException {
		Card played = ps.play(cards, lastDropped);
		cards.remove(played);

		return played;
	}

	/**
	 * Chooses a color when a jolly is played
	 * @return the chosen color
	 */
	public Color pickColor() {
		return ps.pickColor();
	}

	/**
	 * Makes the player say UNO!
	 */
	public void uno() {
		uno = true;
		setChanged();
		notifyObservers();
	}

	/**
	 * Returns whether the player said UNO!
	 * @return true if the player said UNO, false otherwise
	 */
	public boolean saidUno() {
		return uno;
	}
}
