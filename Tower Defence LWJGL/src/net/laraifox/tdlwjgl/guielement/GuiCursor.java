package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.graphics.TextureLoader;

public class GuiCursor {
	private Texture texture;
	private int displayListID;
	private int x, y;
	private float halfWidth;
	private float halfSize;
	private int cursorRotation;
	private int rotationSpeed;
	private boolean isVisible;

	public GuiCursor() {
		this(0, 0, 32, true);
	}

	public GuiCursor(int x, int y) {
		this(x, y, 32, true);
	}

	public GuiCursor(int x, int y, int size) {
		this(x, y, size, true);
	}

	public GuiCursor(int x, int y, int size, boolean isVisible) {
		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File("res/gui/selection_cursor_128.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		this.halfSize = size / 2.0f;
		this.cursorRotation = 0;
		this.rotationSpeed = 2;
		this.isVisible = isVisible;

		createDisplayList();
	}

	private void createDisplayList() {
		displayListID = glGenLists(1);

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0.0f, 1.0f);
				glVertex2f(-halfSize, -halfSize);

				glTexCoord2f(1.0f, 1.0f);
				glVertex2f(halfSize, -halfSize);

				glTexCoord2f(1.0f, 0.0f);
				glVertex2f(halfSize, halfSize);

				glTexCoord2f(0.0f, 0.0f);
				glVertex2f(-halfSize, halfSize);
			}
			glEnd();
		}
		glEndList();
	}

	/**
	 * Sets the location of the bottom middle of the cursor and also sets the
	 * width of the cursor. The left and right parts of the cursor will be
	 * spaced half of this distance away from the center.
	 * 
	 * @param x
	 *            - The X position of the center.
	 * @param y
	 *            - The Y position of the center.
	 * @param width
	 *            - The total width of the cursor.
	 */
	public void setLocation(int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.halfWidth = width / 2.0f;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public void update() {
		cursorRotation += rotationSpeed;
	}

	public void render() {
		if (isVisible) {
			glColor3f(1.0f, 1.0f, 0.65f);

			texture.bindTexture();
			glPushMatrix();
			glLoadIdentity();
			glTranslated(x - halfWidth - halfSize, y, 0);
			glRotatef(-cursorRotation, 0, 0, 1);
			glCallList(displayListID);
			glPopMatrix();

			glPushMatrix();
			glLoadIdentity();
			glTranslated(x + halfWidth + halfSize, y, 0);
			glRotatef(cursorRotation, 0, 0, 1);
			glCallList(displayListID);
			glPopMatrix();

			glColor3f(1.0f, 1.0f, 1.0f);
		}
	}
}
