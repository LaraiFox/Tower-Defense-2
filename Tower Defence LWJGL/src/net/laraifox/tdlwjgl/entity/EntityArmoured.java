package net.laraifox.tdlwjgl.entity;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumDirection;

public class EntityArmoured extends Entity {
	public EntityArmoured(int gridX, int gridY, EnumDirection initialDirection) {
		super(8, gridX, gridY, initialDirection, 0.3f, new Rectangle(8, 8, 16, 16), (short) 6, 8);
	}
}
