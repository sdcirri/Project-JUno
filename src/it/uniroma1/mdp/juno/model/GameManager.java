package it.uniroma1.mdp.juno.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * This class implements various game mechanics
 * such as card distributing, player turns and
 * victory
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")	// Observable deprecation
public class GameManager extends Observable implements Observer {
	/**
	 * Player IDs: 0 represents human, 1-3 AI
	 */
	public static final int PLAYER_HUMAN = 0, PLAYER_AI1 = 1, PLAYER_AI2 = 2, PLAYER_AI3 = 3;
	/**
	 * Action flags for notifying the audio feedback observer
	 */
	public static final int PLAY_CARD = 0, PLAY_UNO = 1, PLAY_WIN = 2;
	/**
	 * Class instance
	 */
	private static GameManager instance;
	/**
	 * Profile of the human player
	 */
	private PlayerProfile humanProfile;

	/**
	 * Pile of dropped cards
	 */
	private DropPile dropPile;
	/**
	 * Game deck
	 */
	private Deck deck;
	/**
	 * Current and previous player counter
	 */
	private int currentPlayer, previousPlayer;
	/**
	 * Direction of the game (true=right, false=left)
	 */
	private boolean right;
	/**
	 * Whether the game is still running
	 */
	private boolean running;
	/**
	 * Whether next player has to draw cards
	 */
	private boolean nextDraws;
	/**
	 * Players
	 */
	private Player[] players;
	/**
	 * Winner, null if game is in progress
	 */
	private Player winner;

	/**
	 * Initializes the game manager
	 */
	private GameManager() {
		initGame();
	}

	/**
	 * Initiates the game
	 */
	private void initGame() {
		winner = null;
		previousPlayer = -1;
		dropPile = DropPile.getInstance();
		deck = Deck.getInstance();
		players = new Player[] {
				(humanProfile == null) ? null : HumanPlayer.getInstance(humanProfile),
				new AIPlayer(ProfileRandomizer.randomProfile()),
				new AIPlayer(ProfileRandomizer.randomProfile()),
				new AIPlayer(ProfileRandomizer.randomProfile())
		};

		Random rng = new Random();
		Card first = deck.takeCard();
		dropPile.addCard(first);
		right = true;
		currentPlayer = rng.nextInt(4);
	}

	/**
	 * Starts the game manager
	 */
	public void start() {
		reset();
		running = true;
	}

	/**
	 * Stops the game manager
	 */
	public void stop() {
		running = false;
		System.gc();
	}

	/**
	 * Returns the game winner
	 * @return the game winner, null if game is still in progress
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * Sets the human player
	 * @param pp profile of the player
	 */
	public void setHumanPlayer(PlayerProfile pp) {
		humanProfile = pp;
		players[PLAYER_HUMAN] = HumanPlayer.getInstance(humanProfile);
	}

