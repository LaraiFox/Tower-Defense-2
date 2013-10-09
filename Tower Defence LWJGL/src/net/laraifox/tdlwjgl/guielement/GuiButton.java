package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.glColor3f;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class GuiButton extends GuiElement {
	private static final EnumFontSize fontsize = EnumFontSize.Medium;

	private String text;

	public GuiButton(int x, int y, int width, int height, String text) {
		super("res/gui/button.png", x, y, width, height);

		this.text = text;
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

		StringRenderer.render(text, bounds.x + (bounds.width - text.length() * fontsize.getWidth()) / 2, bounds.y + (bounds.height - fontsize.getHeight()) / 2, fontsize);

		glColor3f(1.0f, 1.0f, 1.0f);
	}
}
