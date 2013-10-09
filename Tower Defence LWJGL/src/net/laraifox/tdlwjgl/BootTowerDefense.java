package net.laraifox.tdlwjgl;

import java.io.File;
import java.io.IOException;

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
	private static final int DEFAULT_ORTHO_WIDTH = 1600;
	private static final int DEFAULT_ORTHO_HEIGHT = 900;

	public static void main(String[] args) {
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
			System.err.println("Your Operating System (" + operatingSystem + ") is unrecognised or unsupported");
			new Exception().printStackTrace();
			System.exit(1);
		}

		try {
			Settings.initializeProgramDirectories();
			Settings.loadSettings();

			TowerDefenseGame programDisplay = new TowerDefenseGame(Settings.getWidth(), Settings.getHeight());
			// programDisplay.setOrtho(0, DEFAULT_ORTHO_WIDTH, 0, DEFAULT_ORTHO_HEIGHT, -1, 1);
			programDisplay.intitialize();
			programDisplay.start();

			Settings.saveSettings();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
