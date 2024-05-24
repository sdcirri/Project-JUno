package it.uniroma1.mdp.juno.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import it.uniroma1.mdp.juno.model.GameManager;
import it.uniroma1.mdp.juno.model.HumanPlayer;
import it.uniroma1.mdp.juno.model.PlayStrategy;
import it.uniroma1.mdp.juno.model.PlayerNotReadyException;

/**
 * This class implements the game screen
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class GameScreen extends NavigationPanel implements Observer {
	private static final long serialVersionUID = 5863451094073941838L;
	/**
	 * Reference to game manager
	 */
	private GameManager game;
	/**
	 * Drawing area for game elements
	 */
	private GameScreenDraw drawArea;
	/**
	 * Top toolbar
	 */
	private GameToolbar toolbar;
	/**
	 * Event triggered by game reset
	 */
	private GameResetEvent gameResetEvent;
	/**
	 * Button to press to say UNO
	 */
	private JButton button_uno;

	/**
	 * Constructs the game screen
	 */
	public GameScreen() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout());

		toolbar = new GameToolbar();
		gameResetEvent = toolbar.getGameResetEvent();
		gameResetEvent.addObserver(this);

		drawArea = new GameScreenDraw();
		gameResetEvent.addObserver(drawArea);
		game = GameManager.getInstance();
		game.addObserver(this);

		button_uno = new JButton("UNO!");
		button_uno.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.getPlayer(GameManager.PLAYER_HUMAN).uno();
			}
		});
		button_uno.setEnabled(false);

		add(toolbar, BorderLayout.PAGE_START);
		add(drawArea, BorderLayout.CENTER);
		add(button_uno, BorderLayout.PAGE_END);
	}

	/**
	 * Starts the game
	 */
	public void startGame() {
		game.start();
		try {
			game.play();
		} catch (PlayerNotReadyException e) {
			// TODO: Check if handling is necessary
		}
	}

	/**
	 * Stops the game
	 */
	public void stopGame() {
		game.stop();
	}

	/**
	 * Returns the navigation event
	 * @return the navigation event
	 */
	@Override
	public MenuNavigationEvent getNavigationEvent() {
		return toolbar.getNavigationEvent();
	}

	@Override
	public void update(Observable o, Object arg) {
		// Whoever it is, the screen must be updated)
		HumanPlayer human = (HumanPlayer)game.getPlayer(GameManager.PLAYER_HUMAN);
		button_uno.setEnabled(
				game.getCurrentPlayer() == GameManager.PLAYER_HUMAN &&
				PlayStrategy.hasCompatible(Arrays.asList(human.getCards()), game.getLastPlayed()) &&
				human.hasToPlay() &&
				!human.saidUno() &&
				(human.getCardsCount() == 2)
		);
		repaint();
		revalidate();

		if(o instanceof GameResetEvent) {
			boolean running = (boolean)arg;
			game.reset();
			stopGame();
			if(running)		startGame();
		}
	}
}
