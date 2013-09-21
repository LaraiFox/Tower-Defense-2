package net.laraifox.tdlwjgl.gui;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;
import net.laraifox.lib.graphics.Texture;

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
		displayListID = glGenLists(1);

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0.0f, 1.0f);
				glVertex2i(0, 0);

				glTexCoord2f(1.0f, 1.0f);
				glVertex2i(width, 0);

				glTexCoord2f(1.0f, 0.0f);
				glVertex2i(width, height);

				glTexCoord2f(0.0f, 0.0f);
				glVertex2i(0, height);
			}
			glEnd();
		}
		glEndList();
	}
	
	public void render() {
		glTranslatef(x, y, 0);
		glCallList(displayListID);
	}
}
