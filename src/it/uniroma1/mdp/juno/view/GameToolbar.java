package it.uniroma1.mdp.juno.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import it.uniroma1.mdp.juno.view.Frame.Panel;

/**
 * This class implements the toolbar shown
 * above the game screen
 * @author Sebastiano Deodati
 */
public class GameToolbar extends NavigationPanel {
	private static final long serialVersionUID = 2583178031026048408L;

	/**
	 * Button to start a new game
	 */
	private JButton button_newgame;
	/**
	 * Button to go back to main menu
	 */
	private JButton button_mainmenu;
	/**
	 * Event triggered by game reset
	 */
	private GameResetEvent gameReset;

	/**
	 * Constructs the toolbar
	 */
	public GameToolbar() {
		gameReset = new GameResetEvent();
		setLayout(new FlowLayout(FlowLayout.LEFT));

		button_newgame = new JButton("New game");
		button_newgame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameReset.reset(true);
			}
		});
		button_mainmenu = new JButton("Quit");
		button_mainmenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nav.changeMenu(Panel.MAINMENU);
				gameReset.reset(false);
			}
		});

		add(button_newgame);
		add(button_mainmenu);
	}

	/**
	 * Returns an event triggered by a
	 * game reset request
	 * @return the game reset event for this instance
	 */
	public GameResetEvent getGameResetEvent() {
		return gameReset;
	}
}
