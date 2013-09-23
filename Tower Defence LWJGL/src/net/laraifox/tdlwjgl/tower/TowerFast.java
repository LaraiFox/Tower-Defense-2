package net.laraifox.tdlwjgl.tower;

import net.laraifox.tdlwjgl.enums.EnumTowerType;

public class TowerFast extends Tower {
	private static final EnumTowerType towerType = EnumTowerType.Fast;
	private static int baseDisplayListID, turretDisplayListID;

	public TowerFast(int tiledX, int tiledY) {
		super(towerType, tiledX, tiledY, baseDisplayListID, turretDisplayListID);
	}

	public static void initialize() {
		int[] displayListIDs = Tower.initialize(towerType);
		TowerFast.baseDisplayListID = displayListIDs[0];
		TowerFast.turretDisplayListID = displayListIDs[1];
	}

	protected static int getBaseDisplayListID() {
		return baseDisplayListID;
	}

	protected static int getTurretDisplayListID() {
		return turretDisplayListID;
	}
}
