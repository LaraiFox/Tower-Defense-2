package net.laraifox.tdlwjgl.projectile;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumProjectileType;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class Projectile {
	private static final int TEX_GRID_WIDTH = 4;
	private static final int TEX_GRID_HEIGHT = 4;
	private static final int PROJECTILE_SIZE = 8;
	private static int[] displayListIDs;

	protected int projectileID;
	protected Vector2 position;
	protected Vector2 previousPosition;
	protected float theta;

	private EnumProjectileType projectileType;
	private double range;
	private Vector2 velocity;
	private int waveIndex;
	private int entityIndex;
	private int damage;
	private int ticks;

	public boolean alive = false;

	public Projectile(EnumProjectileType projectileType, double x, double y, float theta, int waveIndex, int entityIndex) {
		this.projectileID = projectileType.getProjectileID();
		this.projectileType = projectileType;
		this.position = new Vector2(x, y);
		this.previousPosition = new Vector2(x, y);
		this.theta = theta;
		this.range = projectileType.getBaseRange();
		this.velocity = Vector2.Zero();
		this.waveIndex = waveIndex;
		this.entityIndex = entityIndex;
		this.damage = projectileType.getBaseDamage();
		this.ticks = 0;
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
		Vector2 vectorToEnity = new Vector2(entity.position);
		vectorToEnity.add(new Vector2(entity.getWidth(), entity.getHeight()).scale(0.5));
		vectorToEnity.subtract(position);
		theta = (float) Math.atan2(vectorToEnity.getY(), vectorToEnity.getX());

		velocity.set(vectorToEnity).normalize().scale(3.0f);

		position.add(velocity);

		if (velocity.length() * ticks > range) {
			alive = false;
			return;
		} else if (!entity.alive) {
			if (velocity.lengthSq() * ticks > (vectorToEnity.getX() * vectorToEnity.getX() + vectorToEnity.getY() * vectorToEnity.getY())) {
				alive = false;
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
}
