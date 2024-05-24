package it.uniroma1.mdp.juno.model;

import java.util.List;
import java.util.Random;

import it.uniroma1.mdp.juno.model.Card.Color;
import it.uniroma1.mdp.juno.model.Card.Type;

/**
 * This class implements random card
 * picking for AI players
 * @author Sebastiano Deodati
 *
 */
public class RandomPicking implements PlayStrategy {
	@Override
	public Card play(List<Card> cards, Card lastDropped) throws NoCompatibleCardsException {
		if(!PlayStrategy.hasCompatible(cards, lastDropped))
			throw new NoCompatibleCardsException();

		boolean cantPlayDraw4 = PlayStrategy.hasCompatibleExceptDraw4(cards, lastDropped);

		Random rng = new Random();
		Card played;

		do played = cards.get(rng.nextInt(cards.size()));
		while(!played.compatible(lastDropped) ||
				(played.getType() == Type.DRAW4 && cantPlayDraw4)
		);
		return played;
	}

	@Override
	public Color pickColor() {
		Random rng = new Random();
		Color[] colors = new Color[] {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
		return colors[rng.nextInt(3)];
	}
}
