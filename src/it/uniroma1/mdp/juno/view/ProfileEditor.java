package it.uniroma1.mdp.juno.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import it.uniroma1.mdp.juno.model.PlayerProfile;
import it.uniroma1.mdp.juno.view.Frame.Panel;

/**
 * This class implements the profile editor form
 * @author Sebastiano Deodati
 */
public class ProfileEditor extends NavigationPanel {
	private static final long serialVersionUID = 1187145073534360578L;

	/**
	 * Text field for username editing
	 */
	private JTextField textField_username;
	/**
	 * Labels to show player info
	 */
	private JLabel label_username, label_level, label_games, label_victories, label_losses;
	/**
	 * Button to apply changes
	 */
	private JButton button_apply;
	/**
	 * Button to edit avatar
	 */
	private JButton button_avatar;
	/**
	 * Player profile to edit
	 */
	private PlayerProfile pp;

	/**
	 * Prepares the avatar for display
	 * @return avatar as an ImageIcon
	 */
	private ImageIcon mkAvatar() {
		Image avatar = AvatarTextureMap.getMap().get(pp.getAvatar());
		return new ImageIcon(avatar);
	}

	/**
	 * Constructs the form
	 * @param pp player profile to edit
	 */
	public ProfileEditor(PlayerProfile pp) {
		this.pp = pp;

		setPreferredSize(new Dimension(800, 600));
		setLayout(new GridBagLayout());

		textField_username = new JTextField(20);
		label_username = new JLabel("Username:");
		label_level = new JLabel("Current level:");
		label_games = new JLabel("Total games:");
		label_victories = new JLabel("Won:");
		label_losses = new JLabel("Lost:");
		button_apply = new JButton("Apply changes");
		button_apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String newname = textField_username.getText();
				pp.setUsername(newname);
				try {
					pp.saveToFile("player.ser");
				} catch (IOException ex) {
					// We really do not expect this as it's handled previously
					ex.printStackTrace();
				}
				nav.changeMenu(Panel.MAINMENU);
			}
		});
		button_avatar = new JButton(mkAvatar());
		button_avatar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nav.changeMenu(Panel.AVATAR);
			}
		});

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 20, 0);
		add(button_avatar, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(label_username, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(textField_username, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(label_level, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(label_games, gridBagConstraints);

		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(label_victories, gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(label_losses, gridBagConstraints);

		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.insets = new Insets(20, 0, 0, 0);
		add(button_apply, gridBagConstraints);

		setPlayerInfo();
	}

	/**
	 * Sets the player info to display
	 */
	public void setPlayerInfo() {
		textField_username.setText(pp.getUsername());
		label_games.setText("Total games: " + pp.getTotalGames());
		label_victories.setText("Won: " + pp.getVictories());
		label_losses.setText("Lost: " + pp.getLosses());
		label_level.setText("Current level: " + pp.getLevel());
	}
}
