package net.laraifox.tdlwjgl.projectile;

import net.laraifox.tdlwjgl.enums.EnumProjectileType;

public class ProjectileBasic extends Projectile {
	private static final EnumProjectileType projectileType = EnumProjectileType.Basic;

	public ProjectileBasic(double x, double y, float theta, int waveIndex, int entityIndex) {
		super(projectileType, x, y, theta, waveIndex, entityIndex);
	}
}
