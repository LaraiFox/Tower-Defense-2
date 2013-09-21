package net.laraifox.tdlwjgl.level;

import java.util.ArrayList;
import java.util.List;

import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumDirection;
import net.laraifox.tdlwjgl.enums.EnumEntityType;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class WaveManager {
	private int startX, startY;
	private EnumDirection initialDir;
	private List<Wave> waves;
	private int wavesStarted;
	private boolean started;
	private int waveCount;
	private int interval;
	private int ticks;

	public WaveManager(int interval) {
		this.waves = new ArrayList<Wave>();
		this.wavesStarted = 0;
		this.started = false;
		this.interval = interval;
		this.ticks = 0;
	}

	public void setStartingLocation(int x, int y) {
		this.startX = x;
		this.startY = y;
	}

	public void setStartingDirection(EnumDirection direction) {
		this.initialDir = direction;
	}

	public void addWave(EnumEntityType entityType, int length, int spawnrate) {
		waves.add(new Wave(entityType, startX, startY, initialDir, length, spawnrate));
	}

	public int getWaveCount() {
		return wavesStarted;
	}

	public Wave[] getWaves() {
		return (Wave[]) waves.toArray();
	}

	public Wave getWaveAt(int i) {
		return waves.get(i);
	}

	public Entity getEntityAt(int i, int j) {
		return waves.get(i).getEntityAt(j);
	}

	public boolean isStarted() {
		return started;
	}

	public void start() {
		started = true;
	}

	public boolean isFinished() {
		return (started && wavesStarted >= waves.size() && waveCount == 0);
	}

	public void nextWave() {
		if (wavesStarted < waves.size()) {
			wavesStarted++;
			ticks = 1;
		}
	}

	public void update(Level level) {
		StringRenderer.addString("Wave Manager Status: " + (started ? "Updating.." : "Not Started"), 16, 64, EnumFontSize.Small);
		StringRenderer.addString("Wave: " + wavesStarted + ", " + waves.size(), 16, 80, EnumFontSize.Small);
		StringRenderer.addString("Time Until Next Wave: " + ((int) ((interval - (ticks % interval)) / 6.0)) / 10.0, 16, 96, EnumFontSize.Small);

		if (started) {
			if (((ticks % interval) == 0) && (wavesStarted < waves.size())) {
				wavesStarted++;
			}

			waveCount = 0;
			for (int i = 0; i < wavesStarted; i++) {
				if (!waves.get(i).isComplete()) {
					waves.get(i).update(level);
					waveCount++;
				}
			}

			ticks++;
		}
	}

	public void render() {
		if (started) {
			for (int i = 0; i < wavesStarted; i++) {
				waves.get(i).render();
			}
		}
	}
}
