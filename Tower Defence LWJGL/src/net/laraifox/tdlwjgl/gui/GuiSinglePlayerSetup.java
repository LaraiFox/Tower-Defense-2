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
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;
import net.laraifox.tdlwjgl.main.MenuManager;

public class GuiSinglePlayerSetup extends Gui {
	private GuiButton start;
	private GuiButton back;

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
		this.start = new GuiButton(width - 300, 50, 250, 50, 0.0f, "Start Game");
		this.back = new GuiButton(50, 50, 250, 50, 0.0f, "Back");
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		start.update();
		back.update();

		if (start.getState() == EnumButtonState.Clicked) {
			game.setProgramState(EnumProgramState.Game);
			game.setGameState(EnumGameState.SinglePlayer);

			manager.setMenuState(EnumMenuState.Title);
		} else if (back.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		}
		
		if (start.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = start.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (back.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = back.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		texture.bindTexture();
		super.render();

		start.render();
		back.render();

		glPopMatrix();
	}
}
