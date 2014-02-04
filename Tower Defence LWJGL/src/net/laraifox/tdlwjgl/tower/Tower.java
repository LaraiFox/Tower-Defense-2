package net.laraifox.tdlwjgl.tower;

import net.laraifox.lib.math.Vector2f;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumTowerType;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.level.Wave;
import net.laraifox.tdlwjgl.level.WaveManager;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class Tower {
	private EnumTowerType towerType;
	private int tiledX, tiledY;
	private int baseDisplayListID, turretDisplayListID;
	private Vector2f position;
	private Vector2f center;
	private float theta;

	private int firingRate;
	private int firingRadius;
	private int cost;

	private int targetEntity;
	private int targetWave;
	private int lastFired;

	public Tower(EnumTowerType towerType, int tiledX, int tiledY, int baseDisplayListID, int turretDisplayListID) {
		this.towerType = towerType;
		this.tiledX = tiledX;
		this.tiledY = tiledY;
		this.baseDisplayListID = baseDisplayListID;
		this.turretDisplayListID = turretDisplayListID;

		this.position = new Vector2f(tiledX * Tile.getTileSize(), tiledY * Tile.getTileSize());
		this.center = new Vector2f(position).add(new Vector2f(getWidth(), getHeight()).scale(0.5f));
		this.theta = -90;

		this.firingRate = towerType.getBaseFiringRate();
		this.firingRadius = towerType.getBaseFiringRadius();
		this.cost = towerType.getBaseCost();

		this.targetEntity = -1;
		this.targetWave = -1;
		this.lastFired = 0;
	}

	protected static int[] initialize(EnumTowerType towerType) {
		int baseID = GL11.glGenLists(1);
		int turretID = GL11.glGenLists(1);

		int tx = towerType.getTowerBaseID() % 8;
		int ty = (towerType.getTowerBaseID() - tx) / 8;

		float left = SpriteSheet.Towers.getLeftOfTile(tx);
		float right = SpriteSheet.Towers.getRightOfTile(tx, towerType.getTiledWidth());
		float top = SpriteSheet.Towers.getTopOfTile(ty, towerType.getTiledHeight());
		float bottom = SpriteSheet.Towers.getBottomOfTile(ty);

		int halfWidth = (towerType.getTiledWidth() * Tile.getTileSize()) / 2;
		int halfHeight = (towerType.getTiledHeight() * Tile.getTileSize()) / 2;

		GL11.glNewList(baseID, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(left, top);
		GL11.glVertex2i(-halfWidth, -halfHeight);
		GL11.glTexCoord2f(right, top);
		GL11.glVertex2i(halfWidth, -halfHeight);
		GL11.glTexCoord2f(right, bottom);
		GL11.glVertex2i(halfWidth, halfHeight);
		GL11.glTexCoord2f(left, bottom);
		GL11.glVertex2i(-halfWidth, halfHeight);
		GL11.glEnd();
		GL11.glEndList();

		tx = towerType.getTowerTurretID() % 8;
		ty = (towerType.getTowerTurretID() - tx) / 8;

		left = SpriteSheet.Towers.getLeftOfTile(tx);
		right = SpriteSheet.Towers.getRightOfTile(tx, towerType.getTiledWidth());
		top = SpriteSheet.Towers.getTopOfTile(ty, towerType.getTiledHeight());
		bottom = SpriteSheet.Towers.getBottomOfTile(ty);

		GL11.glNewList(turretID, GL11.GL_COMPILE);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(left, top);
		GL11.glVertex2i(-halfWidth, -halfHeight);
		GL11.glTexCoord2f(right, top);
		GL11.glVertex2i(halfWidth, -halfHeight);
		GL11.glTexCoord2f(right, bottom);
		GL11.glVertex2i(halfWidth, halfHeight);
		GL11.glTexCoord2f(left, bottom);
		GL11.glVertex2i(-halfWidth, halfHeight);
		GL11.glEnd();
		GL11.glEndList();

		return new int[] {
				baseID, turretID
		};
	}

	public boolean canFire(GameTimer gameTime) {
		if ((gameTime.getTicks() - lastFired >= firingRate) && (targetWave >= 0) && (targetEntity >= 0)) {
			lastFired = gameTime.getTicks();
			return true;
		}

		return false;
	}

	public void update(GameTimer gameTime, WaveManager waveManager) {
		if ((targetWave < 0 || targetEntity < 0) || ((int) position.distanceTo(waveManager.getEntityAt(targetWave, targetEntity).getPosition()) > firingRadius) || !waveManager.getEntityAt(targetWave, targetEntity).isAlive()) {
			int nextEntity = -1;
			int nextWave = -1;
			int nextDistance = -1;

			for (int i = 0; i < waveManager.getWaveCount(); i++) {
				Wave w = waveManager.getWaveAt(i);
				for (int j = 0; j < w.getLength(); j++) {
					Entity e = w.getEntityAt(j);
					int distance = (int) position.distanceTo(e.getPosition());
					if ((e.isAlive()) && (distance <= firingRadius) && (distance < nextDistance || nextEntity < 0 || nextWave < 0)) {
						nextWave = i;
						nextEntity = j;
						nextDistance = distance;
					}
				}
			}

			targetWave = nextWave;
			targetEntity = nextEntity;
		}

		if (targetWave >= 0 && targetEntity >= 0) {
			double dx = waveManager.getEntityAt(targetWave, targetEntity).getPosition().getX() - position.getX();
			double dy = waveManager.getEntityAt(targetWave, targetEntity).getPosition().getY() - position.getY();
			theta = (float) Math.toDegrees(Math.atan2(dy, dx));
		}
	}

	public void render() {
		SpriteSheet.Towers.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslated(center.getX(), center.getY(), 0);
		GL11.glCallList(baseDisplayListID);
		GL11.glRotatef(theta, 0, 0, 1);
		GL11.glCallList(turretDisplayListID);
		GL11.glPopMatrix();
	}

	public static void renderGhost(EnumTowerType towerType, int x, int y, boolean isValidLocation) {
		int baseID = 0;
		int turretID = 0;
		switch (towerType) {
		case Basic:
			baseID = TowerBasic.getBaseDisplayListID();
			turretID = TowerBasic.getTurretDisplayListID();
			break;
		case Fast:
			baseID = TowerFast.getBaseDisplayListID();
			turretID = TowerFast.getTurretDisplayListID();
			break;
		default:
			return;
		}

		SpriteSheet.Towers.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_ADD);
		if (isValidLocation)
			GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.9f);
		else
			GL11.glColor4f(0.6f, 0.0f, 0.0f, 0.9f);
		GL11.glTranslated(x + (towerType.getTiledWidth() * Tile.getTileSize()) / 2, y + (towerType.getTiledHeight() * Tile.getTileSize()) / 2, 0);
		GL11.glCallList(baseID);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glCallList(turretID);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GL11.glPopMatrix();
	}

	public EnumTowerType getTowerType() {
		return towerType;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getCenter() {
		return center;
	}

	public int getTileX() {
		return tiledX;
	}

	public int getTileY() {
		return tiledY;
	}

	public int getFiringRadius() {
		return firingRadius;
	}

	public int getCost() {
		return cost;
	}

	public int getWidth() {
		return Tile.getTileSize() * towerType.getTiledWidth();
	}

	public int getHeight() {
		return Tile.getTileSize() * towerType.getTiledHeight();
	}

	public float getTheta() {
		return theta;
	}

	public int getTargetEntity() {
		return targetEntity;
	}

	public int getTargetWave() {
		return targetWave;
	}
}
