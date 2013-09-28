package net.laraifox.tdlwjgl.projectile;

import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.enums.EnumProjectileType;

public class ProjectileBasic extends Projectile {
	private static final EnumProjectileType PROJECTILE_TYPE = EnumProjectileType.Basic;

	public ProjectileBasic(Vector2 position, float theta, int waveIndex, int entityIndex) {
		super(PROJECTILE_TYPE, position, theta, waveIndex, entityIndex);
	}
}
