package net.laraifox.tdlwjgl.gui;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.main.MenuManager;

public class GuiTitle extends Gui {
	private GuiButton singlePlayerButton;
	private GuiButton multiPlayerButton;
	private GuiButton levelEditorButton;
	private GuiButton optionsButton;
	private GuiButton creditsButton;
	private GuiButton quitButton;

	private GuiLabel title;
	private GuiLabel splash;

	private Random random;

	public GuiTitle(int width, int height, Random random) {
		super(0, 0, width, height);

		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File("res/title/background.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.random = random;

		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		this.singlePlayerButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 5.5), 250, 50, "Single Player");
		this.multiPlayerButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 4.5), 250, 50, "Multi Player");
		this.levelEditorButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 3.5), 250, 50, "Level Editor");
		this.optionsButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 2.5), 250, 50, "Settings");
		this.creditsButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 1.5), 250, 50, "Credits");
		this.quitButton = new GuiButton((width - 250) / 2, (int) ((height / 10) * 0.5), 250, 50, "Quit");

		this.title = new GuiLabel((width - 250) / 2, (int) ((height / 10) * 8), 250, 50, "Tower Defense", EnumFontSize.Large);
		this.splash = new GuiLabel((width - 250) / 2, (int) ((height / 10) * 7.5), 250, 50, getSplash(), EnumFontSize.Small);
	}

	private String getSplash() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("res/title/splashes.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		StringBuilder builder = new StringBuilder();

		while (scanner.hasNext()) {
			String line = scanner.nextLine();

			if (line.length() > 0) {
				char initialCharacter = line.charAt(0);
				if (initialCharacter == '\n' || initialCharacter == '/' || initialCharacter == ' ')
					continue;

				builder.append(line + "\n");
			}
		}

		String[] splashList = builder.toString().split("\n");
		String splash = new String();

		if (splashList.length > 0) {
			splash = splashList[random.nextInt(splashList.length)];
		}

		scanner.close();
		return splash;
	}

	public void update(MenuManager manager) {
		singlePlayerButton.update();
		multiPlayerButton.update();
		levelEditorButton.update();
		optionsButton.update();
		creditsButton.update();
		quitButton.update();

		if (singlePlayerButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.SinglePlayerSetup);
		} else if (multiPlayerButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.MultiPlayerSetup);
		} else if (levelEditorButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.EditorSetup);
		} else if (optionsButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Options);
		} else if (creditsButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Credits);
		} else if (quitButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Quit);
		}

		if (singlePlayerButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = singlePlayerButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (multiPlayerButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = multiPlayerButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (levelEditorButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = levelEditorButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (optionsButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = optionsButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (creditsButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = creditsButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (quitButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = quitButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		texture.bindTexture();
		super.render();

		title.render();
		splash.render();

		singlePlayerButton.render();
		multiPlayerButton.render();
		levelEditorButton.render();
		optionsButton.render();
		creditsButton.render();
		quitButton.render();

		glPopMatrix();
	}
}
