package net.laraifox.tdlwjgl.guielement;

import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class GuiLabel extends GuiElement {
	private String text;
	private EnumFontSize fontsize;
	private int textX, textY;

	public GuiLabel(int x, int y, int width, int height, float theta, String text, EnumFontSize fontsize) {
		super("res/debug/blank.png", x, y, width, height, theta);

		this.text = text;
		this.fontsize = fontsize;

		updateTextPosition();
	}

	public void setText(String text) {
		this.text = text;

		updateTextPosition();
	}

	public void setFontSize(EnumFontSize fontsize) {
		this.fontsize = fontsize;
	}

	public void updateTextPosition() {
		int l = text.length();

		textX = bounds.x + ((bounds.width - l * fontsize.getWidth()) / 2);
		textY = bounds.y + ((bounds.height - fontsize.getHeight()) / 2);
	}
	
	public void update() {	
		/* 
		 *  No updating is required for the GuiLabel class.
		 *  
		 */
	}

	public void render() {
		StringRenderer.render(text, textX, textY, fontsize);

		super.render();
	}
}
