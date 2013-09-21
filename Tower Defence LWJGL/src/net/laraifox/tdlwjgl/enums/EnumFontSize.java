package net.laraifox.tdlwjgl.enums;

public enum EnumFontSize {
	Small(8, 8), Medium(16, 16), Large(32, 32), None(0, 0);

	private int width, height;

	EnumFontSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
