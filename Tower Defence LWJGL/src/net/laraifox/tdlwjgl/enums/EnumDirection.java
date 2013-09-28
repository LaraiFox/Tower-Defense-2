package net.laraifox.tdlwjgl.enums;

import net.laraifox.lib.math.Vector2;

public enum EnumDirection {
	None(Vector2.Zero()),
	Down(Vector2.Down()),
	Right(Vector2.Right()),
	Up(Vector2.Up()),
	Left(Vector2.Left());

	private Vector2 vector;

	EnumDirection(Vector2 vector) {
		this.vector = vector;
	}

	public Vector2 getVector() {
		return vector;
	}
}
