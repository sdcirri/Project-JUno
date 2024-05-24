package it.uniroma1.mdp.juno.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;

/**
 * This class implements the listener for
 * player clicks on the game screen
 * @author Sebastiano Deodati
 */
@SuppressWarnings("deprecation")
public class GameScreenClickListener extends Observable implements MouseListener {

	// Only notify observers when a left click is detected
	@Override
	public void mouseClicked(MouseEvent e) {
		Point p = new Point(e.getX(), e.getY());
		if(e.getButton() == MouseEvent.BUTTON1) {
			setChanged();
			notifyObservers(p);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
