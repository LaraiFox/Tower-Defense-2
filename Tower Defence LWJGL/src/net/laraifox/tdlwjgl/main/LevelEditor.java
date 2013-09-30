package net.laraifox.tdlwjgl.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.laraifox.tdlwjgl.enums.EnumEditorState;
import net.laraifox.tdlwjgl.level.LevelFormatter;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.level.WaveManager;
import net.laraifox.tdlwjgl.level.WaypointList;

public class LevelEditor {
	private EnumEditorState editorState;

	private String fileName;
	private String title;
	private List<String> comments;

	private Tile[] tiles;
	private int width, height;
	private WaveManager waveManager;
	private List<WaypointList> waypointLists;

	public LevelEditor(Tile defaultTile, int width, int height) {
		this.editorState = EnumEditorState.TileEditor;

		this.fileName = "";
		this.title = "";
		this.comments = new ArrayList<String>();

		this.tiles = new Tile[width * height];
		this.width = width;
		this.height = height;
		this.waveManager = new WaveManager();
		this.waypointLists = new ArrayList<WaypointList>();
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

	public void update() {
		switch (editorState) {
		case TileEditor:
			break;
		case EntityEditor:
			break;
		case Menu:
			break;
		default:
			break;
		}
	}

	public void render() {
		switch (editorState) {
		case TileEditor:
			break;
		case EntityEditor:
			break;
		case Menu:
			break;
		default:
			break;
		}
	}
}
