package net.laraifox.tdlwjgl.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.gui.GuiCredits;
import net.laraifox.tdlwjgl.gui.GuiLevelEditorSetup;
import net.laraifox.tdlwjgl.gui.GuiMultiPlayerSetup;
import net.laraifox.tdlwjgl.gui.GuiOptions;
import net.laraifox.tdlwjgl.gui.GuiQuit;
import net.laraifox.tdlwjgl.gui.GuiSinglePlayerSetup;
import net.laraifox.tdlwjgl.gui.GuiTitle;
import net.laraifox.tdlwjgl.guielement.GuiCursor;

public class MenuManager {
	private int width, height;
	private Random random;

	private GuiTitle title;
	private GuiSinglePlayerSetup singlePlayerSetup;
	private GuiMultiPlayerSetup multiPlayerSetup;
	private GuiLevelEditorSetup levelEditorSetup;
	private GuiOptions options;
	private GuiCredits credits;
	private GuiQuit quit;

	private GuiCursor guiCursor;
	private boolean showCursor;
	private EnumMenuState menuState;

	public MenuManager(int width, int height, Random random) {
		this.width = width;
		this.height = height;
		this.random = random;

		this.guiCursor = new GuiCursor();
		guiCursor.setVisible(false);
		this.showCursor = true;
		this.menuState = EnumMenuState.Title;

		try {
			initializeGuis();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initializeGuis() throws FileNotFoundException, IOException {
		this.title = new GuiTitle(width, height, random);
		this.singlePlayerSetup = new GuiSinglePlayerSetup(width, height);
		this.multiPlayerSetup = new GuiMultiPlayerSetup(width, height);
		this.levelEditorSetup = new GuiLevelEditorSetup(width, height);
		this.options = new GuiOptions(width, height);
		this.credits = new GuiCredits(width, height);
		this.quit = new GuiQuit(width, height);
	}

	public boolean getShowCursor() {
		return showCursor;
	}

	public void setShowCursor(boolean showCursor) {
		this.showCursor = showCursor;
	}

	public void setCursorLocation(int x, int y, int width) {
		guiCursor.setLocation(x, y, width);
		guiCursor.setVisible(showCursor);
	}

	public void setMenuState(EnumMenuState menuState) {
		this.menuState = menuState;
		guiCursor.setVisible(false);
	}

	public void update(TowerDefenseGame game) {
		switch (menuState) {
		case Title:
			title.update(this);
			break;
		case SinglePlayerSetup:
			singlePlayerSetup.update(game, this);
			break;
		case MultiPlayerSetup:
			multiPlayerSetup.update(this);
			break;
		case EditorSetup:
			levelEditorSetup.update(game, this);
			break;
		case Options:
			options.update(game, this);
			break;
		case Credits:
			credits.update(this);
			break;
		case Quit:
			quit.update(game, this);
			break;
		default:
			setMenuState(EnumMenuState.Title);
			break;
		}

		guiCursor.update();

		updatePreviousInput();
	}

	private void updatePreviousInput() {
	}

	public void render() {
		switch (menuState) {
		case Title:
			title.render();
			break;
		case SinglePlayerSetup:
			singlePlayerSetup.render();
			break;
		case MultiPlayerSetup:
			multiPlayerSetup.render();
			break;
		case EditorSetup:
			levelEditorSetup.render();
			break;
		case Options:
			options.render();
			break;
		case Credits:
			credits.render();
			break;
		case Quit:
			quit.render();
			break;
		default:
			break;
		}

		guiCursor.render();
	}
}
