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
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.guielement.GuiButton;
import net.laraifox.tdlwjgl.guielement.GuiLabel;
import net.laraifox.tdlwjgl.main.MenuManager;

public class GuiCredits extends Gui {
	private GuiButton back;
	private GuiLabel title;
	private GuiLabel action;
	private GuiLabel name;

	public GuiCredits(int width, int height) {
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
		this.back = new GuiButton((width - 250) / 2, 50, 250, 50, "Back");
		this.title = new GuiLabel(0, (int) ((height / 10) * 6.5f), width, 50, "Tower Defense", EnumFontSize.Large);
		this.action = new GuiLabel(0, (int) ((height / 10) * 5.5f), width, 50, "created by", EnumFontSize.Medium);
		this.name = new GuiLabel(0, (height / 10) * 5, width, 50, "Larai Fox", EnumFontSize.Medium);
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
		texture.bindTexture();
		super.render();

		back.render();
		title.render();
		action.render();
		name.render();

		glPopMatrix();
	}
}
