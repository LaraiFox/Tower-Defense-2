package net.laraifox.tdlwjgl.enums;

public enum EnumProjectileType {
	None(),
	Basic(0, 2, (int) (EnumTowerType.Basic.getBaseFiringRadius() * 1.2)),
	Fast(1, 1, (int) (EnumTowerType.Fast.getBaseFiringRadius() * 1.2));

	private int projectileID;
	private int baseDamage;
	private int baseRange;

	private EnumProjectileType() {
		this(0, 0, 0);
	}

	private EnumProjectileType(int projectileID, int baseDamage, int baseRange) {
		this.projectileID = projectileID;
		this.baseDamage = baseDamage;
		this.baseRange = baseRange;
	}

	public int getProjectileID() {
		return projectileID;
	}

	public int getBaseDamage() {
		return baseDamage;
	}

	public int getBaseRange() {
		return baseRange;
	}
}
