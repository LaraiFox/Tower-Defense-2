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
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.main.MenuManager;

public class GuiMultiPlayerSetup extends Gui {
	private GuiButton back;
	private GuiLabel information;
	private GuiLabel request;

	public GuiMultiPlayerSetup(int width, int height) {
		super(0, 0, width, height);

		initializeGuiElements();
	}

	protected void initializeGuiElements() {
		this.back = new GuiButton(50, 50, 250, 50, 0.0f, "Back");
		this.information = new GuiLabel(0, (int) ((height / 10) * 7.5f), width, 50, 0.0f, "Sorry, this feature is currently unavailable.", EnumFontSize.Medium);
		this.request = new GuiLabel(0, (height / 10) * 7, width, 50, 0.0f, "Please return to the title screen.", EnumFontSize.Medium);
	}

	public void update(MenuManager manager) {
		back.update();

		if (back.getState() == EnumButtonState.Clicked) {
			manager.setMenuState(EnumMenuState.Title);
		}

		if (back.getState() == EnumButtonState.Hovered) {
			Rectangle bounds = back.getBounds();
			manager.setCursorLocation((int) bounds.getCenterX(), (int) bounds.getCenterY(), bounds.width);
		}
	}

	public void render() {
		glPushMatrix();
		glColor3f(0.0f, 0.0f, 0.0f);
		glBindTexture(GL_TEXTURE_2D, 0);
		super.render();
		glColor3f(1.0f,  1.0f, 1.0f);

		back.render();
		information.render();
		request.render();
		glPopMatrix();
	}
}
