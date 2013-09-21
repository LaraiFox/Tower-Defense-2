package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class GuiTinyButton extends GuiElement {
	private static final EnumFontSize fontsize = EnumFontSize.Small;

	private String text;

	public GuiTinyButton(int x, int y, int width, int height, float theta, String text) {
		super("res/gui/button.png", x, y, width, height, theta);

		this.text = text;
	}

	protected void createDisplayList() {
		displayListID = glGenLists(1);

		float tx = 1.0f; // ((float) texture.getImageWidth()) / ((float) texture.getTextureWidth());
		float ty = 1.0f; // ((float) texture.getImageHeight()) / ((float) texture.getTextureHeight());

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				// top half
				glTexCoord2f(0.0f, ty);
				glVertex2i(0, 0);

				glTexCoord2f(tx, ty);
				glVertex2i(bounds.width, 0);

				glTexCoord2f(tx, ty * 0.75f);
				glVertex2i(bounds.width, bounds.height / 2);

				glTexCoord2f(0.0f, ty * 0.75f);
				glVertex2i(0, bounds.height / 2);

				// bottom half
				glTexCoord2f(0.0f, ty * 0.25f);
				glVertex2i(0, bounds.height / 2);

				glTexCoord2f(tx, ty * 0.25f);
				glVertex2i(bounds.width, bounds.height / 2);

				glTexCoord2f(tx, 0.0f);
				glVertex2i(bounds.width, bounds.height);

				glTexCoord2f(0.0f, 0.0f);
				glVertex2i(0, bounds.height);
			}
			glEnd();
		}
		glEndList();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void render() {
		if (buttonState == EnumButtonState.Pressed)
			glColor3f(0.5f, 0.5f, 0.5f);
		else if (buttonState == EnumButtonState.Hovered)
			glColor3f(1.0f, 1.0f, 0.65f);

		super.render();

		StringRenderer.render(text, bounds.x + (bounds.width - text.length() * fontsize.getWidth()) / 2, bounds.y + (bounds.height - fontsize.getHeight()) / 2,
				fontsize);

		glColor3f(1.0f, 1.0f, 1.0f);
	}
}
