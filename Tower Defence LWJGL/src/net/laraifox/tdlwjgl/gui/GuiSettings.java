package net.laraifox.tdlwjgl.gui;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumSettingResolution;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.main.MenuManager;
import net.laraifox.tdlwjgl.main.Settings;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;

import org.lwjgl.opengl.GL11;

public class GuiSettings extends Gui {
	private GuiButton saveButton;
	private GuiButton backButton;
	private GuiButton cursorButton;
	private GuiButton framerateButton;
	private GuiButton resolutionButton;

	private int resolutionIndex, resolutionWidth, resolutionHeight;

	public GuiSettings(int width, int height) {
		super(0, 0, width, height);

		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File("res/title/background.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.resolutionIndex = -1;

		initializeGuiElements();
		updateSettings();
	}

	protected void initializeGuiElements() {
		this.saveButton = new GuiButton(width - 300, 50, 250, 50, "Save");
		this.backButton = new GuiButton(50, 50, 250, 50, "Back");
		this.cursorButton = new GuiButton((width - 350) / 2, height - 200, 350, 50, "Toggle Cursor (on)");
		this.framerateButton = new GuiButton((width - 350) / 2, height - 260, 350, 50, "Show Framerate");
		this.resolutionButton = new GuiButton((width - 350) / 2, height - 320, 350, 50, "Resolution: ");
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		saveButton.update();
		backButton.update();
		cursorButton.update();
		framerateButton.update();
		resolutionButton.update();

		if (saveButton.getState() == EnumButtonState.Clicked) {
			saveSettings();
			manager.setMenuState(EnumMenuState.Title);
		} else if (backButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		} else if (cursorButton.getState() == EnumButtonState.Clicked) {
			manager.setShowCursor(!manager.getShowCursor());
			cursorButton.setText("Toggle Cursor (" + (manager.getShowCursor() ? "on" : "off") + ")");
		} else if (framerateButton.getState() == EnumButtonState.Clicked) {
			game.setShowFramerate(!game.getShowFramerate());
			framerateButton.setText((game.getShowFramerate() ? "Hide" : "Show") + " Framerate");
		} else if (resolutionButton.getState() == EnumButtonState.Clicked) {
			resolutionIndex++;
			EnumSettingResolution[] values = EnumSettingResolution.values();
			if (resolutionIndex < 0 || resolutionIndex >= values.length)
				resolutionIndex = 0;
			resolutionWidth = values[resolutionIndex].getWidth();
			resolutionHeight = values[resolutionIndex].getHeight();
			resolutionButton.setText("Resolution: " + resolutionWidth + "x" + resolutionHeight);
		}

		if (saveButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = saveButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (backButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = backButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (cursorButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = cursorButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (framerateButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = framerateButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (resolutionButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = resolutionButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	private void saveSettings() {
		Settings.setResolution(resolutionWidth, resolutionHeight);
	}

	public void render() {
		GL11.glPushMatrix();
		texture.bindTexture();
		super.render();
		
		saveButton.render();
		backButton.render();
		cursorButton.render();
		framerateButton.render();
		resolutionButton.render();
		GL11.glPopMatrix();
	}

	public void updateSettings() {
		resolutionIndex = -1;
		resolutionWidth = Settings.getWidth();
		resolutionHeight = Settings.getHeight();
		resolutionButton.setText("Resolution: " + resolutionWidth + "x" + resolutionHeight);
		EnumSettingResolution[] values = EnumSettingResolution.values();
		for (int i = 0; i < values.length; i++) {
			if (values[i].getWidth() > resolutionWidth && values[i].getHeight() > resolutionHeight) {
				resolutionIndex = i - 1;
				break;
			}
		}
	}
}
