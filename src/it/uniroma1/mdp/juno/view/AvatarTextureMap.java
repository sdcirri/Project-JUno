package it.uniroma1.mdp.juno.view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import it.uniroma1.mdp.juno.model.Avatar;

/**
 * This class implements the avatar texture map
 * @author Sebastiano Deodati
 */
public class AvatarTextureMap {
	/**
	 * Size (in pixels) of the avatars
	 */
	private static final int AVATAR_SIZE = 107;
	/**
	 * Image containing the map
	 */
	private static BufferedImage map;
	/**
	 * Texture map
	 */
	private static Map<Avatar, Image> avatars;

	/**
	 * Prevents the class from
	 * being instantiated
	 */
	private AvatarTextureMap() {

	}

	/**
	 * Builds the map
	 */
	private static void buildMap() {
		avatars = new HashMap<Avatar, Image>();
		List<Image> textures = new ArrayList<>();

		try {
			map = ImageIO.read(AvatarTextureMap.class.getResource("/res/avatars.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't locate resource! Check build environment");
		}

		for(int i = 0; i < 2; i++)
			for(int j = 0; j < 6; j++)
				textures.add(map.getSubimage(
						j * AVATAR_SIZE,
						i * AVATAR_SIZE,
						AVATAR_SIZE,
						AVATAR_SIZE
				));

		Avatar[] avatarIDs = Avatar.values();

		for(int i = 0; i < 12; i++)
			avatars.put(avatarIDs[i], textures.get(i));
	}

	/**
	 * Returns the map
	 * @return the texture map as HashMap
	 */
	public static Map<Avatar, Image> getMap() {
		if(avatars == null)		buildMap();
		return avatars;
	}
}
