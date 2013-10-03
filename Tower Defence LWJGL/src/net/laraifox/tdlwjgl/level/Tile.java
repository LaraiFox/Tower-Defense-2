package net.laraifox.tdlwjgl.level;

import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public class Tile {
	private static final int TEX_GRID_WIDTH = 16;
	private static final int TEX_GRID_HEIGHT = 16;
	private static final int TILE_SIZE = 32;
	private static int[] displayListIDs;

	private int tileID;
	private boolean towerPlaceable;

	public Tile(int tileID) {
		this.tileID = tileID;
		this.towerPlaceable = tileID > 127;
	}

	public static void initialize() {
		Tile.displayListIDs = new int[TEX_GRID_WIDTH * TEX_GRID_HEIGHT];
		for (int i = 0; i < displayListIDs.length; i++) {
			int tx = i % 16;
			int ty = (i - tx) / 16;

			float left = SpriteSheet.Terrain.getLeftOfTile(tx);
			float right = SpriteSheet.Terrain.getRightOfTile(tx, 1);
			float top = SpriteSheet.Terrain.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Terrain.getBottomOfTile(ty);

			displayListIDs[i] = GL11.glGenLists(1);
			GL11.glNewList(displayListIDs[i], GL11.GL_COMPILE);
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(left, top);
			GL11.glVertex2i(0, 0);
			GL11.glTexCoord2f(right, top);
			GL11.glVertex2i(TILE_SIZE, 0);
			GL11.glTexCoord2f(right, bottom);
			GL11.glVertex2i(TILE_SIZE, TILE_SIZE);
			GL11.glTexCoord2f(left, bottom);
			GL11.glVertex2i(0, TILE_SIZE);
			GL11.glEnd();
			GL11.glEndList();
		}
	}

	public void render(float x, float y) {
		SpriteSheet.Terrain.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glCallList(displayListIDs[tileID]);
		GL11.glPopMatrix();
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}

	public static int getDisplayList(int id) {
		return displayListIDs[id];
	}

	public int getTileID() {
		return tileID;
	}

	public boolean isTowerPlaceable() {
		return towerPlaceable;
	}

	public void setTowerPlaceable(boolean canPlaceTower) {
		this.towerPlaceable = canPlaceTower;
	}
}
