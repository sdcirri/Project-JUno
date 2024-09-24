package it.uniroma1.mdp.juno.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import it.uniroma1.mdp.juno.controller.CardRect;
import it.uniroma1.mdp.juno.controller.GameScreenClickListener;
import it.uniroma1.mdp.juno.model.Avatar;
import it.uniroma1.mdp.juno.model.Card;
import it.uniroma1.mdp.juno.model.Card.Type;
import it.uniroma1.mdp.juno.model.GameManager;
import it.uniroma1.mdp.juno.model.HumanPlayer;
import it.uniroma1.mdp.juno.model.IncompatibleCardException;
import it.uniroma1.mdp.juno.model.Player;
import it.uniroma1.mdp.juno.model.PlayerNotReadyException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * This class implements the game screen
 * drawing area
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class GameScreenDraw extends JPanel implements Observer {
	private static final long serialVersionUID = -3632851171973126203L;

	/**
	 * Mouse listener for left clicks on cards
	 */
	private GameScreenClickListener clickListener;
	/**
	 * Texture map for cards
	 */
	private Map<Card, Image> cardTextureMap;
	/**
	 * Texture map for avatars
	 */
	private Map<Avatar, Image> avatarTextureMap;
	/**
	 * Regions for detecting clicks on cards
	 */
	private List<CardRect> cardRects;
	/**
	 * Regions for detecting clicks on
	 * the jolly color picker
	 */
	private Map<Card.Color, CardRect> jollys;
	/**
	 * The game manager
	 */
	private GameManager game;
	/**
	 * Card back and arrow textures
	 */
	private Image card_back, arrow_up, arrow_down, arrow_left, arrow_right;
	/**
	 * Last played jolly by player
	 */
	private Type lastJolly;
	/**
	 * Last player
	 */
	private int lastPlayer;

	/**
	 * Constructs the drawing area
	 */
	public GameScreenDraw() {
		setPreferredSize(new Dimension(800, 500));

		clickListener = new GameScreenClickListener();
		clickListener.addObserver(this);
		addMouseListener(clickListener);

		cardTextureMap = CardTextureMap.getMap();
		avatarTextureMap = AvatarTextureMap.getMap();
		game = GameManager.getInstance();
		game.addObserver(this);

		try {
			arrow_up = ImageIO.read(GameScreenDraw.class.getResource("/res/arrow_up.png"));
			arrow_down = ImageIO.read(GameScreenDraw.class.getResource("/res/arrow_down.png"));
			arrow_left = ImageIO.read(GameScreenDraw.class.getResource("/res/arrow_left.png"));
			arrow_right = ImageIO.read(GameScreenDraw.class.getResource("/res/arrow_right.png"));
			card_back = ImageIO.read(GameScreenDraw.class.getResource("/res/card_back.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(100);
		}

		initJollyPicker();
	}

	/**
	 * Initializes the regions for the jolly picker
	 */
	private void initJollyPicker() {
		jollys = new HashMap<Card.Color, CardRect>();
		jollys.put(Card.Color.RED, new CardRect(null, new Point(200, 100), 80, 120));
		jollys.put(Card.Color.BLUE, new CardRect(null, new Point(510, 100), 80, 120));
		jollys.put(Card.Color.YELLOW, new CardRect(null, new Point(200, 250), 80, 120));
		jollys.put(Card.Color.GREEN, new CardRect(null, new Point(510, 250), 80, 120));
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D)g;

		Rectangle bg = new Rectangle(0, 0, 800, 600);
		graphics2d.setColor(new Color(0, 127, 0));
		graphics2d.fill(bg);

		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);
		trans.translate(300, 190);			
		trans.scale(.15, .15);

		if(!game.deckEmpty())
			graphics2d.drawImage(card_back, trans, this);

		Image drop = cardTextureMap.get(game.getLastPlayed());
		trans.setToIdentity();
		trans.translate(410, 190);
		trans.scale(.35, .35);
		graphics2d.drawImage(drop, trans, this);

		Player winner = game.getWinner();
		if(winner == null)
			playerUpdate(g);
		else gameOverScreen(g,
				(winner == game.getPlayer(GameManager.PLAYER_HUMAN))
		);
	}

	/**
	 * Updates the game field with player info
	 * @param g graphics object
	 */
	private void playerUpdate(Graphics g) {
		Graphics2D graphics2d = (Graphics2D)g;
		int toDisplay, i;

		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);

		// Turn arrow
		trans.scale(2, 2);
		switch(game.getCurrentPlayer()) {
			case GameManager.PLAYER_HUMAN:
				trans.translate(360 / 2, 320 / 2);
				graphics2d.drawImage(arrow_down, trans, this);
				break;
			case GameManager.PLAYER_AI1:
				trans.translate(200 / 2, 220 / 2);
				graphics2d.drawImage(arrow_left, trans, this);
				break;
			case GameManager.PLAYER_AI2:
				trans.translate(360 / 2, 120 / 2);
				graphics2d.drawImage(arrow_up, trans, this);
				break;
			case GameManager.PLAYER_AI3:
				trans.translate(530 / 2, 220 / 2);
				graphics2d.drawImage(arrow_right, trans, this);
				break;
		}
		trans.setToIdentity();

		Player ai1 = game.getPlayer(GameManager.PLAYER_AI1),
				ai2 = game.getPlayer(GameManager.PLAYER_AI2),
				ai3 = game.getPlayer(GameManager.PLAYER_AI3);
		HumanPlayer human = (HumanPlayer)game.getPlayer(GameManager.PLAYER_HUMAN);

		// AI1 (left)
		trans.scale(.15, .15);
		toDisplay = Math.min(10, ai1.getCardsCount());
		trans.translate(0, 80 / .15);
		for(i = 0; i < toDisplay; i++) {
			trans.translate(0, 20 / .15);
			graphics2d.drawImage(card_back, trans, this);
		}
		trans.setToIdentity();

		// AI2 (top)
		trans.scale(.15, .15);
		toDisplay = Math.min(10, ai2.getCardsCount());
		trans.translate(300 / .15, 0);
		for(i = 0; i < toDisplay; i++) {
			trans.translate(20 / .15, 0);
			graphics2d.drawImage(card_back, trans, this);
		}
		trans.setToIdentity();

		// AI3 (right)
		trans.scale(.15, .15);
		toDisplay = Math.min(10, ai3.getCardsCount());
		trans.translate(720 / .15, 80 / .15);
		for(i = 0; i < toDisplay; i++) {
			trans.translate(0, 20 / .15);
			graphics2d.drawImage(card_back, trans, this);
		}
		trans.setToIdentity();

		// Avatar overlay
		Font f = new Font("Roboto", Font.BOLD, 24), f_uno = new Font("Roboto", Font.BOLD, 48);
		graphics2d.setFont(f);

		Image avatar_ai1 = avatarTextureMap.get(ai1.getProfile().getAvatar());
		trans.translate(10, 200);
		trans.scale(.5, .5);
		Rectangle backdrop = new Rectangle(5, 195, 164, 64);
		graphics2d.setColor(new Color(0, 0, 80));
		graphics2d.fill(backdrop);
		graphics2d.draw(backdrop);

		graphics2d.setColor(new Color(255, 255, 255));
		graphics2d.drawString(ai1.getProfile().getUsername(), 70, 220);
		graphics2d.drawString("Lv. " + ai1.getProfile().getLevel(), 70, 245);

		if(ai1.saidUno()) {
			graphics2d.setColor(new Color(229, 189, 26));
			graphics2d.setFont(f_uno);
			graphics2d.drawString("UNO!", 50, 300);
			graphics2d.setFont(f);
		}

		graphics2d.drawImage(avatar_ai1, trans, this);
		trans.setToIdentity();


		Image avatar_ai2 = avatarTextureMap.get(ai2.getProfile().getAvatar());
		trans.translate(300, 10);
		trans.scale(.5, .5);
		backdrop = new Rectangle(295, 5, 164, 64);
		graphics2d.setColor(new Color(0, 0, 80));
		graphics2d.fill(backdrop);
		graphics2d.draw(backdrop);

		graphics2d.setColor(new Color(255, 255, 255));
		graphics2d.drawString(ai2.getProfile().getUsername(), 360, 30);
		graphics2d.drawString("Lv. " + ai2.getProfile().getLevel(), 360, 55);

		if(ai2.saidUno()) {
			graphics2d.setColor(new Color(229, 189, 26));
			graphics2d.setFont(f_uno);
			graphics2d.drawString("UNO!", 330, 125);
			graphics2d.setFont(f);
		}

		graphics2d.drawImage(avatar_ai2, trans, this);
		trans.setToIdentity();


		Image avatar_ai3 = avatarTextureMap.get(ai3.getProfile().getAvatar());
		trans.translate(635, 200);
		trans.scale(.5, .5);
		backdrop = new Rectangle(630, 195, 164, 64);
		graphics2d.setColor(new Color(0, 0, 80));
		graphics2d.fill(backdrop);
		graphics2d.draw(backdrop);

		graphics2d.setColor(new Color(255, 255, 255));
		graphics2d.drawString(ai3.getProfile().getUsername(), 695, 220);
		graphics2d.drawString("Lv. " + ai3.getProfile().getLevel(), 695, 245);

		if(ai3.saidUno()) {
			graphics2d.setColor(new Color(229, 189, 26));
			graphics2d.setFont(f_uno);
			graphics2d.drawString("UNO!", 655, 300);
			graphics2d.setFont(f);
		}

		graphics2d.drawImage(avatar_ai3, trans, this);
		trans.setToIdentity();

		// Human player (bottom)
		if(human.saidUno()) {
			graphics2d.setColor(new Color(229, 189, 26));
			graphics2d.setFont(f_uno);
			graphics2d.drawString("UNO!", 330, 375);
		}

		Card[] cards = human.getCards();
		int x = 0, y = 400;
		trans.scale(.35, .35);
		trans.translate(x, y / .35);

		// Displays player cards and build card regions
		cardRects = new ArrayList<CardRect>();
		for(i = 0; i < cards.length; i++) {
			int deltax = 50;
			x += deltax;
			trans.translate(deltax / .35, 0);
			graphics2d.drawImage(cardTextureMap.get(cards[i]), trans, this);

			Point p = new Point(x, y);
			if(i < cards.length - 1)
				cardRects.add(new CardRect(cards[i], p, deltax, 120));
			else cardRects.add(new CardRect(cards[i], p, 80, 120));
		}

		if(human.hasToPickColor())
			showJollyPicker(graphics2d, lastJolly);
	}

	/**
	 * Displays game over screen
	 * @param g graphics object
	 * @param winner whether human player has won
	 */
	private void gameOverScreen(Graphics g, boolean winner) {
		Graphics2D graphics2d = (Graphics2D)g;
		String top = winner ? "You win!" : "You lose!",
				bottom = "Press \"New game\" to start a new game";

		Font f1 = new Font("Roboto", Font.BOLD, 72),
				f2 = new Font("Roboto", Font.BOLD, 36);
		graphics2d.setColor(new Color(76, 180, 255));
		graphics2d.setFont(f1);
		graphics2d.drawString(top, 220, 100);
		graphics2d.setFont(f2);
		graphics2d.drawString(bottom, 80, 175);
	}

	/**
	 * Shows the jolly color picker
	 * @param g graphics object
	 * @param type type of jolly to display
	 */
	private void showJollyPicker(Graphics g, Card.Type type) {
		Card red = new Card(Card.Color.RED, type), blue = new Card(Card.Color.BLUE, type),
				yellow = new Card(Card.Color.YELLOW, type), green = new Card(Card.Color.GREEN, type);
		Image img_red = cardTextureMap.get(red), img_blue = cardTextureMap.get(blue),
				img_yellow = cardTextureMap.get(yellow), img_green = cardTextureMap.get(green);

		Graphics2D graphics2d = (Graphics2D)g;
		AffineTransform identity = new AffineTransform();
		AffineTransform trans = new AffineTransform();
		trans.setTransform(identity);
		trans.scale(.35, .35);

		trans.translate(200 / .35, 100 / .35);
		graphics2d.drawImage(img_red, trans, this);
		trans.translate(310 / .35, 0);
		graphics2d.drawImage(img_blue, trans, this);
		trans.translate(-310 / .35, 150 / .35);
		graphics2d.drawImage(img_yellow, trans, this);
		trans.translate(310 / .35, 0);
		graphics2d.drawImage(img_green, trans, this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof GameManager && arg == null) {		// Ignore GameManager updates for the audio system
			if(game.getCurrentPlayer() != lastPlayer) {
				Timer playTimer = new Timer(3000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							game.play();
							lastPlayer = game.getCurrentPlayer();
						}
						catch (PlayerNotReadyException e1) {
							// TODO: sound feedback
						}
					}
				});
				playTimer.setRepeats(false);
				playTimer.start();
			}
		}
		else if(o instanceof GameScreenClickListener) {
			HumanPlayer human = (HumanPlayer)game.getPlayer(GameManager.PLAYER_HUMAN);
			Point p = (Point)arg;

			if(game.getCurrentPlayer() == GameManager.PLAYER_HUMAN && human.hasToPlay()) {
				boolean selected = false;
				int i;
				for(i = 0; i < cardRects.size(); i++)
					if(cardRects.get(i).inRegion(p)) {
						selected = true;
						break;
					}
				if(selected) {
					Card played = cardRects.get(i).getCard();
					if(played.getColor() == Card.Color.BLACK)
						lastJolly = cardRects.get(i).getCard().getType();

					try {
						human.playCardByIndex(i, game.getLastPlayed());
						game.play();
					}
					catch(PlayerNotReadyException e) {
						// Won't happen
					}
					catch(IncompatibleCardException e) {
						// TODO: sound feedback
					}
				}

				if(human.hasToPickColor()) {
					Card.Color selectedColor = null;
					for(Card.Color c: jollys.keySet())
						if(jollys.get(c).inRegion(p)) {
							selectedColor = c;
							break;
						}

					if(selectedColor != null) {
						human.pickColor(selectedColor);
						try {
							game.play();
						}
						catch(PlayerNotReadyException e) {
							// Won't happen
						}
					}
				}
			}
		}

		repaint();
		revalidate();
	}
}
