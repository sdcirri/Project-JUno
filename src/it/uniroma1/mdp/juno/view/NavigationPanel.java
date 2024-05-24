package it.uniroma1.mdp.juno.view;

import javax.swing.JPanel;

/**
 * This class implements a JPanel that
 * has a built in MenuNavigationEvent
 * @author Sebastiano Deodati
 */
public class NavigationPanel extends JPanel {
	private static final long serialVersionUID = 3801169000989831355L;
	/**
	 * Event triggered by navigation actions
	 */
	protected MenuNavigationEvent nav;

	/**
	 * Constructs the NavigationPanel
	 */
	public NavigationPanel() {
		nav = new MenuNavigationEvent();
	}

	/**
	 * Returns the navigation event
	 * @return the navigation event
	 */
	public MenuNavigationEvent getNavigationEvent() {
		return nav;
	}
}
