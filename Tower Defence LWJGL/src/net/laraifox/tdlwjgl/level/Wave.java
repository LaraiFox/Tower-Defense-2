package net.laraifox.tdlwjgl.level;

import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.entity.EntityArmoured;
import net.laraifox.tdlwjgl.entity.EntityBasic;
import net.laraifox.tdlwjgl.entity.EntityFast;
import net.laraifox.tdlwjgl.entity.EntityStrong;
import net.laraifox.tdlwjgl.util.GameTimer;

public class Wave {
	private Entity[] entities;
	private int entityID;
	private int length;
	private int delay;
	private int spawnpoint;
	private int spawnrate;

	private boolean entitiesAlive;
	private int previousSpawnTick;
	private int entitiesSpawned;

	public Wave(int type, int length, int delay, int spawnpoint, int spawnrate) {
		this.entities = new Entity[length];
		switch (type) {
		case Entity.TYPE_BASIC:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityBasic();
			break;
		case Entity.TYPE_FAST:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityFast();
			break;
		case Entity.TYPE_ARMORED:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityArmoured();
			break;
		case Entity.TYPE_STRONG:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityStrong();
			break;
		default:
			break;
		}

		this.entityID = type;
		this.length = length;
		this.delay = delay * 60;
		this.spawnpoint = spawnpoint;
		this.spawnrate = spawnrate * 6;

		this.entitiesAlive = false;
		this.entitiesSpawned = 0;
		this.previousSpawnTick = 0;
	}

	public int getLength() {
		return length;
	}

	public Entity getEntityAt(int i) {
		return entities[i];
	}

	public Entity[] getEntities() {
		return entities;
	}

	public boolean isComplete() {
		return (!entitiesAlive && (entitiesSpawned >= length));
	}

	public void update(GameTimer gameTimer, Level level) {
		if ((gameTimer.getTicks() - previousSpawnTick >= spawnrate) && (entitiesSpawned < length)) {
			previousSpawnTick = gameTimer.getTicks();
			entities[entitiesSpawned].setSpawnWaypoint(level.getWaypoint(spawnpoint, 0));
			entities[entitiesSpawned].setAlive(true);
			entitiesSpawned++;
		}

		entitiesAlive = false;
		for (int i = 0; i < length; i++) {
			if (entities[i].isAlive()) {
				Waypoint waypoint = level.getWaypoint(spawnpoint, entities[i].getWaypointIndex());
				entities[i].update(level.getPlayer(), waypoint);
				entitiesAlive = true;
			} else if (entities[i].getWaypointIndex() >= level.getWaypointListLength(spawnpoint)) {
				continue;
			}
		}

		// StringRenderer.appendString("  -  " + entitiesAliveCount + ", " + entitiesSpawned + "/" + length, StringRenderer.getLength() - 2);
	}

	public void render() {
		if (entitiesSpawned > 0) {
			for (Entity entity : entities) {
				if (entity.isAlive()) {
					entity.render();
				}
			}
		}
	}

	public int getDelay() {
		return delay;
	}

	public int getEntityID() {
		return entityID;
	}

	public int getSpawnpoint() {
		return spawnpoint;
	}

	public int getSpawnrate() {
		return spawnrate;
	}
}
