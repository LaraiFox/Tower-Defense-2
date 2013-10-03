package net.laraifox.tdlwjgl.gui;

import net.laraifox.tdlwjgl.guielement.GuiIconButton;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.util.SpriteSheet;

public class GuiTileSelection extends Gui {
	private GuiIconButton[] tileButtons;

	public GuiTileSelection(int x, int y, int width, int height) {
		super(x, y, width, height);

		this.tileButtons = new GuiIconButton[256];
		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		for (int i = 0; i < tileButtons.length; i++) {
			tileButtons[i] = new GuiIconButton(SpriteSheet.Terrain.getTexture(), Tile.getDisplayList(i), (i % 16) * 32 + 24, height - (i / 16) * 32, 32, 32);
		}
	}
}
