package net.laraifox.tdlwjgl.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.laraifox.tdlwjgl.main.Settings;

public class LevelFormatter {
	public static Level loadLevel(String levelFileName) throws FileNotFoundException {
		LevelTemplate level = new LevelTemplate();
		FileInputStream inputStream = new FileInputStream(new File(levelFileName));
		Scanner scanner = new Scanner(inputStream, "UTF-8");

		do {
			String line = getNextLine(scanner);
			if (line.charAt(0) == '#') {
				if (line.substring(1).contains("TileData")) {
					loadTileData(level, scanner);
				} else if (line.substring(1).contains("WaypointData")) {
					loadWaypointData(level, scanner);
				} else if (line.substring(1).contains("EntityData")) {
					loadEntityData(level, scanner);
				}
			}
		} while (scanner.hasNextLine());
		scanner.close();
		return level;
	}

	private static void loadTileData(LevelTemplate level, Scanner scanner) {
		List<Tile> tileList = new ArrayList<Tile>();
		int width = 0;
		int height = 0;

		String line = getNextLine(scanner);
		while (line.charAt(0) != '}') {
			String[] splitLine = line.split(" ");
			boolean isValidLine = false;
			for (int i = 0; i < splitLine.length; i++) {
				if (splitLine[i].equalsIgnoreCase(""))
					continue;

				int tileID = Integer.parseInt(splitLine[i], 16);
				tileList.add(new Tile(tileID));
				isValidLine = true;
			}
			line = getNextLine(scanner);
			if (isValidLine)
				height++;
		}

		width = tileList.size() / height;
		level.setLevelSize(width, height);
		for (int i = 0; i < tileList.size(); i++) {
			level.setTileAt(i % width, (height - 1) - i / width, tileList.get(i));
		}
	}

	private static void loadWaypointData(LevelTemplate level, Scanner scanner) {
		List<WaypointList> waypointsLists = new ArrayList<WaypointList>();

		String line = getNextLine(scanner);
		while (line.charAt(0) != '}') {
			WaypointList newList = new WaypointList();
			String[] splitLine = line.split(" ");
			for (int i = 0; i < splitLine.length; i++) {
				if (splitLine[i].equalsIgnoreCase(""))
					continue;

				String[] data = splitLine[i].split(":");
				if (data.length < 3)
					continue;

				int x = Integer.parseInt(data[0]);
				int y = Integer.parseInt(data[1]);
				int direction = Integer.parseInt(data[2]);
				newList.addWaypoint(new Waypoint(x, y, direction));
			}
			waypointsLists.add(newList);
			line = getNextLine(scanner);
		}

		level.setWaypointData(waypointsLists);
	}

	private static void loadEntityData(LevelTemplate level, Scanner scanner) {
		String line = getNextLine(scanner);
		while (line.charAt(0) != '}') {
			String[] splitLine = line.split(" ");
			for (int i = 0; i < splitLine.length; i++) {
				if (splitLine[i].equalsIgnoreCase(""))
					continue;

				String[] data = splitLine[i].split(":");
				if (data.length < 4)
					continue;

				int type = Integer.parseInt(data[0], 16);
				int length = Integer.parseInt(data[1]);
				int delay = Integer.parseInt(data[2]);
				int spawnpoint = Integer.parseInt(data[3]);
				int spawnrate = (data.length < 5 ? 10 : Integer.parseInt(data[4]));
				level.addWave(type, length, delay, spawnpoint, spawnrate);
			}
			line = getNextLine(scanner);
		}
	}

	private static String getNextLine(Scanner scanner) {
		String line = scanner.nextLine();
		while (scanner.hasNextLine() && (line.length() <= 0 || line.charAt(0) == '\n' || line.charAt(0) == '/')) {
			line = getNextLine(scanner);
		}

		return line;
	}

	private static class LevelTemplate extends Level {
		public void setLevelSize(int width, int height) {
			this.tiles = new Tile[width * height];
			this.width = width;
			this.height = height;
		}

		public void setTileAt(int i, int j, Tile tile) {
			this.tiles[i + j * width] = tile;
		}

		public void addWave(int type, int length, int delay, int spawnpoint, int spawnrate) {
			this.waveManager.addWave(type, length, delay, spawnpoint, spawnrate);
		}

		public void setWaypointData(List<WaypointList> waypointLists) {
			this.waypointLists = waypointLists;
		}
	}

