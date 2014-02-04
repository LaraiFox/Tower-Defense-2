package net.laraifox.tdlwjgl.util;

import static org.lwjgl.opengl.GL11.glColor3f;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import net.laraifox.lib.math.Vector3f;
import net.laraifox.lib.text.VectorFont;
import net.laraifox.tdlwjgl.enums.EnumFontSize;

public class StringRenderer {
	private static List<GameString> strings = new ArrayList<GameString>();

	private static VectorFont smallFont;
	private static VectorFont mediumFont;
	private static VectorFont largeFont;

	public static void initialize() {
		StringRenderer.smallFont = new VectorFont(new Font("Arial", Font.BOLD, 15), true);
		StringRenderer.mediumFont = new VectorFont(new Font("Arial", Font.BOLD, 25), true);
		StringRenderer.largeFont = new VectorFont(new Font("Arial", Font.BOLD, 35), true);
	}

	public static void render() {
		for (GameString string : strings) {
			glColor3f(string.color.getX(), string.color.getY(), string.color.getZ());
			render(string.text, string.x, string.y, string.fontsize, string.alignment);
			glColor3f(1.0f, 1.0f, 1.0f);
		}
	}

	public static void render(String text, int x, int y, EnumFontSize fontsize, int alignment) {
		switch (fontsize) {
		case Small:
			smallFont.drawString(x, y, text, 1.0f, 1.0f, alignment);
			break;
		case Medium:
			mediumFont.drawString(x, y, text, 1.0f, 1.0f, alignment);
			break;
		case Large:
			largeFont.drawString(x, y, text, 1.0f, 1.0f, alignment);
			break;
		default:
			break;
		}
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

	public static void addString(String s, int x, int y, EnumFontSize fontsize, int alignment) {
		strings.add(new GameString(s, x, y, fontsize, alignment, new Vector3f(1.0f, 1.0f, 1.0f)));
	}

	public static void addString(String s, int x, int y, EnumFontSize fontsize, int alignment, Vector3f color) {
		strings.add(new GameString(s, x, y, fontsize, alignment, color));
	}

	public static void changeString(String s, int x, int y, EnumFontSize fontsize, int alignment, int i) {
		if (i < strings.size())
			strings.set(i, new GameString(s, x, y, fontsize, alignment, new Vector3f(1.0f, 1.0f, 1.0f)));
	}

	public static void changeString(String s, int x, int y, EnumFontSize fontsize, int alignment, Vector3f color, int i) {
		if (i < strings.size())
			strings.set(i, new GameString(s, x, y, fontsize, alignment, color));
	}

	public static void appendString(String s, int i) {
		if (i < strings.size()) {
			strings.set(i, new GameString(strings.get(i).text + s, strings.get(i).x, strings.get(i).y, strings.get(i).fontsize, strings.get(i).alignment, strings.get(i).color));
		}
	}
}
