package net.laraifox.tdlwjgl.util;

import net.laraifox.lib.math.Vector3f;
import net.laraifox.tdlwjgl.enums.EnumFontSize;

public class GameString {
	public String text;
	public int x, y;
	public EnumFontSize fontsize;
	public int alignment;
	public Vector3f color;

	public GameString(String text, int x, int y, EnumFontSize fontsize, int alignment, Vector3f color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.fontsize = fontsize;
		this.alignment = alignment;
		this.color = color;
	}
}
