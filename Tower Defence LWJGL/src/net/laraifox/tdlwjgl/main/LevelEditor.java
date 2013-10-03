package net.laraifox.tdlwjgl.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.laraifox.tdlwjgl.enums.EnumEditorState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.level.LevelFormatter;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.level.WaveManager;
import net.laraifox.tdlwjgl.level.WaypointList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class LevelEditor {
	private EnumEditorState editorState;

	private String fileName;
	private String title;
	private List<String> comments;

	private Tile[] tiles;
	private int width, height;
	private WaveManager waveManager;
	private List<WaypointList> waypointLists;

	private Tile selectedTile;

	private boolean KEY_S;

	public LevelEditor() {
		this.editorState = EnumEditorState.TileEditor;

		this.fileName = "";
		this.title = "";
		this.comments = new ArrayList<String>();

		this.tiles = new Tile[0];
		this.width = 0;
		this.height = 0;
		this.waveManager = new WaveManager();
		this.waypointLists = new ArrayList<WaypointList>();

		this.selectedTile = new Tile(0);
	}

	public void newLevel(int defaultTileID, int width, int height) {
		this.editorState = EnumEditorState.TileEditor;

		this.fileName = "Untitled";
		this.title = "Untitled";
		this.comments = new ArrayList<String>();

		this.tiles = new Tile[width * height];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = new Tile(defaultTileID);
		this.width = width;
		this.height = height;
		this.waveManager = new WaveManager();
		this.waypointLists = new ArrayList<WaypointList>();

		this.selectedTile = new Tile(defaultTileID);
	}

	public void save() {
		try {
			LevelFormatter.formatAndSave(fileName, title, comments.toArray(new String[comments.size()]), tiles, width, height, waveManager, waypointLists);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() {

	}

	public void update(TowerDefenseGame game) {
		switch (editorState) {
		case TileEditor:
			updateTileEditor(game);
			break;
		case EntityEditor:
			updateEntityEditor();
			break;
		case Menu:
			break;
		default:
			break;
		}
	}

	private void updateTileEditor(TowerDefenseGame game) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			game.setProgramState(EnumProgramState.Menu);
			game.setMenuState(EnumMenuState.Title);
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S) && !KEY_S) {
			save();
		}

		if (Mouse.isButtonDown(0)) {
			int x = Mouse.getX() / Tile.getTileSize() - 1;
			int y = Mouse.getY() / Tile.getTileSize() - 1;
			if (x >= 0 && x < width && y >= 0 && y < height) {
				tiles[x + y * width] = selectedTile;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
			selectedTile = new Tile(0x00);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
			selectedTile = new Tile(0x01);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
			selectedTile = new Tile(0x02);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
			selectedTile = new Tile(0x03);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
			selectedTile = new Tile(0x10);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
			selectedTile = new Tile(0x20);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_7)) {
			selectedTile = new Tile(0x11);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_8)) {
			selectedTile = new Tile(0x12);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_9)) {
			selectedTile = new Tile(0x22);
		} else if (Keyboard.isKeyDown(Keyboard.KEY_0)) {
			selectedTile = new Tile(0x21);
		}

		KEY_S = Keyboard.isKeyDown(Keyboard.KEY_S);
	}

	private void updateEntityEditor() {

	}

	public void render() {
		switch (editorState) {
		case TileEditor:
			GL11.glPushMatrix();
			GL11.glTranslatef(Tile.getTileSize(), Tile.getTileSize(), 0);
			for (int i = 0; i < tiles.length; i++) {
				int x = (i % width) * Tile.getTileSize();
				int y = (i / width) * Tile.getTileSize();
				tiles[i].render(x, y);
			}

			selectedTile.render(0, (height + 0.5f) * Tile.getTileSize());
			GL11.glPopMatrix();
			break;
		case EntityEditor:
			break;
		case Menu:
			break;
		default:
			break;
		}
	}
	
	public void setSelectedTile(int tileID) {
		this.selectedTile = new Tile(tileID);
	}
}
