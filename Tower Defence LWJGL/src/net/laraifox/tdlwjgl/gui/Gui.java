package net.laraifox.tdlwjgl.gui;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import net.laraifox.lib.graphics.Texture;

import org.lwjgl.opengl.GL11;

public abstract class Gui {
	private int displayListID;
	private int x, y;

	protected Texture texture;
	protected int width, height;

	public Gui(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		createDisplayList();
	}

	protected abstract void initializeGuiElements();

	private void createDisplayList() {
		displayListID = GL11.glGenLists(1);
		GL11.glNewList(displayListID, GL_COMPILE);
		GL11.glBegin(GL_QUADS);
		GL11.glTexCoord2f(0.0f, 1.0f);
		GL11.glVertex2i(0, 0);
		GL11.glTexCoord2f(1.0f, 1.0f);
		GL11.glVertex2i(width, 0);
		GL11.glTexCoord2f(1.0f, 0.0f);
		GL11.glVertex2i(width, height);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex2i(0, height);
		GL11.glEnd();
		GL11.glEndList();
	}

	public void render() {
		GL11.glTranslatef(x, y, 0);
		GL11.glCallList(displayListID);
	}
}
