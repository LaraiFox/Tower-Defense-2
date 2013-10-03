package net.laraifox.tdlwjgl.gui;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.guielement.GuiTinyButton;
import net.laraifox.tdlwjgl.main.MenuManager;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;

public class GuiLevelEditorSetup extends Gui {
	private GuiButton back;
	private GuiButton create;
	private GuiLabel information;
	private GuiLabel request;
	private GuiTinyButton test;

	public GuiLevelEditorSetup(int width, int height) {
		super(0, 0, width, height);

		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		this.back = new GuiButton(50, 50, 250, 50, "Back");
		this.create = new GuiButton(width - 300, 50, 250, 50, "Create");
		this.information = new GuiLabel(0, (int) ((height / 10) * 7.5f), width, 50, "Sorry, this feature is currently unavailable.", EnumFontSize.Medium);
		this.request = new GuiLabel(0, (height / 10) * 7, width, 50, "Please return to the title screen.", EnumFontSize.Medium);
		this.test = new GuiTinyButton((width - 150) / 2, (height / 10) * 5, 150, 25, "GuiTinyButton");
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		back.update();
		create.update();
		test.update();

		if (back.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		} else if (create.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
			game.setProgramState(EnumProgramState.Editor);
			game.initializeLevelEditor();
		}

		if (back.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = back.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (create.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = create.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		glColor3f(0.0f, 0.0f, 0.0f);
		glBindTexture(GL_TEXTURE_2D, 0);
		super.render();
		glColor3f(1.0f, 1.0f, 1.0f);

		back.render();
		create.render();
		information.render();
		request.render();
		test.render();

		glPopMatrix();
	}
}
