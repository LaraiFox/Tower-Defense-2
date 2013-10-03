package net.laraifox.tdlwjgl.object;

import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class TerrainObject {
	private int displayListID;
	private int x, y;

	public TerrainObject(int displayListID, int tiledX, int tiledY) {
		this.displayListID = displayListID;
		this.x = tiledX * Tile.getTileSize();
		this.y = tiledY * Tile.getTileSize();
	}

	protected static int initialize(int objectID, int tiledWidth, int tiledHeight) {
		int displayListID = GL11.glGenLists(1);

		int tx = objectID % 8;
		int ty = (objectID - tx) / 8;

		float left = SpriteSheet.Towers.getLeftOfTile(tx);
		float right = SpriteSheet.Towers.getRightOfTile(tx, tiledWidth);
		float top = SpriteSheet.Towers.getTopOfTile(ty, tiledHeight);
		float bottom = SpriteSheet.Towers.getBottomOfTile(ty);

		int width = tiledWidth * Tile.getTileSize();
		int height = tiledWidth * Tile.getTileSize();

		GL11.glNewList(displayListID, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(left, top);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(right, top);
		GL11.glVertex2i(width, 0);
		GL11.glTexCoord2f(right, bottom);
		GL11.glVertex2i(width, height);
		GL11.glTexCoord2f(left, bottom);
		GL11.glVertex2i(0, height);
		GL11.glEnd();
		GL11.glEndList();

		return displayListID;
	}

	public void render() {
		SpriteSheet.Objects.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glCallList(displayListID);
		GL11.glPopMatrix();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
