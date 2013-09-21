package net.laraifox.tdlwjgl.tower;

import net.laraifox.tdlwjgl.enums.EnumTowerType;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

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

	public static void renderGhost(int x, int y, boolean isValidLocation) {
		SpriteSheet.Towers.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_ADD);
		if (isValidLocation)
			GL11.glColor4f(0.2f, 0.2f, 0.2f, 0.9f);
		else
			GL11.glColor4f(0.6f, 0.0f, 0.0f, 0.9f);
		GL11.glTranslated(x + (towerType.getTiledWidth() * Tile.getTileSize()) / 2, y + (towerType.getTiledHeight() * Tile.getTileSize()) / 2, 0);
		GL11.glCallList(baseDisplayListID);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glCallList(turretDisplayListID);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
		GL11.glPopMatrix();
	}
}
