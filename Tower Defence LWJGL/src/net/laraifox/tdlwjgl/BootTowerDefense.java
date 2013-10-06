package net.laraifox.tdlwjgl;

import java.io.File;

import net.laraifox.tdlwjgl.main.TowerDefenseGame;
import net.laraifox.tdlwjgl.main.Settings;

/**
 * 
 * @author Larai Fox
 * 
 *         The Boot class is used to contain the main method and to load and save any settings or configuration files.
 * 
 */
public class BootTowerDefense {
	public static void main(String[] args) throws Exception {
		String operatingSystem = System.getProperty("os.name").toLowerCase();

		String username = System.getProperty("user.name");
		String programFolder = Settings.getProgramFolderName();

		if (operatingSystem.contains("win")) {
			System.setProperty("org.lwjgl.librarypath", new File("res/natives/windows").getAbsolutePath());

			Settings.setProgramDirectory("/Users/" + username + "/AppData/Roaming/" + programFolder);
		} else if (operatingSystem.contains("mac")) {
			System.setProperty("org.lwjgl.librarypath", new File("res/natives/macosx").getAbsolutePath());
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Tower Defense");

			Settings.setProgramDirectory("/Users/" + username + "/Library/" + programFolder);
		} else if (operatingSystem.contains("linux")) {
			System.setProperty("org.lwjgl.librarypath", new File("res/natives/linux").getAbsolutePath());

			Settings.setProgramDirectory("/home/" + username + programFolder);
		} else {
			throw new Exception("Your Operating System (" + operatingSystem + ") is unrecognised or unsupported");
		}

		try {
			Settings.loadSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}

		TowerDefenseGame game = new TowerDefenseGame();
		game.intitialize();
		game.start();

		try {
			Settings.saveSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
