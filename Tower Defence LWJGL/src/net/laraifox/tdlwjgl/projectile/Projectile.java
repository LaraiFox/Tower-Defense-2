package net.laraifox.tdlwjgl.projectile;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

import net.laraifox.lib.math.Vector2f;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumProjectileType;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class Projectile {
	private static final int TEX_GRID_WIDTH = 4;
	private static final int TEX_GRID_HEIGHT = 4;
	private static final int PROJECTILE_SIZE = 8;
	private static int[] displayListIDs;

	private EnumProjectileType projectileType;
	private int projectileID;
	private Vector2f position;
	private Vector2f previousPosition;
	private Vector2f velocity;
	private float theta;

	private boolean alive;
	private double range;
	private int damage;
	private int ticks;
	private int waveIndex;
	private int entityIndex;

	public Projectile(EnumProjectileType projectileType, Vector2f position, float theta, int waveIndex, int entityIndex) {
		this.projectileType = projectileType;
		this.projectileID = projectileType.getProjectileID();
		this.position = new Vector2f(position);
		this.previousPosition = new Vector2f(position);
		this.velocity = Vector2f.Zero();
		this.theta = theta;

		this.alive = false;
		this.range = projectileType.getBaseRange() * 50;
		this.damage = projectileType.getBaseDamage();
		this.ticks = 0;
		this.waveIndex = waveIndex;
		this.entityIndex = entityIndex;
	}

	public static void initialize() {
		Projectile.displayListIDs = new int[TEX_GRID_WIDTH * TEX_GRID_HEIGHT];
		for (int i = 0; i < displayListIDs.length; i++) {
			int tx = i % TEX_GRID_WIDTH;
			int ty = (i - tx) / TEX_GRID_WIDTH;

			float left = SpriteSheet.Projectiles.getLeftOfTile(tx);
			float right = SpriteSheet.Projectiles.getRightOfTile(tx, 1);
			float top = SpriteSheet.Projectiles.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Projectiles.getBottomOfTile(ty);

			int halfWidth = PROJECTILE_SIZE / 2;
			int halfHeight = PROJECTILE_SIZE / 2;

			displayListIDs[i] = GL11.glGenLists(1);
			GL11.glNewList(displayListIDs[i], GL11.GL_COMPILE);
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
		}
	}

	public void update(Entity entity) {
		Vector2f vectorToEnity = new Vector2f(entity.getPosition());
		vectorToEnity.add(entity.getCenter());
		vectorToEnity.subtract(position);
		theta = (float) Math.atan2(vectorToEnity.getY(), vectorToEnity.getX());

		velocity.set(vectorToEnity).normalize().scale(3.0f);

		position.add(velocity);

		if (velocity.length() * ticks > range) {
			setAlive(false);
			return;
		} else if (!entity.isAlive()) {
			if (velocity.lengthSq() * ticks > (vectorToEnity.getX() * vectorToEnity.getX() + vectorToEnity.getY() * vectorToEnity.getY())) {
				setAlive(false);
				return;
			}
		}

		previousPosition = position;

		ticks++;
	}

	public void render() {
		SpriteSheet.Projectiles.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslated(position.getX(), position.getY(), 0);
		GL11.glRotatef(theta, 0, 0, 1);
		GL11.glCallList(displayListIDs[projectileID]);
		GL11.glPopMatrix();
	}

	public boolean pathIntersects(Rectangle hitbox) {
		Line2D path = new Line2D.Double(previousPosition.getX(), previousPosition.getY(), position.getX(), position.getY());

		return path.intersects(hitbox);
	}

	public EnumProjectileType getProjectileType() {
		return projectileType;
	}

	public int getDamage() {
		return damage;
	}

	public int getWaveIndex() {
		return waveIndex;
	}

	public int getEntityIndex() {
		return entityIndex;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
}