	/**
	 * Gives cards to players ONLY IF all players
	 * have no cards
	 */
	private void giveCards() {
		for(int i = 0; i < 4; i++)
			if(players[i].getCardsCount() != 0)	return;

		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 7; j++) {
				Card toDraw = deck.takeCard();
				players[i].takeCard(toDraw);
			}
	}

	/**
	 * Returns the ID of the current player,
	 * PLAYER_AI* for AIs, PLAYER_HUMAN for human
	 * @return the ID of the current player
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Returns whether the deck is empty
	 * @return true if deck is empty, false otherwise
	 */
	public boolean deckEmpty() {
		return deck.isEmpty();
	}

	/**
	 * Returns the last card played
	 * @return the last card played
	 */
	public Card getLastPlayed() {
		return dropPile.getLastCard();
	}

	/**
	 * Returns the player with the specified ID
	 * (0 for human, 1-3 for AIs)
	 * @param id player ID
	 * @return a reference to the requested player
	 */
	public Player getPlayer(int id) {
		return players[id];
	}

	/**
	 * Returns the player array
	 * @return a deep copy of the player array
	 */
	public Player[] getPlayers() {
		return players.clone();
	}

	/**
	 * Returns the instance of the game manager
	 * @return a reference to the game manager
	 */
	public static GameManager getInstance() {
		if(instance == null)	instance = new GameManager();
		return instance;
	}

	/**
	 * Starts a new game (remember to set the player
	 * profile again!)
	 */
	public void reset() {
		for(Player p: instance.players)
			p.dropAll();
		deck.reset();
		dropPile.getDroppedCards();		// Empties the stack

		initGame();

		players[0].deleteObservers();
		for(Player p: players)
			p.addObserver(this);

		giveCards();
	}

	/**
	 * Updates the current player
	 */
	private void nextPlayer() {
		previousPlayer = currentPlayer;
		currentPlayer = right ? currentPlayer + 1 : currentPlayer - 1;
		if(currentPlayer >= 4)	currentPlayer -= 4;
		if(currentPlayer < 0)	currentPlayer += 4;
	}

	/**
	 * Makes p draw n cards
	 * @param p player that has to draw
	 * @param n number of cards to draw
	 */
	private void drawN(Player p, int n) {
		boolean rebuiltDeck = false;
		for(int i = 0; i < n; i++) {
			setChanged();
			notifyObservers(PLAY_CARD);
			p.takeCard(deck.takeCard());
			if(deck.isEmpty()) {
				deck.fromDrops(dropPile.getDroppedCards());
				rebuiltDeck = true;
			}
		}
		if(rebuiltDeck) {
			Card first = deck.takeCard();
			dropPile.addCard(first);
		}
	}

	/**
	 * Play action
	 * @throws PlayerNotReadyException when the current player is human and not ready yet
	 */
	@SuppressWarnings("incomplete-switch")
	private void playAction() throws PlayerNotReadyException {
		Player p = players[currentPlayer];

		Card lastPlayed = getLastPlayed();
		switch(lastPlayed.getType()) {
			case DRAW2:
				if(nextDraws) {
					drawN(p, 2);
					nextDraws = false;
					break;
				}

			case DRAW4:
				if(nextDraws) {
					Random rng = new Random();
					boolean wantsToChallenge = rng.nextBoolean() && (previousPlayer != -1) && (currentPlayer != PLAYER_HUMAN);

					if(wantsToChallenge) {
						if(PlayStrategy.hasCompatibleExceptDraw4(
								Arrays.asList(players[previousPlayer].getCards()),
								dropPile.getSecondLastCard()
							))
							drawN(players[previousPlayer], 6);
						else drawN(p, 6);
					}
					else drawN(p, 4);
					nextDraws = false;
					break;
				}

			default:
				if(deckEmpty()) {
					deck.fromDrops(dropPile.getDroppedCards());
					Card first = deck.takeCard();
					dropPile.addCard(first);
				}

				Card played = null;
				try {
					played = p.playCard(getLastPlayed());
					if(played == null)
						throw new PlayerNotReadyException();
				}
				catch(NoCompatibleCardsException e) {
					drawN(p, 1);
					if(PlayStrategy.hasCompatible(
							Arrays.asList(p.getCards()),
							dropPile.getLastCard()
						)) {
						try {
							played = p.playCard(getLastPlayed());
						}
						catch(NoCompatibleCardsException e1) {
							// Won't happen
						}
						if(played == null)
							throw new PlayerNotReadyException();
					}
					else return;
				}

				if(p instanceof AIPlayer)
					((AIPlayer)p).checkForUno();

				// Plays sound only after is sure that is a valid play
				setChanged();
				notifyObservers(PLAY_CARD);

				switch(played.getType()) {
					case REVERSE:
						right = !right;
						break;
					case BLOCK:
						nextPlayer();
						break;
					case DRAW2:
						nextDraws = true;
						break;
					case COLORCHANGE:
						played.setColor(p.pickColor());
						break;
					case DRAW4:
						played.setColor(p.pickColor());
						nextDraws = true;
						break;
				}
	
				dropPile.addCard(played);

				if(p.done())
					endGame(p);
		}

		if(p.getCardsCount() == 1 && !p.saidUno())
			drawN(p, 2);
	}

	/**
	 * Ends the game and records the results
	 * in every player profile
	 * @param winner winner
	 */
	private void endGame(Player winner) {
		stop();

		Player[] notWinners = Arrays.stream(players)
				.filter(p -> p != winner)
				.toArray(Player[]::new);

		List<Card> losersCards = new ArrayList<>();

		for(Player p: notWinners) {
			String[] adversaries = Arrays.stream(players)
					.filter(pl -> pl != p)
					.map(pl -> pl.getProfile().getUsername())
					.toArray(String[]::new);
			losersCards.addAll(Arrays.asList(p.dropAll()));

			p.getProfile().addGameRecord(new GameRecord(0, adversaries));
		}

		String[] adversaries = Arrays.stream(notWinners)
				.map(p -> p.getProfile().getUsername())
				.toArray(String[]::new);

		int victoryPoints = losersCards.stream()
				.mapToInt(x -> x.getPoints())
				.sum();

		winner.getProfile().addGameRecord(new GameRecord(victoryPoints, adversaries));
		winner.getProfile().addXp(victoryPoints);
		this.winner = winner;

		if(winner == players[PLAYER_HUMAN]) {
			setChanged();
			notifyObservers(PLAY_WIN);
		}

		try {
			players[PLAYER_HUMAN].getProfile().saveToFile("player.ser");
		} catch (IOException e) {
			System.err.println("An error occured while trying to save user data:");
			e.printStackTrace();
		}
	}

	/**
	 * Makes the current player play a turn
	 * @throws PlayerNotReadyException when the current player is human and not ready yet
	 */
	public void play() throws PlayerNotReadyException {
		if(running) {
			try {
				playAction();
				nextPlayer();
				setChanged();
				notifyObservers();
			}
			catch(PlayerNotReadyException e) {
				setChanged();
				notifyObservers();
				throw new PlayerNotReadyException();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Player) {
			setChanged();					// We already know that the UNO! sound must be played
			notifyObservers(PLAY_UNO);
		}
	}
}
