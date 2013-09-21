package net.laraifox.tdlwjgl.entity;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumDirection;

public class EntityStrong extends Entity {
	public EntityStrong(int gridX, int gridY, EnumDirection initialDirection) {
		super(12, gridX, gridY, initialDirection, 0.5f,  new Rectangle(4, 4, 24, 24), (short) 6, 50);
	}
}
