package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.glColor3f;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class GuiIconButton extends GuiElement {
	private static final EnumFontSize fontsize = EnumFontSize.Small;
	private static final int buttonSize = 64;

	private String text;

	public GuiIconButton(String texture, int x, int y, int width, int height, float theta, String text) {
		super(texture, x, y, width * buttonSize, height * buttonSize, theta);

		this.text = text;
	}

	public void render() {
		if (buttonState == EnumButtonState.Pressed)
			glColor3f(0.5f, 0.5f, 0.5f);
		else if (buttonState == EnumButtonState.Hovered)
			glColor3f(1.0f, 1.0f, 0.65f);

		super.render();

		StringRenderer.render(text, bounds.x + (bounds.width - text.length() * fontsize.getWidth()) / 2, bounds.y + (bounds.height - fontsize.getHeight()) / 2, fontsize);

		glColor3f(1.0f, 1.0f, 1.0f);
	}
}
