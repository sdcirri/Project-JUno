package it.uniroma1.mdp.juno.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements player profiles
 * @author Sebastiano Deodati
 */
public class PlayerProfile implements Serializable {
	private static final long serialVersionUID = -6865374078675837786L;
	/**
	 * Game history
	 */
	private List<GameRecord> gameHistory;
	/**
	 * Username
	 */
	private String username;
	/**
	 * Path to avatar
	 */
	private Avatar avatar;
	/**
	 * Level and XP
	 */
	private int level = 1, xp;

	/**
	 * Constructs a player profile with the
	 * specified username, default avatar and level 1
	 * @param username username
	 */
	public PlayerProfile(String username) {
		gameHistory = new ArrayList<GameRecord>();
		this.username = username;
		this.avatar = Avatar.AVATAR_SMILE;
	}

	/**
	 * Detects the OS and returns the most appropriate
	 * save path for player data
	 * @return path where to save
	 */
	private static Path getOSSavePath() {
		String osString = System.getProperty("os.name").toLowerCase(),
				homePath = System.getProperty("user.home");
		if (osString.contains("nix") || osString.contains("aix") || osString.contains("nux")){
		    return Paths.get(homePath + "/.local/share/juno/");			// home will be /home/user
		}
		else if (osString.contains("osx")) {
			return Paths.get(homePath + "/Application Support/JUNO/");	// home will be /Users/user
		} else if (osString.contains("win")) {
			return Paths.get(System.getenv("APPDATA") + "\\juno\\");	// Will save under AppData/Roaming
		}
		else return Paths.get(".");
	}

	/**
	 * Saves the profile to a file
	 * @param filename file where to save
	 * @throws IOException when it's somehow impossible to access the save path
	 */
	public void saveToFile(String filename) throws IOException {
		Path savePath = getOSSavePath(), fullpath = savePath.resolve(filename);
		Files.createDirectories(savePath);
		File outFile = new File(fullpath.toString());
		outFile.createNewFile();
		FileOutputStream out = new FileOutputStream(outFile);
		ObjectOutputStream objo = new ObjectOutputStream(out);
		System.out.println("Saving profile with username " + username + " at " + fullpath);
		objo.writeObject(username);
		objo.writeObject(level);
		objo.writeObject(xp);
		objo.writeObject(avatar);
		objo.writeObject(gameHistory);
		objo.close();
	}

	/**
	 * Restores the profile from a file and
	 * then returns it
	 * @param filename file to read
	 * @return restored profile
	 * @throws FileNotFoundException when file is not found
	 */
	@SuppressWarnings("unchecked")
	public static PlayerProfile restoreFromFile(String filename) throws FileNotFoundException {
		Path fullpath = getOSSavePath().resolve(filename);
		String username = null;
		Avatar avatar = null;
		int level = 0, xp = 0;
		List<GameRecord> gameHistory = null;

		try {
			File inFile = new File(fullpath.toString());
			FileInputStream in = new FileInputStream(inFile);
			ObjectInputStream obji = new ObjectInputStream(in);

			username = (String)obji.readObject();
			level = (int)obji.readObject();
			xp = (int)obji.readObject();
			avatar = (Avatar)obji.readObject();
			gameHistory = (List<GameRecord>)obji.readObject();

			obji.close();
		}
		catch (FileNotFoundException e) {
			throw e;
		}
		catch(IOException | ClassNotFoundException e) {
			System.err.println("An error occured while trying to restore the user profile:");
			e.printStackTrace();
			return null;
		}

		PlayerProfile profile = new PlayerProfile(username);
		profile.level = level;
		profile.xp = xp;
		profile.avatar = avatar;
		profile.gameHistory = gameHistory;
		return profile;
	}

	/**
	 * Returns the username of the player as a
	 * copy of the private String object
	 * @return the username of the player
	 */
	public String getUsername() {
		return new String(username);
	}

	/**
	 * Sets the username of the player
	 * @param username the username to assign
	 */
	public void setUsername(String username) {
		this.username = new String(username);
	}

	/**
	 * Returns the ID of the avatar
	 * @return the avatar of the player
	 */
	public Avatar getAvatar() {
		// TODO: revise return type
		return avatar;
	}

	/**
	 * Sets the player avatar from path
	 * @param avatar path to the avatar image
	 */
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	/**
	 * Returns the current level
	 * @return the current level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Returns the current experience points
	 * @return the current experience points
	 */
	public int getXp() {
		return xp;
	}

	/**
	 * Adds the specified amount of experience to
	 * the player and eventually levels up
	 * @param amount amount of experience to add
	 */
	public void addXp(int amount) {
		xp += amount;

		while(xp >= 100 * level) {
			xp -= 100 * level;
			level += 1;
		}
	}

	/**
	 * Returns the history of played games
	 * @return an array of game records
	 */
	public GameRecord[] getGameHistory() {
		return gameHistory.toArray(GameRecord[]::new);
	}

	/**
	 * Adds a record to the game history
	 * @param r record to add
	 */
	public void addGameRecord(GameRecord r) {
		gameHistory.add(r);
	}

	/**
	 * Returns the number of total victories
	 * @return the number of total victories
	 */
	public long getVictories() {
		return gameHistory.stream()
				.filter(r -> r.getVictoryPoints() > 0)
				.count();
	}

	/**
	 * Returns the number of total losses
	 * @return the number of total losses
	 */
	public long getLosses() {
		return gameHistory.stream()
				.filter(r -> r.getVictoryPoints() == 0)
				.count();
	}

	/**
	 * Returns the number of total games played
	 * @return the number of total games played
	 */
	public long getTotalGames() {
		return gameHistory.size();
	}
}
