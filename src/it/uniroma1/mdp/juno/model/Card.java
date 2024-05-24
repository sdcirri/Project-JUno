package it.uniroma1.mdp.juno.model;

import java.util.Objects;

/**
 * This class implements game cards
 * @author Sebastiano Deodati
 *
 */
public class Card {
	/**
	 * Enumeration of card colors
	 * @author Sebastiano Deodati
	 *
	 */
	public static enum Color {
		RED, YELLOW, GREEN, BLUE, BLACK		// Black is for jollies
	}

	/**
	 * Enumeration of card types
	 * @author Sebastiano Deodati
	 *
	 */
	public static enum Type {
		ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
		SEVEN(7), EIGHT(8), NINE(9), BLOCK(20), REVERSE(20), DRAW2(20),
		DRAW4(50), COLORCHANGE(50);

		/**
		 * Value in points
		 */
		private final int points;

		/**
		 * Constructs a card type worth the specified
		 * amount of points
		 * @param points points worth
		 */
		private Type(int points) {
			this.points = points;
		}

		/**
		 * Returns the points for this type
		 * @return the points for this type
		 */
		public int getPoints() {
			return points;
		}
	}

	/**
	 * Color of the card
	 */
	private Color color;
	/**
	 * Type of the card
	 */
	private Type type;

	/**
	 * Constructs a new card with the specified
	 * color and type
	 * @param color card color
	 * @param type card type
	 */
	public Card(Color color, Type type) {
		this.color = color;
		this.type = type;
	}

	/**
	 * Returns the card color
	 * @return the card color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the card type
	 * @return the card type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns the points for this card
	 * @return the points for this card
	 */
	public int getPoints() {
		return type.getPoints();
	}

	/**
	 * Determines whether the current card can
	 * be played after the c card
	 * @param c the card already played
	 * @return true if this card can be played, false otherwise
	 */
	public boolean compatible(Card c) {
		if(color == Color.BLACK)	return true;

		if(c == new Card(Color.BLACK, Type.COLORCHANGE))	// When color change is set as first card
			return true;

		return color == c.color || type == c.type;
	}

	/**
	 * Changes the card color if it's a jolly
	 * @param color color to set
	 */
	public void setColor(Color color) {
		if(this.color == Color.BLACK)
			this.color = color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		return color == other.color && type == other.type;
	}

	@Override
	public String toString() {
		return "Card(" + type + ", " + color + ")";
	}
}
