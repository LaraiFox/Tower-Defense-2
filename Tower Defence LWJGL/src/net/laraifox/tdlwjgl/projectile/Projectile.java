package net.laraifox.tdlwjgl.projectile;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumTowerType;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class Projectile {
	private static final int WIDTH = 8;
	private static final int HEIGHT = 8;

	protected int id;
	protected Vector2 position;
	protected Vector2 previousPosition;
	protected float theta;

	private int displayListID;
	private EnumTowerType projectileType;
	private double range;
	private Vector2 velocity;
	private Rectangle hitboxSize;
	private int waveIndex;
	private int entityIndex;
	private int damage;
	private int ticks;

	public boolean alive = false;

	public Projectile(EnumTowerType projectileType, double x, double y, float theta, double range, Rectangle hitboxSize, int waveIndex, int entityIndex,
			int damage) {
		this.projectileType = projectileType;
		this.position = new Vector2(x, y);
		this.position = new Vector2(x, y);
		this.theta = theta;
		this.range = range + 16;
		this.velocity = Vector2.Zero();
		this.hitboxSize = hitboxSize;
		this.waveIndex = waveIndex;
		this.entityIndex = entityIndex;
		this.damage = damage;
		this.ticks = 0;

		createDisplayList();
	}

	private void createDisplayList() {
		this.displayListID = GL11.glGenLists(1);

		int tx = id % 4;
		int ty = (id - tx) / 4;

		float left = SpriteSheet.Projectiles.getLeftOfTile(tx);
		float right = SpriteSheet.Projectiles.getRightOfTile(tx, 1);
		float top = SpriteSheet.Projectiles.getTopOfTile(ty, 1);
		float bottom = SpriteSheet.Projectiles.getBottomOfTile(ty);

		int halfWidth = WIDTH / 2;
		int halfHeight = HEIGHT / 2;

		GL11.glNewList(displayListID, GL11.GL_COMPILE);
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

	public EnumTowerType getProjectileType() {
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

	public Rectangle getHitbox() {
		return new Rectangle((int) (position.getX() + hitboxSize.getX()), (int) (position.getY() + hitboxSize.getY()), hitboxSize.width, hitboxSize.height);
	}

	public boolean isCollided(Rectangle entityHitbox) {
		return getHitbox().intersects(entityHitbox);
	}

	public void update(Entity entity) {
		double dx = (entity.position.getX() + entity.getWidth() / 2) - (position.getX() + 8 / 2);
		double dy = (entity.position.getY() + entity.getHeight() / 2) - (position.getY() + 8 / 2);
		theta = (float) Math.atan2(dy, dx);

		velocity.set(dx, dy).normalize().scale(3.0f);

		position.add(velocity);

		if (velocity.length() * ticks > range * 1.2) {
			alive = false;
			return;
		} else if (!entity.alive) {
			if (velocity.lengthSq() * ticks > (dx * dx + dy * dy)) {
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
		GL11.glCallList(displayListID);
		GL11.glPopMatrix();
	}

	public boolean pathIntersects(Rectangle hitbox) {
		Line2D path = new Line2D.Double(previousPosition.getX(), previousPosition.getY(), position.getX(), position.getY());

		return path.intersects(hitbox);
	}
}
