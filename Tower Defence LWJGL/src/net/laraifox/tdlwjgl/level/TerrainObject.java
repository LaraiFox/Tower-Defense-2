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
import net.laraifox.tdlwjgl.util.SpriteSheet;

public class TerrainObject {
	private int id;
	private int gridX, gridY;
	private int x, y, width, height;
	private int displayListID;

	public TerrainObject(int id, int gridX, int gridY, int width, int height) {
		this.id = id;
		this.gridX = gridX;
		this.gridY = gridY;
		this.x = gridX * width;
		this.y = gridY * height;
		this.width = width;
		this.height = height;
		this.displayListID = glGenLists(1);

		createDisplayList();
	}

	private void createDisplayList() {
		int tx = id % 16;
		int ty = (id - tx) / 16;

		float left = SpriteSheet.Terrain.getLeftOfTile(tx);
		float right = SpriteSheet.Terrain.getRightOfTile(tx, 1);
		float top = SpriteSheet.Terrain.getTopOfTile(ty, 1);
		float bottom = SpriteSheet.Terrain.getBottomOfTile(ty);

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(left, top);
				glVertex2i(x, y);

				glTexCoord2f(right, top);
				glVertex2i(x + width, y);

				glTexCoord2f(right, bottom);
				glVertex2i(x + width, y + height);

				glTexCoord2f(left, bottom);
				glVertex2i(x, y + height);
			}
			glEnd();
		}
		glEndList();
	}

	public int getDisplayListID() {
		return displayListID;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void render() {
		SpriteSheet.Objects.bindSheetTexture();
		glCallList(displayListID);
	}
}
