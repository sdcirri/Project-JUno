package it.uniroma1.mdp.juno.view;

import java.util.Observable;

import it.uniroma1.mdp.juno.view.Frame.Panel;

/**
 * This class implements the event that
 * is triggered by menu navigation
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class MenuNavigationEvent extends Observable {
	/**
	 * Notifies the frame to show the
	 * desired panel
	 * @param dest tag of the panel to show
	 */
	public void changeMenu(Panel dest) {
		setChanged();
		notifyObservers(dest);
	}
}
