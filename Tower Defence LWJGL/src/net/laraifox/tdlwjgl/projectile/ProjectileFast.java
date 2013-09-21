package net.laraifox.tdlwjgl.projectile;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.awt.Rectangle;

import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumTowerType;

public class ProjectileFast extends Projectile {
	private float phi;

	public ProjectileFast(EnumTowerType projectileType, double x, double y, float theta, double range, int waveIndex, int entityIndex) {
		super(projectileType, x, y, theta, range, new Rectangle(0, 1, 8, 4), waveIndex, entityIndex, 1);

		this.id = 1;
		this.phi = 0f;
	}

	public void update(Entity entity) {
		phi += 9.0;

		super.update(entity);
	}

	public void render() {
		glPushMatrix();
		glLoadIdentity();
		glTranslated(position.getX() + 36, position.getY() + 36, 0);
		glRotatef(phi, 0, 0, 1);
		glTranslated(-position.getX() - 4, -position.getY() - 4, 0);

		super.render();
		glPopMatrix();
	}
}
