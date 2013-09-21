package net.laraifox.tdlwjgl.main;

import java.util.Random;

import net.laraifox.lib.graphics.Color3f;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.level.Level;
import net.laraifox.tdlwjgl.level.LevelGenerator;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.StringRenderer;

import org.lwjgl.input.Keyboard;

public class GameManager {
	private EnumGameState gameState;

	private Random random;

	private Level leveltest;
	private boolean isLevelOpen;

	private boolean KEY_ESCAPE;
	private boolean KEY_RETURN;

	public GameManager(Random random) {
		this.gameState = EnumGameState.SinglePlayer;

		this.random = random;

		this.leveltest = new Level();
		this.isLevelOpen = false;
	}

	public void setGameState(EnumGameState gameState) {
		this.gameState = gameState;
	}

	public boolean isLevelOpen() {
		return isLevelOpen;
	}

	public void openLevel(String levelName) {
		leveltest = LevelGenerator.generateLevelFrom("res/blueprints/" + levelName + ".txt", random, true);
		isLevelOpen = true;
	}

	public void closeLevel() {
		leveltest = null;
		isLevelOpen = false;
	}

	public void update(TowerDefenseGame game, GameTimer gameTime) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if (!KEY_ESCAPE) {
				game.setProgramState(EnumProgramState.Menu);
				closeLevel();
				return;
			}
		} else if (!isLevelOpen) {
			game.setProgramState(EnumProgramState.Menu);
			return;
		}

		if (gameState == EnumGameState.SinglePlayer) {
			if (leveltest.hasLevelErrorOccurred()) {
				closeLevel();
				gameState = EnumGameState.None;
				game.setProgramState(EnumProgramState.Menu);
				return;
			}
			leveltest.update(gameTime);

			if (leveltest.isLevelComplete()) {
				StringRenderer.addString("Level Complete!", (game.getWidth() - 15 * EnumFontSize.Large.getWidth()) / 2,
						(game.getHeight() + 3 * EnumFontSize.Large.getHeight()) / 2, EnumFontSize.Large, new Color3f(1.0f, 1.0f, 0.0f));
				StringRenderer.addString("Press Enter to return", (game.getWidth() - 21 * EnumFontSize.Large.getWidth()) / 2,
						(game.getHeight() - 3 * EnumFontSize.Large.getHeight()) / 2, EnumFontSize.Large, new Color3f(1.0f, 1.0f, 0.0f));
				StringRenderer.addString("to the title screen", (game.getWidth() - 19 * EnumFontSize.Large.getWidth()) / 2,
						(game.getHeight() - 5 * EnumFontSize.Large.getHeight()) / 2, EnumFontSize.Large, new Color3f(1.0f, 1.0f, 0.0f));

				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN) && !KEY_RETURN) {
					game.setProgramState(EnumProgramState.Menu);
					closeLevel();
					return;
				}
			}
		}

		KEY_ESCAPE = Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
		KEY_RETURN = Keyboard.isKeyDown(Keyboard.KEY_RETURN);
	}

	public void render() {
		leveltest.render();
	}
}
