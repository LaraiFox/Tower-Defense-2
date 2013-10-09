package net.laraifox.tdlwjgl.gui;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumButtonState;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.main.MenuManager;
import net.laraifox.tdlwjgl.main.TowerDefenseGame;

import org.lwjgl.opengl.GL11;

public class GuiLevelEditorSetup extends Gui {
	private GuiButton backButton;
	private GuiButton createButton;
	private GuiLabel information;
	private GuiLabel request;

	public GuiLevelEditorSetup(int width, int height) {
		super(0, 0, width, height);

		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		this.backButton = new GuiButton(50, 50, 250, 50, "Back");
		this.createButton = new GuiButton(width - 300, 50, 250, 50, "Create");
		this.information = new GuiLabel(0, (int) ((height / 10) * 7.5f), width, 50, "Sorry, this feature is currently unavailable.", EnumFontSize.Medium);
		this.request = new GuiLabel(0, (height / 10) * 7, width, 50, "Please return to the title screen.", EnumFontSize.Medium);
	}

	public void update(TowerDefenseGame game, MenuManager manager) {
		backButton.update();
		createButton.update();

		if (backButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		} else if (createButton.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
			game.setProgramState(EnumProgramState.Editor);
			game.initializeLevelEditor();
		}

		if (backButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = backButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		} else if (createButton.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = createButton.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		GL11.glPushMatrix();
		GL11.glColor3f(0.0f, 0.0f, 0.0f);
		super.render();
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

		request.render();

		backButton.render();
		createButton.render();
		information.render();
		GL11.glPopMatrix();
	}
}
