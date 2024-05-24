package it.uniroma1.mdp.juno.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import it.uniroma1.mdp.juno.model.Avatar;
import it.uniroma1.mdp.juno.model.PlayerProfile;
import it.uniroma1.mdp.juno.view.Frame.Panel;

/**
 * This class implements the avatar picker
 * @author Sebastiano Deodati
 */
public class AvatarPicker extends NavigationPanel {
	private static final long serialVersionUID = -3110233206829962125L;
	/**
	 * Avatar texture map
	 */
	private Map<Avatar, Image> map;
	/**
	 * Buttons to select avatars
	 */
	private JButton[] buttons_avatars;
	/**
	 * Reference to the player profile
	 */
	private PlayerProfile pp;

	/**
	 * Configures the button for the given avatar
	 * @param a avatar ID
	 * @return JButton for selecting avatar
	 */
	private JButton mkButton(Avatar a) {
		ImageIcon icon = new ImageIcon(map.get(a));
		JButton button = new JButton(icon);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pp.setAvatar(a);
				pp.saveToFile("player.ser");
				nav.changeMenu(Panel.PROFILE);
			}
		});
		return button;
	}

	/**
	 * Constructs the avatar picker
	 * @param pp player profile
	 */
	public AvatarPicker(PlayerProfile pp) {
		setPreferredSize(new Dimension(800, 600));
		setLayout(new GridBagLayout());

		this.pp = pp;
		buttons_avatars = new JButton[12];
		map = AvatarTextureMap.getMap();
		Avatar[] avatars = Avatar.values();
		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		for(int i = 0; i < 12; i++) {
			buttons_avatars[i] = mkButton(avatars[i]);

			gridBagConstraints.gridx = i % 6;
			gridBagConstraints.gridy = (i < 6) ? 0 : 1;
			gridBagConstraints.weightx = 0;
			gridBagConstraints.weighty = 0;
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
			add(buttons_avatars[i], gridBagConstraints);
		}

		setVisible(true);
	}
}
