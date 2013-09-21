package net.laraifox.tdlwjgl.entity;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumDirection;

public class EntityBasic extends Entity {
	public EntityBasic(int gridX, int gridY, EnumDirection initialDirection) {
		super(0, gridX, gridY, initialDirection, 0.5f, new Rectangle(8, 8, 16, 16), (short) 6, 5);
	}
}
