package it.uniroma1.mdp.juno.controller;

import java.awt.Point;

import it.uniroma1.mdp.juno.model.Card;

/**
 * This class represents a pixel region
 * on which a card is present
 * @author Sebastiano Deodati
 */
public class CardRect {
	/**
	 * Associated card
	 */
	private Card card;
	/**
	 * Top left corner of the region
	 */
	private Point startPoint;
	/**
	 * Width and height of the region
	 */
	private int w, h;

	/**
	 * Constructs the region
	 * @param card card to associate
	 * @param startPoint top-left point of the region
	 * @param w total width
	 * @param h total height
	 */
	public CardRect(Card card, Point startPoint, int w, int h) {
		this.card = card;
		this.startPoint = startPoint;
		this.w = w;
		this.h = h;
	}

	/**
	 * Checks whether the point is in this region
	 * @param point point to check
	 * @return true if the point is in the region, false otherwise
	 */
	public boolean inRegion(Point point) {
		int x = point.x, y = point.y, x0 = startPoint.x, y0 = startPoint.y;
		return x >= x0 && x <= (x0 + w) &&
				y >= y0 && y <= (y0 + h);
	}

	/**
	 * Returns the card associated with the region
	 * @return the card associated with the region
	 */
	public Card getCard() {
		return card;
	}

	@Override
	public String toString() {
		return "CardRect(" + card + ", " + startPoint + ", " + w + ", " + h + ")";
	}
}
