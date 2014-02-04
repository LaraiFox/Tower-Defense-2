package net.laraifox.tdlwjgl.enums;

import net.laraifox.lib.math.Vector2f;

public enum EnumDirection {
	None(Vector2f.Zero()),
	Down(Vector2f.Down()),
	Right(Vector2f.Right()),
	Up(Vector2f.Up()),
	Left(Vector2f.Left());

	private Vector2f vector;

	private EnumDirection(Vector2f vector) {
		this.vector = vector;
	}

	public Vector2f getVector() {
		return vector;
	}
}
