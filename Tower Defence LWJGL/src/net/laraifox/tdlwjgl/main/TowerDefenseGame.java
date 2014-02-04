package net.laraifox.tdlwjgl.main;

import java.util.Random;

import net.laraifox.lib.display.OpenGLDisplay;
import net.laraifox.lib.text.VectorFont;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumGameState;
import net.laraifox.tdlwjgl.enums.EnumMenuState;
import net.laraifox.tdlwjgl.enums.EnumProgramState;
import net.laraifox.tdlwjgl.level.Tile;
import net.laraifox.tdlwjgl.projectile.Projectile;
import net.laraifox.tdlwjgl.tower.TowerBasic;
import net.laraifox.tdlwjgl.tower.TowerFast;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.MouseHandler;
import net.laraifox.tdlwjgl.util.StringRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class TowerDefenseGame extends OpenGLDisplay {
	private GameTimer gameTimer;
	private EnumProgramState programState;
	private Random random;

	private MenuManager menuManager;
	private GameManager gameManager;
	private LevelEditor levelEditor;

	private String nextLevelName;

	private boolean showFramerate;

	public TowerDefenseGame(int width, int height) {
		super(width, height);

		this.pixelFormat = new PixelFormat(0, 4, 1, 1, 8, 0, 1, 1, false);
	}

	protected void initializeResources() {
		// try {
		// Texture texture = TextureLoader.getTexture(new FileInputStream(new File("res/title/loading_screen.png")));
		// texture.bindTexture();
		// GL11.glBegin(GL11.GL_QUADS);
		// GL11.glTexCoord2f(0.0f, 1.0f);
		// GL11.glVertex2i(0, 0);
		// GL11.glTexCoord2f(1.0f, 1.0f);
		// GL11.glVertex2i(getWidth(), 0);
		// GL11.glTexCoord2f(1.0f, 0.0f);
		// GL11.glVertex2i(getWidth(), getHeight());
		// GL11.glTexCoord2f(0.0f, 0.0f);
		// GL11.glVertex2i(0, getHeight());
		// GL11.glEnd();
		// Display.update();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		Entity.initialize();
		MouseHandler.initialize(orthographicProjection.getRight() / (float) getWidth(), orthographicProjection.getTop() / (float) getHeight());
		Projectile.initialize();
		StringRenderer.initialize();
		Tile.initialize();
		TowerBasic.initialize();
		TowerFast.initialize();
	}

	protected void initializeVariables() {
		this.gameTimer = new GameTimer(getFramerate());
		this.programState = EnumProgramState.Menu;
		this.random = new Random();

		this.menuManager = new MenuManager((int) orthographicProjection.getRight(), (int) orthographicProjection.getTop(), random);
		this.gameManager = new GameManager();
		this.levelEditor = new LevelEditor();

		this.nextLevelName = "prototype_level_1";

		this.showFramerate = false;
	}

	protected void cleanUp() {
		// TODO: Add any clean up of resources here.
	}

	protected void tick() {
		// System.out.println(gameTimer.toString());
	}

	protected void update(double delta) {
		StringRenderer.clear();
		MouseHandler.update();
		gameTimer.update();

		switch (programState) {
		case Menu:
			menuManager.update(this);

			if (programState == EnumProgramState.Game) {
				gameManager.openLevel(nextLevelName);
			}
			break;
		case Game:
			gameManager.update(this, gameTimer);
			break;
		case Editor:
			levelEditor.update(this);
			break;
		case Quit:
			this.stop();
			break;
		default:
			break;
		}
	}

	protected void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glLoadIdentity();

		if (showFramerate) {
			StringRenderer.addString("FPS: " + getCurrentFPS() + ", (updates: " + getCurrentUPS() + ")", 48, (int) (height - 80), EnumFontSize.Medium, VectorFont.ALIGN_LEFT);
		}

		switch (programState) {
		case Menu:
			menuManager.render();
			break;
		case Game:
			gameManager.render();
			break;
		case Editor:
			levelEditor.render();
			break;
		default:
			break;
		}

		StringRenderer.render();
	}

	public void initializeLevelEditor() {
		levelEditor.newLevel(0, 24, 17);
	}

	public void setProgramState(EnumProgramState programState) {
		this.programState = programState;
	}

	public void setMenuState(EnumMenuState menuState) {
		menuManager.setMenuState(menuState);
	}

	public void setGameState(EnumGameState gameState) {
		gameManager.setGameState(gameState);
	}

	public void setNextLevel(String levelName) {

	}

	public boolean getShowFramerate() {
		return showFramerate;
	}

	public void setShowFramerate(boolean showFramerate) {
		this.showFramerate = showFramerate;
	}
}
