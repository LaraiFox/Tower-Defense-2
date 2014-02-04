package net.laraifox.tdlwjgl.main;

import java.io.FileNotFoundException;

import net.laraifox.lib.math.Vector3f;
import net.laraifox.lib.text.VectorFont;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.level.Level;
import net.laraifox.tdlwjgl.level.LevelFormatter;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.StringRenderer;

import org.lwjgl.input.Keyboard;

public class GameManager {
	private EnumGameState gameState;

	private Level leveltest;
	private boolean isLevelOpen;

	private boolean KEY_ESCAPE;
	private boolean KEY_RETURN;

	public GameManager() {
		this.gameState = EnumGameState.SinglePlayer;

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
		// leveltest = LevelGenerator.generateLevelFrom("res/blueprints/" + levelName + ".txt", random, true);
		try {
			leveltest = LevelFormatter.loadLevel("res/blueprints/" + levelName + ".txt");
			isLevelOpen = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
			leveltest.update(gameTime);

			if (leveltest.isLevelComplete()) {
				StringRenderer.addString("Level Complete!", (int) (game.getWidth() / 2), (int) (game.getHeight() / 2), EnumFontSize.Large, VectorFont.ALIGN_CENTER, new Vector3f(1.0f, 1.0f, 0.0f));
				StringRenderer.addString("Press Enter to return", (int) (game.getWidth() / 2), (int) game.getHeight() / 2, EnumFontSize.Large, VectorFont.ALIGN_CENTER, new Vector3f(1.0f, 1.0f, 0.0f));
				StringRenderer.addString("to the title screen", (int) (game.getWidth() / 2), (int) game.getHeight() / 2, EnumFontSize.Large, VectorFont.ALIGN_CENTER, new Vector3f(1.0f, 1.0f, 0.0f));

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
