package net.laraifox.tdlwjgl.enums;

import java.awt.Rectangle;

public enum EnumEntityType {
	Basic(0, 5, 6, 0.5f, new Rectangle(8, 8, 16, 16)),
	Fast(4, 3, 9, 1.2f, new Rectangle(8, 8, 16, 16)),
	Armoured(8, 8, 6, 0.3f, new Rectangle(8, 8, 16, 16)),
	Strong(12, 50, 20, 0.5f, new Rectangle(4, 4, 24, 24));

	private int entityID;
	private int health;
	private int reward;
	private float speed;
	private Rectangle hitbox;

	private EnumEntityType(int entityID, int health, int reward, float speed, Rectangle hitbox) {
		this.entityID = entityID;
		this.health = health;
		this.reward = reward;
		this.speed = speed;
		this.hitbox = hitbox;
	}

	public int getEntityID() {
		return entityID;
	}

	public int getHealth() {
		return health;
	}

	public int getReward() {
		return reward;
	}

	public float getSpeed() {
		return speed;
	}

	public Rectangle getHitbox() {
		return hitbox;
	}
}
