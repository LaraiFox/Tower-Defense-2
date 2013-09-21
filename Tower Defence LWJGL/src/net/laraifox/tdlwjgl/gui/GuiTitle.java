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
	private GuiButton singlePlayer;
	private GuiButton multiPlayer;
	private GuiButton levelEditor;
	private GuiButton options;
	private GuiButton credits;
	private GuiButton quit;

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
		this.singlePlayer = new GuiButton((width - 250) / 2, (int) ((height / 10) * 5.5), 250, 50, 0.0f, "Single Player");
		this.multiPlayer = new GuiButton((width - 250) / 2, (int) ((height / 10) * 4.5), 250, 50, 0.0f, "Multi Player");
		this.levelEditor = new GuiButton((width - 250) / 2, (int) ((height / 10) * 3.5), 250, 50, 0.0f, "Level Editor");
		this.options = new GuiButton((width - 250) / 2, (int) ((height / 10) * 2.5), 250, 50, 0.0f, "Options");
		this.credits = new GuiButton((width - 250) / 2, (int) ((height / 10) * 1.5), 250, 50, 0.0f, "Credits");
		this.quit = new GuiButton((width - 250) / 2, (int) ((height / 10) * 0.5), 250, 50, 0.0f, "Quit");

		this.title = new GuiLabel((width - 250) / 2, (int) ((height / 10) * 8), 250, 50, 0.0f, "Tower Defense", EnumFontSize.Large);
		this.splash = new GuiLabel((width - 250) / 2, (int) ((height / 10) * 7.5), 250, 50, 25.0f, getSplash(), EnumFontSize.Small);
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
		singlePlayer.update();
		multiPlayer.update();
		levelEditor.update();
		options.update();
		credits.update();
		quit.update();
		
		if (singlePlayer.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.SinglePlayerSetup);
		} else if (multiPlayer.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.MultiPlayerSetup);
		} else if (levelEditor.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.EditorSetup);
		} else if (options.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Options);
		} else if (credits.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Credits);
		} else if (quit.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Quit);
		}
		
		if (singlePlayer.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = singlePlayer.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (multiPlayer.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = multiPlayer.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (levelEditor.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = levelEditor.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (options.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = options.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (credits.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = credits.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (quit.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = quit.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		texture.bindTexture();
		super.render();

		title.render();
		splash.render();

		singlePlayer.render();
		multiPlayer.render();
		levelEditor.render();
		options.render();
		credits.render();
		quit.render();

		glPopMatrix();
	}
}