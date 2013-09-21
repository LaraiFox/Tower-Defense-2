package net.laraifox.tdlwjgl.util;

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
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.util.ArrayList;
import java.util.List;

import net.laraifox.lib.graphics.Color3f;
import net.laraifox.tdlwjgl.enums.EnumFontSize;

public class StringRenderer {
	private static List<GameString> strings = new ArrayList<GameString>();
	private static final String font = "" + //
			"ABCDEFGHIJKLMNOP" + //
			"QRSTUVWXYZ<>?:\"©" + //
			"abcdefghijklmnop" + //
			"qrstuvwxyz,./;' " + //
			"1234567890+-*\\=_" + //
			"()[]{}@&!%#~£$¢Û" + //
			"" + //
			"";
	private static int[][] displayListIDs;

	public static void initialize() {
		displayListIDs = new int[3][256];

		for (int i = 0; i < 256; i++) {
			int tx = i % 16;
			int ty = (i - tx) / 16;

			float left = SpriteSheet.Font_Small.getLeftOfTile(tx);
			float right = SpriteSheet.Font_Small.getRightOfTile(tx, 1);
			float top = SpriteSheet.Font_Small.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Font_Small.getBottomOfTile(ty);

			displayListIDs[EnumFontSize.Small.ordinal()][i] = glGenLists(1);

			glNewList(displayListIDs[EnumFontSize.Small.ordinal()][i], GL_COMPILE);
			{
				glBegin(GL_QUADS);
				{
					glTexCoord2f(left, top);
					glVertex2i(0, 0);

					glTexCoord2f(right, top);
					glVertex2i(EnumFontSize.Small.getWidth(), 0);

					glTexCoord2f(right, bottom);
					glVertex2i(EnumFontSize.Small.getWidth(), EnumFontSize.Small.getHeight());

					glTexCoord2f(left, bottom);
					glVertex2i(0, EnumFontSize.Small.getHeight());
				}
				glEnd();
			}
			glEndList();
		}

		for (int i = 0; i < 256; i++) {
			int tx = i % 16;
			int ty = (i - tx) / 16;

			float left = SpriteSheet.Font_Small.getLeftOfTile(tx);
			float right = SpriteSheet.Font_Small.getRightOfTile(tx, 1);
			float top = SpriteSheet.Font_Small.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Font_Small.getBottomOfTile(ty);

			displayListIDs[EnumFontSize.Medium.ordinal()][i] = glGenLists(1);

			glNewList(displayListIDs[EnumFontSize.Medium.ordinal()][i], GL_COMPILE);
			{
				glBegin(GL_QUADS);
				{
					glTexCoord2f(left, top);
					glVertex2i(0, 0);

					glTexCoord2f(right, top);
					glVertex2i(EnumFontSize.Medium.getWidth(), 0);

					glTexCoord2f(right, bottom);
					glVertex2i(EnumFontSize.Medium.getWidth(), EnumFontSize.Medium.getHeight());

					glTexCoord2f(left, bottom);
					glVertex2i(0, EnumFontSize.Medium.getHeight());
				}
				glEnd();
			}
			glEndList();
		}

		for (int i = 0; i < 256; i++) {
			int tx = i % 16;
			int ty = (i - tx) / 16;

			float left = SpriteSheet.Font_Small.getLeftOfTile(tx);
			float right = SpriteSheet.Font_Small.getRightOfTile(tx, 1);
			float top = SpriteSheet.Font_Small.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Font_Small.getBottomOfTile(ty);

			displayListIDs[EnumFontSize.Large.ordinal()][i] = glGenLists(1);

			glNewList(displayListIDs[EnumFontSize.Large.ordinal()][i], GL_COMPILE);
			{
				glBegin(GL_QUADS);
				{
					glTexCoord2f(left, top);
					glVertex2i(0, 0);

					glTexCoord2f(right, top);
					glVertex2i(EnumFontSize.Large.getWidth(), 0);

					glTexCoord2f(right, bottom);
					glVertex2i(EnumFontSize.Large.getWidth(), EnumFontSize.Large.getHeight());

					glTexCoord2f(left, bottom);
					glVertex2i(0, EnumFontSize.Large.getHeight());
				}
				glEnd();
			}
			glEndList();
		}

	}

	public static void render() {
		for (GameString string : strings) {
			glColor3f(string.color.getRed(), string.color.getGreen(), string.color.getBlue());
			render(string.text, string.x, string.y, string.fontsize);
			glColor3f(1.0f, 1.0f, 1.0f);
		}
	}

	public static void render(String s, int x, int y, EnumFontSize fontsize) {
		glPushMatrix();
		switch (fontsize) {
		case Small:
			SpriteSheet.Font_Small.bindSheetTexture();
			break;
		case Medium:
			SpriteSheet.Font_Medium.bindSheetTexture();
			break;
		case Large:
			SpriteSheet.Font_Large.bindSheetTexture();
			break;
		case None:
			return;
		}

		for (int i = 0; i < s.length(); i++) {
			int j = font.indexOf(s.charAt(i));

			glLoadIdentity();
			glTranslatef(x + i * fontsize.getWidth(), y, 0);
			glCallList(displayListIDs[fontsize.ordinal()][j]);
		}
		glPopMatrix();
	}

	public static void clear() {
		strings.clear();
	}

	public static int getLength() {
		return strings.size();
	}

	public static String getStringAt(int i) {
		return strings.get(i).text;
	}

	public static String[] getStrings() {
		return (String[]) strings.toArray();
	}

	public static void addString(String s, int x, int y, EnumFontSize fontsize) {
		strings.add(new GameString(s, x, y, fontsize, new Color3f(1.0f, 1.0f, 1.0f)));
	}

	public static void addString(String s, int x, int y, EnumFontSize fontsize, Color3f color) {
		strings.add(new GameString(s, x, y, fontsize, color));
	}

	public static void changeString(String s, int x, int y, EnumFontSize fontsize, int i) {
		if (i < strings.size())
			strings.set(i, new GameString(s, x, y, fontsize, new Color3f(1.0f, 1.0f, 1.0f)));
	}

	public static void changeString(String s, int x, int y, EnumFontSize fontsize, Color3f color, int i) {
		if (i < strings.size())
			strings.set(i, new GameString(s, x, y, fontsize, color));
	}

	public static void appendString(String s, int i) {
		if (i < strings.size()) {
			strings.set(i, new GameString(strings.get(i).text + s, strings.get(i).x, strings.get(i).y, strings.get(i).fontsize, strings.get(i).color));
		}
	}
}
