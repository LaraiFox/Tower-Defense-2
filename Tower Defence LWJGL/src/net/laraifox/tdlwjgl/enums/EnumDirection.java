package net.laraifox.tdlwjgl.enums;

import net.laraifox.lib.math.Vector2;

public enum EnumDirection {
	None(Vector2.Zero(), (byte) 0), 
	Up(Vector2.Up(), (byte) 2), 
	Down(Vector2.Down(), (byte) 0), 
	Left(Vector2.Left(), (byte) 3), 
	Right(Vector2.Right(), (byte) 1);
	
	private Vector2 v;
	private byte i;
	
	EnumDirection(Vector2 v, byte i) {
		this.v = v;
		this.i = i;
	}
	
	public Vector2 getVector() {
		return v;
	}

	public byte getTextureIndex() {
		return i;
	}
}
