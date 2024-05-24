package it.uniroma1.mdp.juno.view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;

import it.uniroma1.mdp.juno.model.Card;
import it.uniroma1.mdp.juno.model.Card.Color;
import it.uniroma1.mdp.juno.model.Card.Type;

/**
 * This class implements the texture map for cards
 * @author Sebastiano Deodati
 */
public class CardTextureMap {
	/**
	 * Dimensions (in pixels) of the card
	 */
	public static final int CARD_WIDTH = 240, CARD_HEIGHT = 360;
	/**
	 * Images containing the maps
	 */
	private static BufferedImage map, map_jolly;
	/**
	 * Texture map
	 */
	private static Map<Card, Image> cards;

	/**
	 * Prevents the class from
	 * being instantiated
	 */
	private CardTextureMap() {
		
	}

	/**
	 * Builds the map
	 */
	private static void buildMap() {
		cards = new HashMap<Card, Image>();
		Card[] card = new Card[] {
				new Card(Color.RED, Type.ZERO),
				new Card(Color.RED, Type.ONE),
				new Card(Color.RED, Type.TWO),
				new Card(Color.RED, Type.THREE),
				new Card(Color.RED, Type.FOUR),
				new Card(Color.RED, Type.FIVE),
				new Card(Color.RED, Type.SIX),
				new Card(Color.RED, Type.SEVEN),
				new Card(Color.RED, Type.EIGHT),
				new Card(Color.RED, Type.NINE),
				new Card(Color.RED, Type.BLOCK),
				new Card(Color.RED, Type.REVERSE),
				new Card(Color.RED, Type.DRAW2),
				new Card(Color.YELLOW, Type.ZERO),
				new Card(Color.YELLOW, Type.ONE),
				new Card(Color.YELLOW, Type.TWO),
				new Card(Color.YELLOW, Type.THREE),
				new Card(Color.YELLOW, Type.FOUR),
				new Card(Color.YELLOW, Type.FIVE),
				new Card(Color.YELLOW, Type.SIX),
				new Card(Color.YELLOW, Type.SEVEN),
				new Card(Color.YELLOW, Type.EIGHT),
				new Card(Color.YELLOW, Type.NINE),
				new Card(Color.YELLOW, Type.BLOCK),
				new Card(Color.YELLOW, Type.REVERSE),
				new Card(Color.YELLOW, Type.DRAW2),
				new Card(Color.GREEN, Type.ZERO),
				new Card(Color.GREEN, Type.ONE),
				new Card(Color.GREEN, Type.TWO),
				new Card(Color.GREEN, Type.THREE),
				new Card(Color.GREEN, Type.FOUR),
				new Card(Color.GREEN, Type.FIVE),
				new Card(Color.GREEN, Type.SIX),
				new Card(Color.GREEN, Type.SEVEN),
				new Card(Color.GREEN, Type.EIGHT),
				new Card(Color.GREEN, Type.NINE),
				new Card(Color.GREEN, Type.BLOCK),
				new Card(Color.GREEN, Type.REVERSE),
				new Card(Color.GREEN, Type.DRAW2),
				new Card(Color.BLUE, Type.ZERO),
				new Card(Color.BLUE, Type.ONE),
				new Card(Color.BLUE, Type.TWO),
				new Card(Color.BLUE, Type.THREE),
				new Card(Color.BLUE, Type.FOUR),
				new Card(Color.BLUE, Type.FIVE),
				new Card(Color.BLUE, Type.SIX),
				new Card(Color.BLUE, Type.SEVEN),
				new Card(Color.BLUE, Type.EIGHT),
				new Card(Color.BLUE, Type.NINE),
				new Card(Color.BLUE, Type.BLOCK),
				new Card(Color.BLUE, Type.REVERSE),
				new Card(Color.BLUE, Type.DRAW2),
				new Card(Color.BLACK, Type.COLORCHANGE),
				new Card(Color.RED, Type.COLORCHANGE),
				new Card(Color.BLUE, Type.COLORCHANGE),
				new Card(Color.YELLOW, Type.COLORCHANGE),
				new Card(Color.GREEN, Type.COLORCHANGE),
				new Card(Color.BLACK, Type.DRAW4),
				new Card(Color.RED, Type.DRAW4),
				new Card(Color.BLUE, Type.DRAW4),
				new Card(Color.YELLOW, Type.DRAW4),
				new Card(Color.GREEN, Type.DRAW4)
		};
		List<Image> textures = new ArrayList<>();

		try {
			map = ImageIO.read(CardTextureMap.class.getResource("/res/deck.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't locate resource! Check build environment");
		}

		for(int j = 0; j < 4; j++)
			for(int i = 0; i < 13; i++)
				textures.add(map.getSubimage(i * CARD_WIDTH, j * CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT));

		try {
			map_jolly = ImageIO.read(CardTextureMap.class.getResource("/res/jollys.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't locate resource! Check build environment");
		}

		for(int j = 0; j < 2; j++)
			for(int i = 0; i < 5; i++)
				textures.add(map_jolly.getSubimage(i * CARD_WIDTH, j * CARD_HEIGHT, CARD_WIDTH, CARD_HEIGHT));

		for(int i = 0; i < card.length; i++)
			cards.put(card[i], textures.get(i));
	}

	/**
	 * Returns the map
	 * @return the texture map as HashMap
	 */
	public static Map<Card, Image> getMap() {
		if(cards == null)	buildMap();
		return cards;
	}
}
