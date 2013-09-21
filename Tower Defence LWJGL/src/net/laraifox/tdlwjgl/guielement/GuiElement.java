package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;

import org.lwjgl.input.Mouse;

public abstract class GuiElement {
	protected Texture texture;
	protected int displayListID;
	protected Rectangle bounds;
	protected float theta;
	protected EnumButtonState buttonState;

	protected GuiElement(String textureFile, int x, int y, int width, int height, float theta) {
		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File(textureFile)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.bounds = new Rectangle(x, y, width, height);
		this.buttonState = EnumButtonState.None;

		createDisplayList();
	}

	protected void createDisplayList() {
		displayListID = glGenLists(1);

		float tx = 1.0f; // ((float) texture.getImageWidth()) / ((float) texture.getTextureWidth());
		float ty = 1.0f; // ((float) texture.getImageHeight()) / ((float) texture.getTextureHeight());

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0.0f, ty);
				glVertex2i(0, 0);

				glTexCoord2f(tx, ty);
				glVertex2i(bounds.width, 0);

				glTexCoord2f(tx, 0.0f);
				glVertex2i(bounds.width, bounds.height);

				glTexCoord2f(0.0f, 0.0f);
				glVertex2i(0, bounds.height);
			}
			glEnd();
		}
		glEndList();
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public EnumButtonState getState() {
		return buttonState;
	}

	public void update() {
		if (bounds.contains(Mouse.getX(), Mouse.getY())) {
			if (Mouse.isButtonDown(0)) {
				buttonState = EnumButtonState.Pressed;
			} else {
				if (buttonState == EnumButtonState.Pressed) {
					buttonState = EnumButtonState.Clicked;
				} else {
					buttonState = EnumButtonState.Hovered;
				}
			}
		} else {
			buttonState = EnumButtonState.Released;
		}
	}

	public void render() {
		glPushMatrix();
		glLoadIdentity();
		glTranslatef(bounds.x + bounds.width, bounds.y + bounds.height, 0);
		glRotatef(theta, 0, 0, 1);
		glTranslatef(-bounds.width, -bounds.height, 0);
		texture.bindTexture();
		glCallList(displayListID);
		glPopMatrix();
	}
}
