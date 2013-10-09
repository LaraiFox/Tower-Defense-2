package net.laraifox.tdlwjgl.enums;

public enum EnumSettingResolution {
	Tiny(800, 450), Small(1024, 576), Normal(1280, 720), Large(1600, 900), Huge(1920, 1080);

	private static final int size = EnumSettingResolution.values().length;
	private int width, height;

	private EnumSettingResolution(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public static int getValueCount() {
		return size;
	}
}
