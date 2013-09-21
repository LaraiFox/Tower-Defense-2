package net.laraifox.tdlwjgl.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.laraifox.tdlwjgl.enums.EnumFramerateLimit;
import net.laraifox.tdlwjgl.enums.EnumWindowResolution;

/**
 * <Summary> Settings Class <Summary>
 * 
 * The settings class will be a single static object that contains the main settings for the game. Most of these settings can be changed from the
 * options menu and some will be a part of the setting file however, the user can still change them if they know what they're doing.
 * 
 * This class must contain methods for saving and loading setting to and from a file the pathname of which will likely be stored as a private static
 * final String object in this class. There might be a method for retrieving the pathname if necessary however no outside class should be able to
 * change the value of the pathname.
 * 
 * If the pathname contains a null value then an exception is thrown when trying to load or save the settings.
 * 
 */
public class Settings {
	private static final String PROGRAM_FOLDER = "/Tower Defense";
	private static final String FILE_NAME = "/setting.cfg";

	private static String programDirectory = "";

	public static EnumWindowResolution resolution;
	public static EnumFramerateLimit framerateLimit;

	public static void loadSettings() throws Exception {
		if (programDirectory != null) {
			/*
			 * Load all the necessary settings from the settings file located under the directory stored in the constant "PATHNAME" and place them
			 * into their appropriate variables.
			 */

			File programFolder = new File(programDirectory);
			if (!programFolder.exists())
				programFolder.mkdir();

			File settingsFile = new File(programDirectory + FILE_NAME);
			if (!settingsFile.exists()) {
				settingsFile.createNewFile();
				createDefaultSettingsFile();
			}
		} else {
			throw new Exception();
		}
	}

	public static void saveSettings() throws Exception {
		if (programDirectory != null) {
			/*
			 * Save all the settings stored in the variables located in this class to the file under the directory stored in the constant "PATHNAME"
			 * using the appropriate format.
			 */

			File settingsFile = new File(programDirectory + FILE_NAME);

			String newLine = System.getProperty("line.separator");
			BufferedWriter writer = new BufferedWriter(new FileWriter(settingsFile));
			writer.write("// Tower Defense configuration file");
			writer.append(newLine + newLine);

			writer.append("resolution:" + resolution.name().toLowerCase() + newLine);
			writer.append("fps-limit:" + framerateLimit.name().toLowerCase() + newLine);

			writer.close();
		} else {
			throw new Exception();
		}
	}

	private static void createDefaultSettingsFile() throws Exception {
		resolution = EnumWindowResolution.Normal;
		framerateLimit = EnumFramerateLimit.Standard;

		saveSettings();
	}

	public static void setProgramDirectory(String directory) {
		programDirectory = directory;
	}

	public static String getProgramFolder() {
		return PROGRAM_FOLDER;
	}

	public static String getFileName() {
		return FILE_NAME;
	}
}
