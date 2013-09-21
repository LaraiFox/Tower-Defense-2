package net.laraifox.tdlwjgl.level;

import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.entity.EntityArmoured;
import net.laraifox.tdlwjgl.entity.EntityBasic;
import net.laraifox.tdlwjgl.entity.EntityFast;
import net.laraifox.tdlwjgl.entity.EntityStrong;
import net.laraifox.tdlwjgl.enums.EnumDirection;
import net.laraifox.tdlwjgl.enums.EnumEntityType;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class Wave {
	private Entity[] entities;
	private int length;
	private int spawnrate;
	private int entitiesSpawned = 0;
	private int ticks = 0;
	private boolean entitiesAlive = false;

	public Wave(EnumEntityType waveType, int gridX, int gridY, EnumDirection initialDir, int length, int spawnrate) {
		entities = new Entity[length];

		switch (waveType) {
		case Basic:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityBasic(gridX, gridY, initialDir);
			break;
		case Fast:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityFast(gridX, gridY, initialDir);
			break;
		case Armoured:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityArmoured(gridX, gridY, initialDir);
			break;
		case Strong:
			for (int i = 0; i < entities.length; i++)
				entities[i] = new EntityStrong(gridX, gridY, initialDir);
			break;
		default:
			break;
		}

		this.length = length;
		this.spawnrate = spawnrate;
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
		return (entitiesAlive && (entitiesSpawned >= length));
	}

	public void update(Level level) {
		int entitiesAliveCount = 0;

		if (((ticks % spawnrate) == 0) && (entitiesSpawned < length)) {
			entities[entitiesSpawned].alive = true;
			entitiesSpawned++;
		}

		entitiesAlive = true;

		for (int i = 0; i < length; i++) {
			if (entities[i].alive) {
				entitiesAlive = false;

				entities[i].update(level);
				entitiesAliveCount++;
			}
		}

		StringRenderer.appendString("  -  " + entitiesAliveCount + ", " + entitiesSpawned + "/" + length, StringRenderer.getLength() - 2);

		ticks++;
	}

	public void render() {
		if (entitiesSpawned > 0) {
			for (int i = 0; i < length; i++) {
				if (entities[i].alive) {
					entities[i].render();
				}
			}
		}
	}
}
