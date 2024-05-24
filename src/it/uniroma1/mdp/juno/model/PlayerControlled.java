package it.uniroma1.mdp.juno.model;

import java.util.List;

import it.uniroma1.mdp.juno.model.Card.Color;

/**
 * This class is an empty placeholder for the
 * human player, which does not play by its own
 * @author Sebastiano Deodati
 */
public class PlayerControlled implements PlayStrategy {
	@Override
	public Card play(List<Card> cards, Card lastDropped) {
		return null;
	}

	@Override
	public Color pickColor() {
		return null;
	}
}
