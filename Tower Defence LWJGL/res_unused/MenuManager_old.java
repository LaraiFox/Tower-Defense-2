package net.laraifox.tdlwjgl.main;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.gui.Gui;
import net.laraifox.tdlwjgl.gui.GuiButton;
import net.laraifox.tdlwjgl.gui.GuiLabel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class MenuManager_old {
	private int width, height;
	private Random random;

	private EnumMenuState menuState;
	private int selectionIndex;
	private Texture cursorTexture;
	private int displayListID;
	private int cursorRotation;
	private boolean showCursor;
	private final int cursorWidth, cursorHeight;

	private Gui title;
	private Gui singleplayerSetup;
	private Gui multiplayerSetup;
	private Gui editorSetup;
	private Gui options;
	private Gui credits;
	private Gui quit;

	private boolean MOUSE1;
	private boolean KEY_UP;
	private boolean KEY_DOWN;
	private boolean KEY_RETURN;

	public MenuManager_old(int width, int height, Random random) {
		this.width = width;
		this.height = height;
		this.random = random;

		this.menuState = EnumMenuState.Title;
		this.selectionIndex = 0;

		try {
			this.cursorTexture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/gui/selection_cursor_128.png")));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		this.cursorRotation = 0;
		this.showCursor = true;
		this.cursorWidth = 32;
		this.cursorHeight = 32;

		createDisplayList();

		try {
			initializeGuis();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createDisplayList() {
		displayListID = glGenLists(1);

		glNewList(displayListID, GL_COMPILE);
		{
			glBegin(GL_QUADS);
			{
				glTexCoord2f(0.0f, 1.0f);
				glVertex2i(0, 0);

				glTexCoord2f(1.0f, 1.0f);
				glVertex2i(32, 0);

				glTexCoord2f(1.0f, 0.0f);
				glVertex2i(32, 32);

				glTexCoord2f(0.0f, 0.0f);
				glVertex2i(0, 32);
			}
			glEnd();
		}
		glEndList();
	}

	private void initializeGuis() throws FileNotFoundException, IOException {
		this.title = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);

		this.singleplayerSetup = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		singleplayerSetup.addGuiElement("Start", new GuiButton(width - 300, 50, 250, 50, 0.0f, "Start Game"));
		singleplayerSetup.addGuiElement("Back", new GuiButton(50, 50, 250, 50, 0.0f, "Back"));
		
		this.multiplayerSetup = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		multiplayerSetup.addGuiElement("Back", new GuiButton(50, 50, 250, 50, 0.0f, "Back"));
		multiplayerSetup.addGuiElement("Information", new GuiLabel(0, (int) ((height / 10) * 5.5f), width, 50, 0.0f,
				"Sorry, this feature is currently unavailable.", EnumFontSize.Medium));
		multiplayerSetup.addGuiElement("Request",
				new GuiLabel(0, (height / 10) * 5, width, 50, 0.0f, "Please return to the title screen.", EnumFontSize.Medium));

		this.editorSetup = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		editorSetup.addGuiElement("Back", new GuiButton(50, 50, 250, 50, 0.0f, "Back"));
		editorSetup.addGuiElement("Information", new GuiLabel(0, (int) ((height / 10) * 5.5f), width, 50, 0.0f,
				"Sorry, this feature is currently unavailable.", EnumFontSize.Medium));
		editorSetup.addGuiElement("Request", new GuiLabel(0, (height / 10) * 5, width, 50, 0.0f, "Please return to the title screen.", EnumFontSize.Medium));

		this.options = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		options.addGuiElement("Back", new GuiButton(50, 50, 250, 50, 0.0f, "Back"));
		options.addGuiElement("Save", new GuiButton(width - 300, 50, 250, 50, 0.0f, "Save"));

		this.credits = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		credits.addGuiElement("Back", new GuiButton(50, 50, 250, 50, 0.0f, "Back"));
		credits.addGuiElement("Title", new GuiLabel(0, (int) ((height / 10) * 6.5f), width, 50, 0.0f, "Tower Defense", EnumFontSize.Large));
		credits.addGuiElement("Action", new GuiLabel(0, (int) ((height / 10) * 5.5f), width, 50, 0.0f, "created by", EnumFontSize.Medium));
		credits.addGuiElement("Name", new GuiLabel(0, (height / 10) * 5, width, 50, 0.0f, "Larai Fox", EnumFontSize.Medium));

		this.quit = new Gui(TextureLoader.getTexture("PNG", new FileInputStream(new File("res/title/background.png"))), 1.0f, 0, 0, width, height);
		quit.addGuiElement("Yes", new GuiButton((width - 250) / 2, (height / 10) * 5, 250, 50, 0.0f, "Yes"));
		quit.addGuiElement("No", new GuiButton((width - 250) / 2, (height / 10) * 4, 250, 50, 0.0f, "No"));
		quit.addGuiElement("Sure Label", new GuiLabel(0, (height / 10) * 6, width, 50, 0.0f, "Are You Sure You Want To Quit?", EnumFontSize.Medium));
	}

	private String getSplash() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("res/title/splashes.txt"));
		List<String> stringList = new ArrayList<String>();
		String splash = "splash";
		boolean flag = false;
		
		while (scanner.hasNext()) {
			String s = scanner.nextLine();

			if (s.length() > 0) {
				char c = s.charAt(0);
				if (c == '\n' || c == '/' || c == '#') {
					if (c == '#')
						flag = true;
					continue;
				} else if (flag) {
					stringList.add(s);
				}
			}
		}

		if (stringList.size() > 0) {
			splash = stringList.get(random.nextInt(stringList.size()));
			for (String s : stringList) {
				System.out.println(s);
			}
		}

		return splash;
	}

	public void setMenuState(EnumMenuState menuState) {
		this.menuState = menuState;
	}

	public void update(Game game) {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP) && !KEY_UP) {
			selectionIndex--;

			if (selectionIndex < 0)
				selectionIndex = 5;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN) && !KEY_DOWN) {
			selectionIndex++;

			if (selectionIndex > 5)
				selectionIndex = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !KEY_RETURN) {
			try {
				((GuiLabel) title.getGuiElement("Splash")).setText(getSplash());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		switch (menuState) {
		case Title:
			updateTitle(game);
			break;
		case SinglePlayerSetup:
			updateSingleplayerSetup(game);
			break;
		case MultiPlayerSetup:
			updateMultiplayerSetup();
			break;
		case EditorSetup:
			updateEditorSetup();
			break;
		case Options:
			updateOptions();
			break;
		case Credits:
			updateCredits();
			break;
		case Quit:
			if (updateQuit()) {
				game.setProgramState(EnumProgramState.Quit);
			}
			break;
		}

		cursorRotation += 2;

		updatePreviousInput();
	}

	private void updatePreviousInput() {
		MOUSE1 = Mouse.isButtonDown(0);

		KEY_UP = Keyboard.isKeyDown(Keyboard.KEY_UP);
		KEY_DOWN = Keyboard.isKeyDown(Keyboard.KEY_DOWN);

		KEY_RETURN = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
	}

	private void updateTitle(Game game) {
		if (title.getGuiElement("Single Player").isHovered()) {
			selectionIndex = 0;
			
			if (title.getGuiElement("Single Player").isClicked(MOUSE1)) {
				setMenuState(EnumMenuState.SinglePlayerSetup);
			}
		} else if (title.getGuiElement("Multi Player").isHovered()) {
			selectionIndex = 1;

			if (title.getGuiElement("Multi Player").isClicked(MOUSE1)) {
				menuState = EnumMenuState.MultiPlayerSetup;
			}
		} else if (title.getGuiElement("Level Editor").isHovered()) {
			selectionIndex = 2;

			if (title.getGuiElement("Level Editor").isClicked(MOUSE1)) {
				menuState = EnumMenuState.EditorSetup;
			}
		} else if (title.getGuiElement("Options").isHovered()) {
			selectionIndex = 3;

			if (title.getGuiElement("Options").isClicked(MOUSE1)) {
				menuState = EnumMenuState.Options;
			}
		} else if (title.getGuiElement("Credits").isHovered()) {
			selectionIndex = 4;

			if (title.getGuiElement("Credits").isClicked(MOUSE1)) {
				menuState = EnumMenuState.Credits;
			}
		} else if (title.getGuiElement("Quit").isHovered()) {
			selectionIndex = 5;

			if (title.getGuiElement("Quit").isClicked(MOUSE1)) {
				menuState = EnumMenuState.Quit;
			}
		}
	}

	private void updateSingleplayerSetup(Game game) {
		if (singleplayerSetup.getGuiElement("Start").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
			
			game.setProgramState(EnumProgramState.Game);
			game.setGameState(EnumGameState.SinglePlayer);
		}
		if (singleplayerSetup.getGuiElement("Back").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}
	}

	private void updateMultiplayerSetup() {
		if (multiplayerSetup.getGuiElement("Back").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}
	}

	private void updateEditorSetup() {
		if (editorSetup.getGuiElement("Back").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}
	}

	private void updateOptions() {
		if (options.getGuiElement("Back").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}

		if (options.getGuiElement("Save").isClicked(MOUSE1)) {
			try {
				Settings.saveSettings();
			} catch (Exception e) {
				e.printStackTrace();
			}

			menuState = EnumMenuState.Title;
		}
	}

	private void updateCredits() {
		if (credits.getGuiElement("Back").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}
	}

	private boolean updateQuit() {
		if (quit.getGuiElement("Yes").isClicked(MOUSE1)) {
			return true;
		}

		if (quit.getGuiElement("No").isClicked(MOUSE1)) {
			menuState = EnumMenuState.Title;
		}

		return false;
	}

	public void render() {
		switch (menuState) {
		case Title:
			title.render();

			if (showCursor) {
				int xLeft = width / 3 * 1;
				int xRight = width / 3 * 2;
				int y = (int) (height / 10 * (5.9 - selectionIndex));

				glColor3f(1.0f, 1.0f, 0.65f);

				cursorTexture.bind();
				glPushMatrix();
				glLoadIdentity();
				glTranslated(xLeft, y, 0);
				glRotatef(-cursorRotation, 0, 0, 1);
				glTranslated(-cursorWidth / 2, -cursorHeight / 2, 0);
				glCallList(displayListID);
				glPopMatrix();

				glPushMatrix();
				glLoadIdentity();
				glTranslated(xRight, y, 0);
				glRotatef(cursorRotation, 0, 0, 1);
				glTranslated(-cursorWidth / 2, -cursorHeight / 2, 0);
				glCallList(displayListID);
				glPopMatrix();

				glColor3f(1.0f, 1.0f, 1.0f);
			}
			break;
		case SinglePlayerSetup:
			singleplayerSetup.render();
			break;
		case MultiPlayerSetup:
			multiplayerSetup.render();
			break;
		case EditorSetup:
			editorSetup.render();
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
		}
	}
}