	public static void formatAndSave(String fileName, String title, String[] comments, Tile[] tiles, int width, int height, WaveManager waveManager, List<WaypointList> waypointLists) throws IOException {
		File file = new File(Settings.getProgramDirectory() + "/levels/" + fileName + ".txt");
		if (!file.exists())
			file.createNewFile();
		FileWriter writer = new FileWriter(file);

		String newline = System.getProperty("line.separator");
		String newblock = newline + newline + newline + newline;

		writer.append("");
		writer.append("//--------------------------------------------------------------------------------------------//" + newline);
		writer.append("//                - " + title + " - " + new String("                                                                ").substring(0, 71 - title.length()) + "//" + newline);

		// Add comment support here!

		writer.append("//--------------------------------------------------------------------------------------------//" + newblock);

		writer.append("// Level tile data encoded using a two digit hexadecimal number per tile." + newline);
		writer.append("#TileData {" + newline);
		for (int j = height - 1; j >= 0; j--) {
			writer.append("   ");
			for (int i = 0; i < width; i++) {
				String tileID = Integer.toHexString(tiles[i + j * width].getTileID());
				if (tileID.length() < 2)
					writer.append("0");
				writer.append(tileID + " ");
			}
			writer.append(newline);
		}
		writer.append("}" + newblock);

		writer.append("// List of waypoint location lists for entities using the format [ x:y:direction ]." + newline);
		writer.append("// X and Y positions start at 00:00 being the bottom left corner. Waypoint position data is encoded using a decimal number." + newline);
		writer.append("// Direction turns clockwise starting at 0 facing right. A direction of 4 is used to show the end location." + newline);
		writer.append("#WaypointData {" + newline);
		for (int j = 0; j < waypointLists.size(); j--) {
			writer.append("   ");
			for (int i = 0; i < waypointLists.get(j).getLength(); i++) {
				Waypoint waypoint = waypointLists.get(j).getWaypoint(i);
				String x = (waypoint.getX() < 10 ? " " : "") + Integer.toString(waypoint.getX());
				String y = (waypoint.getY() < 10 ? " " : "") + ":" + Integer.toString(waypoint.getY());
				String direction = ":" + Integer.toString((waypoint.getDirection().ordinal() == 0 ? 4 : waypoint.getDirection().ordinal() - 1));
				writer.append(x + y + direction + " ");
			}
			writer.append(newline);
		}
		writer.append("}" + newblock);

		writer.append("// List of entity waves for the level using the format [ type:length:delay:spawnpoint(:spawnrate) ]." + newline);
		writer.append("// Entity type is a value representing the entity's ID. Entity type data is encoded using a hexadecimal number." + newline);
		writer.append("// Wave length indicates the number of entities to spawn before the wave ends using a decimal number." + newline);
		writer.append("// Delay represents the time to wait before starting the wave in seconds using a decimal number." + newline);
		writer.append("// Spawnpoint is only used when there are more that one spawnpoint available." + newline);
		writer.append("// Spawnrate is the amount of time in deciseconds between entity spawns. (+1 decisecond is added on automatically)" + newline);
		writer.append("#EntityData {" + newline);
		if (waveManager.getWaveCount() > 0) {
			writer.append("   ");
			for (int i = 0; i < waveManager.getWaveCount(); i++) {
				Wave wave = waveManager.getWaveAt(i);
				String type = (wave.getEntityID() < 16 ? "0" : "") + Integer.toHexString(wave.getEntityID());
				String length = (wave.getLength() < 10 ? "0" : "") + ":" + Integer.toString(wave.getLength());
				String delay = (wave.getDelay() < 10 ? " " : "") + ":" + Integer.toString(wave.getDelay());
				String spawnpoint = ":" + Integer.toString(wave.getSpawnpoint());
				String spawnrate = (wave.getSpawnpoint() == 60 ? "" : ":" + Integer.toString(wave.getSpawnpoint()));
				writer.append(type + length + delay + spawnpoint + spawnrate + " ");
			}
			writer.append(newline);
		}
		writer.append("}" + newblock);

		writer.append("//--------------------------------------------------------------------------------------------//" + newline);
		writer.append("//      - End Of Level Data File -                                                            //" + newline);
		writer.append("//--------------------------------------------------------------------------------------------//");
		writer.close();
	}
}
