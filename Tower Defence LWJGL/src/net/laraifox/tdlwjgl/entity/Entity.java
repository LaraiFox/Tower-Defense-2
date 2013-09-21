package net.laraifox.tdlwjgl.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2i;

import java.awt.Rectangle;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.math.Vector;
import net.laraifox.lib.math.Vector2;
import net.laraifox.tdlwjgl.enums.EnumDirection;
import net.laraifox.tdlwjgl.enums.EnumWaypoint;
import net.laraifox.tdlwjgl.level.Level;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.util.SpriteSheet;

public abstract class Entity {
	public static Texture texture;

	public Vector2 position;
	public boolean alive;

	private int id;
	private short reward;
	private int width, height;
	private int lastWaypointX, lastWaypointY;

	protected EnumDirection direction;
	protected float speed;
	protected Vector2 vector;
	protected Rectangle hitboxSize;
	protected int health;

	public Entity(int id, int gridX, int gridY, EnumDirection initialDirection, float speed, Rectangle hitboxSize, short reward, int health) {
		this.position = new Vector2(gridX * Tile.getTileSize(), gridY * Tile.getTileSize());
		this.alive = false;

		this.id = id;
		this.reward = reward;
		this.width = Tile.getTileSize();
		this.height = Tile.getTileSize();
		this.lastWaypointX = gridX;
		this.lastWaypointY = gridY;

		this.direction = initialDirection;
		this.speed = speed;
		this.vector = Vector.scale(initialDirection.getVector(), speed);
		this.hitboxSize = hitboxSize;
		this.health = health;
	}

	public short getReward() {
		return reward;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Rectangle getHitbox() {
		return new Rectangle((int) (position.getX() + hitboxSize.getX()), (int) (position.getY() + hitboxSize.getY()), hitboxSize.width, hitboxSize.height);
	}

	public void update(Level level) {
		if (health <= 0) {
			Level.player.money += reward;
			alive = false;
			return;
		}

		if ((position.getX() % Tile.getTileSize() > -speed) && (position.getX() % Tile.getTileSize() < speed)
				&& (position.getY() % Tile.getTileSize() > -speed) && (position.getY() % Tile.getTileSize() < speed)) {
			int x = (int) (position.getX() / Tile.getTileSize());
			int y = (int) (position.getY() / Tile.getTileSize());

			EnumWaypoint waypoint = level.getTileAt(x + y * Level.DEFAULT_WIDTH).getWaypoint();

			if (waypoint != EnumWaypoint.None) {
				setNewWaypoint(waypoint, x, y);
			}
		}

		position.add(vector);
	}

	public void render() {
		int i = id + direction.getTextureIndex();

		int tx = i % 16;
		int ty = (i - tx) / 16;

		float left = SpriteSheet.Entities.getLeftOfTile(tx);
		float right = SpriteSheet.Entities.getRightOfTile(tx, 1);
		float top = SpriteSheet.Entities.getTopOfTile(ty, 1);
		float bottom = SpriteSheet.Entities.getBottomOfTile(ty);

		SpriteSheet.Entities.bindSheetTexture();
		glBegin(GL_QUADS);
		{
			glTexCoord2f(left, top);
			glVertex2i((int) position.getX(), (int) position.getY());

			glTexCoord2f(right, top);
			glVertex2i((int) position.getX() + 32, (int) position.getY());

			glTexCoord2f(right, bottom);
			glVertex2i((int) position.getX() + 32, (int) position.getY() + 32);

			glTexCoord2f(left, bottom);
			glVertex2i((int) position.getX(), (int) position.getY() + 32);
		}
		glEnd();
	}

	public void setNewWaypoint(EnumWaypoint waypoint, int lastWaypointX, int lastWaypointY) {
		// Change direction

		if (waypoint == EnumWaypoint.End) {
			direction = EnumDirection.None;
			Level.player.removeLife();
			alive = false;
		} else if ((lastWaypointX != this.lastWaypointX) || (lastWaypointY != this.lastWaypointY)) {
			switch (direction) {
			case None:
				alive = false;
				break;
			case Up:
				if (waypoint == EnumWaypoint.Left) {
					direction = EnumDirection.Left;
				} else if (waypoint == EnumWaypoint.Right) {
					direction = EnumDirection.Right;
				} else if (waypoint == EnumWaypoint.Back) {
					direction = EnumDirection.Down;
				}
				break;
			case Down:
				if (waypoint == EnumWaypoint.Left) {
					direction = EnumDirection.Right;
				} else if (waypoint == EnumWaypoint.Right) {
					direction = EnumDirection.Left;
				} else if (waypoint == EnumWaypoint.Back) {
					direction = EnumDirection.Up;
				}
				break;
			case Left:
				if (waypoint == EnumWaypoint.Left) {
					direction = EnumDirection.Down;
				} else if (waypoint == EnumWaypoint.Right) {
					direction = EnumDirection.Up;
				} else if (waypoint == EnumWaypoint.Back) {
					direction = EnumDirection.Right;
				}
				break;
			case Right:
				if (waypoint == EnumWaypoint.Left) {
					direction = EnumDirection.Up;
				} else if (waypoint == EnumWaypoint.Right) {
					direction = EnumDirection.Down;
				} else if (waypoint == EnumWaypoint.Back) {
					direction = EnumDirection.Left;
				}
				break;
			default:
				direction = EnumDirection.None;
				alive = false;
				break;
			}

			vector = Vector.scale(direction.getVector(), speed);

			this.lastWaypointX = lastWaypointX;
			this.lastWaypointY = lastWaypointY;
		}
	}

	public void dealDamage(int damage) {
		health -= damage;
	}
}
