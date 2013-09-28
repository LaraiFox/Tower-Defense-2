package net.laraifox.tdlwjgl.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.laraifox.tdlwjgl.enums.EnumWaypoint;

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
				tileList.add(new Tile(tileID, true, EnumWaypoint.None));
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
}
