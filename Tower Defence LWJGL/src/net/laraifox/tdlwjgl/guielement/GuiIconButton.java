package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.glColor3f;
import net.laraifox.lib.graphics.Texture;
import net.laraifox.tdlwjgl.enums.EnumButtonState;

public class GuiIconButton extends GuiElement {
	public GuiIconButton(String texture, int x, int y, int width, int height) {
		super(texture, x, y, width, height);
	}

	public GuiIconButton(Texture texture, int id, int x, int y, int width, int height) {
		super(texture, id, x, y, width, height);
	}

	public void render() {
		if (buttonState == EnumButtonState.Pressed)
			glColor3f(0.5f, 0.5f, 0.5f);
		else if (buttonState == EnumButtonState.Hovered)
			glColor3f(1.0f, 1.0f, 0.65f);

		super.render();

		glColor3f(1.0f, 1.0f, 1.0f);
	}
}
