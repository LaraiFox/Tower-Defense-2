package net.laraifox.tdlwjgl.gui;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.main.MenuManager;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;

import org.lwjgl.opengl.GL11;

public class GuiSinglePlayerSetup extends Gui {
	private GuiButton startButton;
	private GuiButton backButton;

	public GuiSinglePlayerSetup(int width, int height) {
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
		this.startButton = new GuiButton(width - 300, 50, 250, 50, "Start Game");
		this.backButton = new GuiButton(50, 50, 250, 50, "Back");
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		startButton.update();
		backButton.update();

		if (startButton.getState() == EnumButtonState.Clicked) {
			game.setProgramState(EnumProgramState.Game);
			game.setGameState(EnumGameState.SinglePlayer);

			manager.setMenuState(EnumMenuState.Title);
		} else if (backButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		}

		if (startButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = startButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (backButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = backButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		GL11.glPushMatrix();
		texture.bindTexture();
		super.render();
		
		startButton.render();
		backButton.render();
		GL11.glPopMatrix();
	}
}
