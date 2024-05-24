package it.uniroma1.mdp.juno.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import it.uniroma1.mdp.juno.view.Frame.Panel;

/**
 * This class implements the main menu
 * @author Sebastiano Deodati
 */
public class MainMenu extends NavigationPanel {
	private static final long serialVersionUID = -8389351543512012398L;

	/**
	 * Buttons to navigate across the
	 * various panels
	 */
	private JButton newgameButton, profileButton, exitButton;
	/**
	 * Label to show the logo
	 */
	private JLabel logoLabel;
	/**
	 * Logo image
	 */
	private ImageIcon logo;

	/**
	 * Method to setup image for the logo
	 */
	private void setLogo() {
		logo = new ImageIcon(MainMenu.class.getResource("/res/uno_logo.png"));
		Image img = logo.getImage();
		logo.setImage(img.getScaledInstance(300, 200, 0));
	}

	/**
	 * Constructs the menu
	 */
	public MainMenu() {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new GridBagLayout());

		setLogo();
		logoLabel = new JLabel(logo);
		logoLabel.setPreferredSize(new Dimension(300, 200));

		newgameButton = new JButton("New game");
		newgameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nav.changeMenu(Panel.GAME);
			}
		});
		profileButton = new JButton("Edit profile");
		profileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nav.changeMenu(Panel.PROFILE);
			}
		});
		exitButton = new JButton("Quit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(logoLabel, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(newgameButton, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(profileButton, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		add(exitButton, gridBagConstraints);
	}
}
