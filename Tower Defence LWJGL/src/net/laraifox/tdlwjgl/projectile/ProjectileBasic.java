package net.laraifox.tdlwjgl.projectile;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.enums.EnumTowerType;

public class ProjectileBasic extends Projectile {
	public ProjectileBasic(EnumTowerType projectileType, double x, double y, float theta, double range, int waveIndex, int entityIndex) {
		super(projectileType, x, y, theta, range, new Rectangle(1, 1, 4, 4), waveIndex, entityIndex, 2);

		this.id = 0;
	}
}
