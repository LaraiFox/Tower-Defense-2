package net.laraifox.tdlwjgl.enums;

public enum EnumSettingFramerate {
	PowerSaver(30),
	Standard(60),
	HighPerformance(120),
	MaximumFramerate(600);
	
	private int framerate;

	EnumSettingFramerate(int framerate) {
		this.framerate = framerate;
	}

	public int getFramerate() {
		return framerate;
	}
}
