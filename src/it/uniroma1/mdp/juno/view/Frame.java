package it.uniroma1.mdp.juno.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import it.uniroma1.mdp.juno.model.PlayerProfile;

/**
 * This class implements the main application frame
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class Frame extends JFrame implements Observer {
	private static final long serialVersionUID = -4861954461917751656L;
	/**
	 * Currently displayed panel
	 */
	private NavigationPanel currentPanel;
	/**
	 * Player profile
	 */
	private PlayerProfile profile;

	/**
	 * Enumeration for displayable panels
	 * @author Sebastiano Deodati
	 */
	public static enum Panel {
		MAINMENU, GAME, PROFILE, AVATAR
	}

	/**
	 * Constructs the main window
	 * @param profile player profile
	 */
	public Frame(PlayerProfile profile) {
		super("Project JUNO");
		setSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		this.profile = profile;
		setCurrentPanel(Panel.MAINMENU);
		add(currentPanel, BorderLayout.CENTER);

		setVisible(true);
	}

	/**
	 * Sets the current panel to display
	 * @param toSet type of panel to set
	 */
	private void setCurrentPanel(Panel toSet) {
		setVisible(false);
		if(currentPanel != null)
			remove(currentPanel);

		switch(toSet) {
			case MAINMENU:
				currentPanel = new MainMenu();
				currentPanel.getNavigationEvent().addObserver(this);
				break;
			case GAME:
				currentPanel = new GameScreen();
				currentPanel.getNavigationEvent().addObserver(this);
				((GameScreen)currentPanel).startGame();
				break;
			case PROFILE:
				currentPanel = new ProfileEditor(profile);
				currentPanel.getNavigationEvent().addObserver(this);
				break;
			case AVATAR:
				currentPanel = new AvatarPicker(profile);
				currentPanel.getNavigationEvent().addObserver(this);
				break;
		}

		add(currentPanel, BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof MenuNavigationEvent) {
			Panel dest = (Panel)arg;
			setCurrentPanel(dest);
		}
	}
}
