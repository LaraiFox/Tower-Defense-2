package net.laraifox.tdlwjgl.projectile;

import net.laraifox.tdlwjgl.enums.EnumProjectileType;

public class ProjectileFast extends Projectile {
	private static final EnumProjectileType projectileType = EnumProjectileType.Fast;

	public ProjectileFast(double x, double y, float theta, int waveIndex, int entityIndex) {
		super(projectileType, x, y, theta, waveIndex, entityIndex);
	}
}
