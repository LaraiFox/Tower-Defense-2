package net.laraifox.tdlwjgl.util;

import net.laraifox.lib.text.VectorFont;
import net.laraifox.tdlwjgl.enums.EnumFontSize;

import org.lwjgl.input.Mouse;

public class MouseHandler {
	private static boolean[] mouseButtons;
	private static float scaleX, scaleY;
	private static int mouseX, mouseY;

	public static void initialize() {
		MouseHandler.initialize(1.0f, 1.0f);
	}

	public static void initialize(float scaleX, float scaleY) {
		MouseHandler.mouseButtons = new boolean[Mouse.getButtonCount()];
		MouseHandler.scaleX = scaleX;
		MouseHandler.scaleY = scaleY;
		MouseHandler.mouseX = Mouse.getX();
		MouseHandler.mouseY = Mouse.getY();
	}

	public static void update() {
		for (int i = 0; i < mouseButtons.length; i++)
			mouseButtons[i] = Mouse.isButtonDown(i);

		mouseX = (int) (Mouse.getX() * scaleX);
		mouseY = (int) (Mouse.getY() * scaleY);
		StringRenderer.addString("Mouse: " + Mouse.getX() + " (" + mouseX + "), " + Mouse.getY() + " (" + mouseY + ")", 10, 10, EnumFontSize.Medium, VectorFont.ALIGN_LEFT);
	}

	public static boolean isButtonDown(int i) {
		return mouseButtons[i];
	}

	protected static float getScaleX() {
		return scaleX;
	}

	protected static void setScaleX(float scaleX) {
		MouseHandler.scaleX = scaleX;
	}

	protected static float getScaleY() {
		return scaleY;
	}

	protected static void setScaleY(float scaleY) {
		MouseHandler.scaleY = scaleY;
	}

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}
}
