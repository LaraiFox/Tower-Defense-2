package net.laraifox.tdlwjgl.util;

import org.lwjgl.input.Mouse;

public class MouseHandler {
	public static boolean[] mouseButtons;
	public static float scaleX, scaleY;
	public static int mouseX, mouseY;

	public static void initialize() {
		MouseHandler.mouseButtons = new boolean[Mouse.getButtonCount()];
		MouseHandler.mouseX = Mouse.getX();
		MouseHandler.mouseY = Mouse.getY();
	}

	public static void update() {
		for (int i = 0; i < mouseButtons.length; i++)
			mouseButtons[i] = Mouse.isButtonDown(i);

		mouseX = (int) (Mouse.getX() * scaleX);
		mouseY = (int) (Mouse.getY() * scaleY);
	}

	public static boolean isButtonDown(int i) {
		return mouseButtons[i];
	}

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}
}
