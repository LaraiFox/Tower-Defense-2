package net.laraifox.tdlwjgl.gui;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;
import net.laraifox.tdlwjgl.main.MenuManager;

public class GuiOptions extends Gui {
	private GuiButton save;
	private GuiButton back;
	private GuiButton toggleCursor;
	private GuiButton toggleFPS;

	public GuiOptions(int width, int height) {
		super(0, 0, width, height);

		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File("res/title/background.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		this.save = new GuiButton(width - 300, 50, 250, 50, "Save");
		this.back = new GuiButton(50, 50, 250, 50, "Back");
		this.toggleCursor = new GuiButton((width - 350) / 2, height - 200, 350, 50, "Toggle Cursor (on)");
		this.toggleFPS = new GuiButton((width - 350) / 2, height - 260, 350, 50, "Show Framerate");
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		save.update();
		back.update();

		toggleCursor.update();
		toggleFPS.update();

		if (save.getState() == EnumButtonState.Clicked) {
			// call a saveSettings() method here

			manager.setMenuState(EnumMenuState.Title);
		} else if (back.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		} else if (toggleCursor.getState() == EnumButtonState.Clicked) {
			manager.setShowCursor(!manager.getShowCursor());
			toggleCursor.setText("Toggle Cursor (" + (manager.getShowCursor() ? "on" : "off") + ")");
		} else if (toggleFPS.getState() == EnumButtonState.Clicked) {
			game.setShowFramerate(!game.getShowFramerate());
			toggleFPS.setText((game.getShowFramerate() ? "Hide" : "Show") + " Framerate");
		}

		if (save.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = save.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (back.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = back.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (toggleCursor.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = toggleCursor.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (toggleFPS.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = toggleFPS.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		texture.bindTexture();
		super.render();

		save.render();
		back.render();

		toggleCursor.render();
		toggleFPS.render();

		glPopMatrix();
	}
}
