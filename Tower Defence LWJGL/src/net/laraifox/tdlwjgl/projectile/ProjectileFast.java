package net.laraifox.tdlwjgl.projectile;

import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.enums.EnumProjectileType;

public class ProjectileFast extends Projectile {
	private static final EnumProjectileType projectileType = EnumProjectileType.Fast;

	public ProjectileFast(Vector2 position, float theta, int waveIndex, int entityIndex) {
		super(projectileType, position, theta, waveIndex, entityIndex);
	}
}
