package net.laraifox.tdlwjgl.level;

import net.laraifox.tdlwjgl.enums.EnumDirection;

public class Waypoint {
	private int x, y;
	private EnumDirection direction;

	public Waypoint(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		switch (direction) {
		case 0:
			this.direction = EnumDirection.Right;
			break;
		case 1:
			this.direction = EnumDirection.Down;
			break;
		case 2:
			this.direction = EnumDirection.Left;
			break;
		case 3:
			this.direction = EnumDirection.Up;
			break;
		case 4:
			this.direction = EnumDirection.None;
			break;
		default:
			this.direction = EnumDirection.None;
			break;
		}
	}

	public Waypoint(int x, int y, EnumDirection direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public EnumDirection getDirection() {
		return direction;
	}
}
