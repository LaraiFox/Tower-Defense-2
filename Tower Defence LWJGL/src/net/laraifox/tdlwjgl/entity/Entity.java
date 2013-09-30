package net.laraifox.tdlwjgl.entity;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Rectangle;

import net.laraifox.lib.math.Vector;
import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.enums.EnumDirection;
import net.laraifox.tdlwjgl.enums.EnumEntityType;
import net.laraifox.tdlwjgl.level.Player;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.level.Waypoint;
import net.laraifox.tdlwjgl.util.SpriteSheet;

import org.lwjgl.opengl.GL11;

public abstract class Entity {
	public static final int TYPE_BASIC = 0;
	public static final int TYPE_FAST = 1;
	public static final int TYPE_ARMORED = 2;
	public static final int TYPE_STRONG = 3;

	private static final int TEX_GRID_WIDTH = 16;
	private static final int TEX_GRID_HEIGHT = 16;
	private static final int ENTITY_SIZE = 32;
	private static int[] displayListIDs;

	private int entityID;
	private int health;
	private int reward;
	private float speed;
	private Rectangle hitbox;

	private Vector2 position;
	private Vector2 center;
	private Vector2 velocity;
	private boolean alive;

	private EnumDirection direction;
	private int waypointIndex;

	public Entity(EnumEntityType entityType) {
		this.entityID = entityType.getEntityID();
		this.health = entityType.getHealth();
		this.reward = entityType.getReward();
		this.speed = entityType.getSpeed();
		this.hitbox = entityType.getHitbox();

		this.position = new Vector2(0, 0);
		this.center = new Vector2(ENTITY_SIZE / 2, ENTITY_SIZE / 2);
		this.velocity = Vector.scale(direction.getVector(), speed);
		this.alive = false;

		this.direction = EnumDirection.None;
		this.waypointIndex = 0;
	}

	public static void initialize() {
		Entity.displayListIDs = new int[TEX_GRID_WIDTH * TEX_GRID_HEIGHT];
		for (int i = 0; i < displayListIDs.length; i++) {
			int tx = i % 16;
			int ty = (i - tx) / 16;

			float left = SpriteSheet.Entities.getLeftOfTile(tx);
			float right = SpriteSheet.Entities.getRightOfTile(tx, 1);
			float top = SpriteSheet.Entities.getTopOfTile(ty, 1);
			float bottom = SpriteSheet.Entities.getBottomOfTile(ty);

			displayListIDs[i] = glGenLists(1);
			glNewList(displayListIDs[i], GL_COMPILE);
			glBegin(GL_QUADS);
			glTexCoord2f(left, top);
			glVertex2i(0, 0);
			glTexCoord2f(right, top);
			glVertex2i(ENTITY_SIZE, 0);
			glTexCoord2f(right, bottom);
			glVertex2i(ENTITY_SIZE, ENTITY_SIZE);
			glTexCoord2f(left, bottom);
			glVertex2i(0, ENTITY_SIZE);
			glEnd();
			glEndList();
		}
	}

	public int getReward() {
		return reward;
	}

	public Rectangle getHitbox() {
		return new Rectangle((int) (getPosition().getX() + hitbox.getX()), (int) (getPosition().getY() + hitbox.getY()), hitbox.width, hitbox.height);
	}

	public void setSpawnWaypoint(Waypoint waypoint) {
		this.position = new Vector2(waypoint.getX(), waypoint.getY()).scale(Tile.getTileSize());
		this.direction = waypoint.getDirection();
	}

	public void update(Player player, Waypoint waypoint) {
		if (health <= 0) {
			player.money += reward;
			setAlive(false);
			return;
		}

		if ((getPosition().getX() % Tile.getTileSize() > -speed) && (getPosition().getX() % Tile.getTileSize() < speed) && (getPosition().getY() % Tile.getTileSize() > -speed) && (getPosition().getY() % Tile.getTileSize() < speed)) {
			int x = (int) (getPosition().getX() / Tile.getTileSize());
			int y = (int) (getPosition().getY() / Tile.getTileSize());

			if (waypoint.getX() == x && waypoint.getY() == y) {
				direction = waypoint.getDirection();
				velocity = Vector.scale(direction.getVector(), speed);
				waypointIndex++;
				if (direction == EnumDirection.None) {
					player.removeLife();
					setAlive(false);
					return;
				}
			}
		}

		getPosition().add(velocity);
	}

	public void render() {
		SpriteSheet.Entities.bindSheetTexture();
		GL11.glPushMatrix();
		GL11.glTranslated(position.getX(), position.getY(), 0);
		GL11.glCallList(displayListIDs[entityID + direction.ordinal() - 1]);
		GL11.glPopMatrix();
	}

	public void dealDamage(int damage) {
		health -= damage;
	}

	public Vector2 getPosition() {
		return position;
	}

	public Vector2 getCenter() {
		return center;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getWaypointIndex() {
		return waypointIndex;
	}
}
