package net.laraifox.tdlwjgl.entity;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumDirection;

public class EntityFast extends Entity {
	public EntityFast(int gridX, int gridY, EnumDirection initialDirection) {
		super(4, gridX, gridY, initialDirection, 1.2f, new Rectangle(8, 8, 16, 16), (short) 9, 3);
	}
}
