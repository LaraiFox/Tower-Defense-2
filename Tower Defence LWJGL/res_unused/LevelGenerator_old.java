package net.laraifox.tdlwjgl.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import net.laraifox.tdlwjgl.enums.EnumDirection;
import net.laraifox.tdlwjgl.enums.EnumEntityType;
import net.laraifox.tdlwjgl.enums.EnumWaypoint;

public class LevelGenerator {
	/**
	 * 
	 * @param fileName
	 * @param random
	 * @param getLevelWaves
	 */
	public static Level generateLevelFrom(String fileName, Random random, boolean getLevelWaves) {
		LevelTemplate level = new LevelTemplate();
		WaveManager waveManager = new WaveManager(960);
		
		FileInputStream in = null;
		try {
			in = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (in == null)
			return null;

		Scanner scanner = new Scanner(in, "UTF-8");

		generateTiles(level, waveManager, scanner);

		if (getLevelWaves)
			generateWavesFrom(waveManager, scanner);
		else
			generateRandomWaves(waveManager, random);

		level.setWaveManager(waveManager);
		
		scanner.close();

		return level;
	}

	private static void generateTiles(LevelTemplate level, WaveManager waveManager, Scanner scanner) {
		String line = getNextLine(scanner);

		short waypointIndex = 0;
		byte[] waypoints = new byte[line.length()];

		for (int i = 0; i < waypoints.length; i++) {
			waypoints[i] = Byte.parseByte(String.valueOf(line.charAt(i)));
		}

		level.setLevelSize(Level.DEFAULT_WIDTH, Level.DEFAULT_HEIGHT);

		for (byte y = Level.DEFAULT_HEIGHT - 1; y >= 0; y--) {
			line = getNextLine(scanner);

			for (byte x = 0; x < Level.DEFAULT_WIDTH; x++) {
				byte xx = (byte) (x * 2);

				byte i = (byte) Integer.parseInt(line.substring(xx, xx + 2), 16);

				EnumWaypoint waypoint = EnumWaypoint.None;
				boolean canPlaceTower = true;

				if (waypointIndex >= waypoints.length)
					waypointIndex = 0;

				switch (i) {
				case 0x0d:
					canPlaceTower = false;
					break;
				case 0x0e:
					canPlaceTower = false;
					break;
				case 0x0f:
					canPlaceTower = false;
					break;
				case 0x10:
					canPlaceTower = false;
					break;
				case 0x11:
					waypoint = (waypoints[waypointIndex] == 0 ? EnumWaypoint.Left : waypoints[waypointIndex] == 1 ? EnumWaypoint.Right : EnumWaypoint.Back);
					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x12:
					waypoint = (waypoints[waypointIndex] == 0 ? EnumWaypoint.Left : waypoints[waypointIndex] == 1 ? EnumWaypoint.Right : EnumWaypoint.Back);
					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x13:
					if (waypoints[waypointIndex] == 0) {
						waypoint = EnumWaypoint.Start;
						waveManager.setStartingDirection(EnumDirection.Right);
					} else if (waypoints[waypointIndex] == 1) {
						waypoint = EnumWaypoint.End;
					} else {
						waypoint = EnumWaypoint.Back;
					}

					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x14:
					if (waypoints[waypointIndex] == 0) {
						waypoint = EnumWaypoint.Start;
						waveManager.setStartingDirection(EnumDirection.Left);
					} else if (waypoints[waypointIndex] == 1) {
						waypoint = EnumWaypoint.End;
					} else {
						waypoint = EnumWaypoint.Back;
					}

					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x1d:
					canPlaceTower = false;
					break;
				case 0x1e:
					canPlaceTower = false;
					break;
				case 0x1f:
					canPlaceTower = false;
					break;
				case 0x20:
					canPlaceTower = false;
					break;
				case 0x21:
					waypoint = (waypoints[waypointIndex] == 0 ? EnumWaypoint.Left : waypoints[waypointIndex] == 1 ? EnumWaypoint.Right : EnumWaypoint.Back);
					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x22:
					waypoint = (waypoints[waypointIndex] == 0 ? EnumWaypoint.Left : waypoints[waypointIndex] == 1 ? EnumWaypoint.Right : EnumWaypoint.Back);
					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x23:
					if (waypoints[waypointIndex] == 0) {
						waypoint = EnumWaypoint.Start;
						waveManager.setStartingDirection(EnumDirection.Down);
					} else if (waypoints[waypointIndex] == 1) {
						waypoint = EnumWaypoint.End;
					} else {
						waypoint = EnumWaypoint.Back;
					}

					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x24:
					if (waypoints[waypointIndex] == 0) {
						waypoint = EnumWaypoint.Start;
						waveManager.setStartingDirection(EnumDirection.Up);
					} else if (waypoints[waypointIndex] == 1) {
						waypoint = EnumWaypoint.End;
					} else {
						waypoint = EnumWaypoint.Back;
					}

					waypointIndex++;
					canPlaceTower = false;
					break;
				case 0x2d:
					canPlaceTower = false;
					break;
				case 0x2e:
					canPlaceTower = false;
					break;
				case 0x2f:
					canPlaceTower = false;
					break;
				default:
					break;
				}

				// Set starting coordinates for entities
				if (waypoint == EnumWaypoint.Start) {
					waveManager.setStartingLocation(x, y);
				}

				level.setTileAt(x, y, new Tile(i, canPlaceTower, waypoint));
			}
		}
	}

	private static void generateWavesFrom(WaveManager waveManager, Scanner scanner) {
		String entityTypeLine = getNextLine(scanner);
		String waveLengthLine = getNextLine(scanner);
		String spawnrateLine = getNextLine(scanner);

		for (int i = 0; i < entityTypeLine.length(); i++) {
			int waveLength = Integer.parseInt(waveLengthLine.substring(i * 2, i * 2 + 2), 16);
			int spawnrate = Integer.parseInt(spawnrateLine.substring(i * 3, i * 3 + 3), 16);

			int entityType = Integer.parseInt(entityTypeLine.substring(i, i+1), 16);
			
			switch (entityType) {
			case 0x0:
				waveManager.addWave(EnumEntityType.Basic, waveLength, spawnrate);
				break;
			case 0x1:
				waveManager.addWave(EnumEntityType.Fast, waveLength, spawnrate);
				break;
			case 0x2:
				waveManager.addWave(EnumEntityType.Armoured, waveLength, spawnrate);
				break;
			case 0x3:
				waveManager.addWave(EnumEntityType.Strong, waveLength, spawnrate);
				break;
			default:
				break;
			}
		}		
	}

	private static void generateRandomWaves(WaveManager waveManager, Random random) {
		waveManager = new WaveManager((int) ((random.nextDouble() + 0.5) * 1440));

		for (int i = 0; i < (random.nextInt(32) + 8); i++) {
			switch (random.nextInt(3)) {
			case 0:
				waveManager.addWave(EnumEntityType.Basic, (random.nextInt(16) + 8), 60);
				break;
			case 1:
				waveManager.addWave(EnumEntityType.Fast, (random.nextInt(16) + 8), 60);
				break;
			case 2:
				waveManager.addWave(EnumEntityType.Armoured, (random.nextInt(16) + 8), 60);
				break;
			default:
				break;
			}
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

		public void setTileAt(int x, int y, Tile tile) {
			this.tiles[x + y * width] = tile;
		}
		
		public void setWaveManager(WaveManager waveManager) {
			this.waveManager = waveManager;
		}
	}
}
