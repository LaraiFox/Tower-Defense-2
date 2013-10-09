package net.laraifox.tdlwjgl.guielement;

import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.graphics.TextureLoader;
import net.laraifox.tdlwjgl.enums.EnumButtonState;

public class GuiListBox extends GuiElement {
	private Texture contentBoxTexture;

	private int displayListID;
	private List<String> contents;
	private boolean opened;

	public GuiListBox(int x, int y, int width, int height) {
		super("res/gui/listbox.png", x, y, width, height);

		try {
			this.contentBoxTexture = TextureLoader.getTexture(new FileInputStream(new File("res/gui/listbox_content.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.contents = new ArrayList<String>();
		this.opened = false;

		createDisplayList();
	}

	public void addContentString(String s) {
		contents.add(s);
	}

	protected void createDisplayList() {
		/*
		 * Need to create at least two display lists.
		 * 
		 * One display list will be for the bottom edge of the content box and the other will be for the central sections. The central sections can then be
		 * reused as many times as necessary to allow all the lines of test to fit.
		 * 
		 * - I may need a third display list for the top edge of the content box. I will need to check the image file when I have time.
		 */
	}

	public void update() {
		/*
		 * No updating is required for the GuiListBox class.
		 */
	}

	public void render() {
		if (buttonState == EnumButtonState.Pressed)
			glColor3f(0.5f, 0.5f, 0.5f);
		else if (buttonState == EnumButtonState.Hovered)
			glColor3f(1.0f, 1.0f, 0.65f);

		super.render();

		glColor3f(1.0f, 1.0f, 1.0f);

		if (opened) {
			glPopMatrix();
			glTranslatef(bounds.x, bounds.y - bounds.height, 0);
			contentBoxTexture.bindTexture();
			glCallList(displayListID);
			glPushMatrix();
		}
	}
}
