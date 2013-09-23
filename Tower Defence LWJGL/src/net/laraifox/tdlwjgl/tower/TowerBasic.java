package net.laraifox.tdlwjgl.tower;

import net.laraifox.tdlwjgl.enums.EnumTowerType;

public class TowerBasic extends Tower {
	private static final EnumTowerType towerType = EnumTowerType.Basic;
	private static int baseDisplayListID, turretDisplayListID;

	public TowerBasic(int tiledX, int tiledY) {
		super(towerType, tiledX, tiledY, baseDisplayListID, turretDisplayListID);
	}

	public static void initialize() {
		int[] displayListIDs = Tower.initialize(towerType);
		TowerBasic.baseDisplayListID = displayListIDs[0];
		TowerBasic.turretDisplayListID = displayListIDs[1];
	}

	protected static int getBaseDisplayListID() {
		return baseDisplayListID;
	}

	protected static int getTurretDisplayListID() {
		return turretDisplayListID;
	}
}
