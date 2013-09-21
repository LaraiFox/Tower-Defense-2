package net.laraifox.tdlwjgl.enums;

public enum EnumTowerType {
	None(),
	Basic(0, 8, 1, 1, 50, 96, 60),
	Fast(1, 9, 1, 1, 80, 96, 20);

	private int towerBaseID;
	private int towerTurretID;
	private int tiledWidth;
	private int tiledHeight;
	private int baseCost;
	private int baseFiringRadius;
	private int baseFiringRate;

	private EnumTowerType() {
		this(0, 0, 0, 0, 0, 0, 0);
	}

	private EnumTowerType(int towerBaseID, int towerTurretID, int tiledWidth, int tiledHeight, int baseCost, int baseFiringRadius, int baseFiringRate) {
		this.towerBaseID = towerBaseID;
		this.towerTurretID = towerTurretID;
		this.tiledWidth = tiledWidth;
		this.tiledHeight = tiledHeight;
		this.baseCost = baseCost;
		this.baseFiringRadius = baseFiringRadius;
		this.baseFiringRate = baseFiringRate;
	}

	public int getTowerBaseID() {
		return towerBaseID;
	}

	public int getTowerTurretID() {
		return towerTurretID;
	}

	public int getTiledWidth() {
		return tiledWidth;
	}

	public int getTiledHeight() {
		return tiledHeight;
	}

	public int getBaseCost() {
		return baseCost;
	}

	public int getBaseFiringRadius() {
		return baseFiringRadius;
	}

	public int getBaseFiringRate() {
		return baseFiringRate;
	}
}
