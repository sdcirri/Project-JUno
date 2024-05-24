package it.uniroma1.mdp.juno.model;

import java.util.Random;

/**
 * This class implements a random
 * profile generator
 * @author Sebastiano Deodati
 */
public class ProfileRandomizer {
	/**
	 * Random names
	 */
	private static final String[] usernames = new String[] {
			"Oliver",
			"Jack",
			"Harry",
			"Jacob",
			"Charlie",
			"Thomas",
			"George",
			"Oscar",
			"James",
			"William",
			"Jake",
			"Connor",
			"Callum",
			"Kyle",
			"Joe",
			"Charlie",
			"Damian",
			"Noah",
			"Liam",
			"Mason",
			"Ethan",
			"Michael",
			"Alexander",
			"Daniel",
			"John",
			"Robert",
			"David",
			"Richard",
			"Joseph",
			"Charles",
			"Thomas",
			"Amelia",
			"Olivia",
			"Isla",
			"Emily",
			"Poppy",
			"Mia",
			"Isabella",
			"Jessica",
			"Lily",
			"Sophie",
			"Margaret",
			"Samantha",
			"Bethany",
			"Elizabeth",
			"Joanne",
			"Megan",
			"Victoria",
			"Lauren",
			"Michelle",
			"Tracy",
			"Emma",
			"Sophia",
			"Charlotte",
			"Mary",
			"Patricia",
			"Jennifer",
			"Linda",
			"Barbara",
			"Susan",
			"Sarah"
	};
	/**
	 * Random generator
	 */
	private static Random rng;

	/**
	 * Prevents instantiation
	 */
	private ProfileRandomizer() {

	}

	/**
	 * Generates a random player profile
	 * @return a random player profile
	 */
	public static PlayerProfile randomProfile() {
		if(rng == null)		rng = new Random();

		String username = usernames[rng.nextInt(usernames.length)];
		Avatar[] avatars = Avatar.values();
		Avatar avatar = avatars[rng.nextInt(avatars.length)];
		int xp = rng.nextInt(100000);
		PlayerProfile random = new PlayerProfile(username);
		random.addXp(xp);
		random.setAvatar(avatar);
		return random;
	}
}
