package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;

import org.lwjgl.input.Mouse;

public class GuiSlider extends GuiElement {
	private static final int sliderBarWidth = 0;
	private static final int sliderBarHeight = 0;

	private int displayListID;
	private Texture sliderBarTexture;
	private float value;

	protected GuiSlider(int x, int y, int width, int height, float theta, float value) {
		super("res/gui/slider_background.png", x, y, width, height, theta);

		try {
			this.sliderBarTexture = TextureLoader.getTexture(new FileInputStream(new File("res/gui/slider_bar.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.value = value;

		createDisplayList();
	}

	protected void createDisplayList() {
		//
		// The display list isn't correct. The width and height of the slider bar is unknown at this point
		// so I will need to change the values when I know them.
		//

		displayListID = glGenLists(1);

		float tx = (float) sliderBarTexture.getImageWidth() / (float) sliderBarTexture.getTextureWidth();
		float ty = (float) sliderBarTexture.getImageHeight() / (float) sliderBarTexture.getTextureHeight();

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

	public void update() {
		if (buttonState == EnumButtonState.Pressed) {
			float dx = Mouse.getX() - bounds.x;

			value = dx / bounds.width;

			if (value < 0)
				value = 0;
			else if (value > 1)
				value = 1;
		}
	}

	public void render() {
		if (buttonState == EnumButtonState.Hovered)
			glColor3f(1.0f, 1.0f, 0.65f);

		super.render();

		glColor3f(1.0f, 1.0f, 1.0f);

		if (buttonState == EnumButtonState.Pressed)
			glColor3f(1.0f, 1.0f, 1.0f);

		glPushMatrix();
		glTranslatef(bounds.x + (bounds.width - sliderBarWidth) * value, bounds.y + (bounds.height - sliderBarHeight) / 2, 0);
		sliderBarTexture.bindTexture();
		glCallList(displayListID);
		glPopMatrix();
	}
}
