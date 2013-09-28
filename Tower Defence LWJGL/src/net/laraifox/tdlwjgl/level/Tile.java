package net.laraifox.tdlwjgl.level;

//import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import net.laraifox.tdlwjgl.enums.EnumWaypoint;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public class Tile {
	private static final int TEX_GRID_WIDTH = 16;
	private static final int TEX_GRID_HEIGHT = 16;
	private static final int TILE_SIZE = 32;
	private static int[] displayListIDs;

	private int tileID;
	private boolean canPlaceTower;

	public Tile(int id, boolean canPlaceTower, EnumWaypoint waypoint) {
		this.tileID = id;
		this.canPlaceTower = canPlaceTower;
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

			displayListIDs[i] = glGenLists(1);
			glNewList(displayListIDs[i], GL_COMPILE);
			glBegin(GL_QUADS);
			glTexCoord2f(left, top);
			glVertex2i(0, 0);
			glTexCoord2f(right, top);
			glVertex2i(TILE_SIZE, 0);
			glTexCoord2f(right, bottom);
			glVertex2i(TILE_SIZE, TILE_SIZE);
			glTexCoord2f(left, bottom);
			glVertex2i(0, TILE_SIZE);
			glEnd();
			glEndList();
		}
	}

	public void render(float x, float y) {
		SpriteSheet.Terrain.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		glCallList(displayListIDs[tileID]);
		GL11.glPopMatrix();
	}

	public static int getTileSize() {
		return TILE_SIZE;
	}

	public boolean canPlaceTower() {
		return canPlaceTower;
	}

	public void setCanPlaceTower(boolean canPlaceTower) {
		this.canPlaceTower = canPlaceTower;
	}
}
