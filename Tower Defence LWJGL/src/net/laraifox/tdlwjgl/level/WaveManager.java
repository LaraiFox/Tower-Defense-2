package net.laraifox.tdlwjgl.level;

import java.util.ArrayList;
import java.util.List;

import net.laraifox.lib.graphics.VectorFont;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.StringRenderer;

public class WaveManager {
	private List<Wave> waves;
	private int wavesStarted;
	private boolean gameStarted;
	private int waveCount;
	private int previousStartTick;

	public WaveManager() {
		this.waves = new ArrayList<Wave>();
		this.wavesStarted = 0;
		this.gameStarted = false;
		this.previousStartTick = 0;
	}

	public void addWave(int type, int length, int delay, int spawnpoint, int spawnrate) {
		waves.add(new Wave(type, length, delay, spawnpoint, spawnrate));
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

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void start() {
		gameStarted = true;
	}

	public boolean isFinished() {
		return (gameStarted && wavesStarted >= waves.size() && waveCount == 0);
	}

	public void update(GameTimer gameTimer, Level level) {
		StringRenderer.addString("Wave Manager Status: " + (gameStarted ? "Updating.." : "Not Started"), 16, 64, EnumFontSize.Small, VectorFont.ALIGN_LEFT);
		StringRenderer.addString("Wave: " + wavesStarted + ", " + waves.size(), 16, 80, EnumFontSize.Small, VectorFont.ALIGN_LEFT);

		if (gameStarted) {
			if ((wavesStarted < waves.size()) && (gameTimer.getTicks() - previousStartTick >= waves.get(wavesStarted).getDelay())) {
				previousStartTick = gameTimer.getTicks();

				wavesStarted++;
			}

			waveCount = 0;
			for (int i = 0; i < wavesStarted; i++) {
				if (!waves.get(i).isComplete()) {
					waves.get(i).update(gameTimer, level);
					waveCount++;
				}
			}
		}
	}

	public void startNextWave(GameTimer gameTimer) {
		if (wavesStarted < waves.size()) {
			previousStartTick = gameTimer.getTicks();
			wavesStarted++;
		}
	}

	public void render() {
		if (gameStarted) {
			for (Wave wave : waves) {
				wave.render();
			}
		}
	}
}
