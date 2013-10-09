package net.laraifox.tdlwjgl.gui;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.main.MenuManager;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;

import org.lwjgl.opengl.GL11;

public class GuiQuit extends Gui {
	private GuiButton yesButton;
	private GuiButton noButton;

	private GuiLabel questionLabel;

	public GuiQuit(int width, int height) {
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
		this.yesButton = new GuiButton((width - 250) / 2, (height / 10) * 5, 250, 50, "Yes");
		this.noButton = new GuiButton((width - 250) / 2, (height / 10) * 4, 250, 50, "No");

		this.questionLabel = new GuiLabel(0, (height / 10) * 6, width, 50, "Are You Sure You Want To Quit?", EnumFontSize.Medium);
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		yesButton.update();
		noButton.update();

		if (yesButton.getState() == EnumButtonState.Clicked) {
			game.setProgramState(EnumProgramState.Quit);
		} else if (noButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		}

		if (yesButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = yesButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (noButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = noButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		GL11.glPushMatrix();
		texture.bindTexture();
		super.render();

		questionLabel.render();

		yesButton.render();
		noButton.render();
		GL11.glPopMatrix();
	}
}
