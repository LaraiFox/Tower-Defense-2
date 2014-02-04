package net.laraifox.tdlwjgl.guielement;

import net.laraifox.lib.text.VectorFont;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class GuiLabel extends GuiElement {
	private String text;
	private EnumFontSize fontsize;

	public GuiLabel(int x, int y, int width, int height, String text, EnumFontSize fontsize) {
		super("res/debug/blank.png", x, y, width, height);

		this.text = text;
		this.fontsize = fontsize;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setFontSize(EnumFontSize fontsize) {
		this.fontsize = fontsize;
	}

	public void update() {
		/*
		 * No updating is required for the GuiLabel class.
		 */
	}

	public void render() {
		StringRenderer.render(text, bounds.x + bounds.width / 2, bounds.y, fontsize, VectorFont.ALIGN_CENTER);

		super.render();
	}
}
