package net.laraifox.tdlwjgl.level;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.laraifox.tdlwjgl.enums.EnumEntityType;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumTowerType;
import net.laraifox.tdlwjgl.enums.EnumWaypoint;
import net.laraifox.tdlwjgl.projectile.Projectile;
import net.laraifox.tdlwjgl.projectile.ProjectileBasic;
import net.laraifox.tdlwjgl.projectile.ProjectileFast;
import net.laraifox.tdlwjgl.tower.Tower;
import net.laraifox.tdlwjgl.tower.TowerBasic;
import net.laraifox.tdlwjgl.tower.TowerFast;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.StringRenderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Level {
	public boolean levelLoadedWithoutErrors = true;

	public static final Player player = new Player();

	public static final int DEFAULT_WIDTH = 24;
	public static final int DEFAULT_HEIGHT = 17;

	protected Tile[] tiles;
	protected int width, height;
	protected WaveManager waveManager;

	private boolean levelErrorOccurred;

	private List<Projectile> projectiles;
	private List<Tower> towers;

	private EnumTowerType selectedTowerType;

	private boolean MOUSE_3;
	private boolean KEY_B;
	private boolean KEY_F;
	private boolean KEY_SPACE;

	public Level() {
		player.reset();

		this.projectiles = new ArrayList<Projectile>();

		this.tiles = new Tile[DEFAULT_WIDTH * DEFAULT_HEIGHT];
		for (int i = 0; i < DEFAULT_WIDTH * DEFAULT_HEIGHT; i++) {
			this.tiles[i] = tiles[i];
		}

		this.towers = new ArrayList<Tower>();

		this.selectedTowerType = EnumTowerType.None;
	}

	public Level(Random random) {
		int w = 24, h = 17;
		tiles = new Tile[w * h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int i = random.nextInt(20);
				tiles[x + y * w] = new Tile((i < 16 ? 0 : i < 19 ? 3 : 1), true, EnumWaypoint.None);
			}
		}
		this.selectedTowerType = EnumTowerType.None;
	}

	public boolean hasLevelErrorOccurred() {
		return levelErrorOccurred;
	}

	public void setLevelErrorOccurred(boolean levelErrorOccurred) {
		this.levelErrorOccurred = levelErrorOccurred;
	}

	public Tile getTileAt(int i) {
		if (i >= tiles.length) {
			setLevelErrorOccurred(true);
			i = 0;
		}

		return tiles[i];
	}

	public boolean isLevelComplete() {
		return waveManager.isFinished();
	}

	public void addWave(EnumEntityType waveType, byte length, short spawnrate) {

	}

	private void buildTower(EnumTowerType towerType, int x, int y) {
		Tower newTower = null;

		switch (towerType) {
		case Basic:
			newTower = new TowerBasic(x, y);
			break;
		case Fast:
			newTower = new TowerFast(x, y);
			break;
		default:
			return;
		}

		if (newTower != null && player.money >= newTower.getCost()) {
			towers.add(newTower);
			player.money -= newTower.getCost();
			tiles[x + y * DEFAULT_WIDTH].setCanPlaceTower(false);
		}
	}

	private void destroyTower(int x, int y) {
		for (int i = 0; i < towers.size(); i++) {
			if (towers.get(i).getGridX() == x && towers.get(i).getGridY() == y) {
				player.money += (int) (towers.get(i).getCost() * 0.75);
				towers.remove(i);
				tiles[x + y * DEFAULT_WIDTH].setCanPlaceTower(true);
			}
		}
	}

	private void createProjectile(Tower tower) {
		Projectile p = null;

		switch (tower.getTowerType()) {
		case Basic:
			p = new ProjectileBasic(tower.getTowerType(), tower.getPosition().getX() + tower.getWidth() / 2,
					tower.getPosition().getY() + tower.getHeight() / 2, tower.getTheta(), tower.getFiringRadius(), tower.getTargetWave(),
					tower.getTargetEntity());
			p.alive = true;
			projectiles.add(p);
			break;
		case Fast:
			p = new ProjectileFast(tower.getTowerType(), tower.getPosition().getX() + tower.getWidth() / 2, tower.getPosition().getY() + tower.getHeight() / 2,
					tower.getTheta(), tower.getFiringRadius(), tower.getTargetWave(), tower.getTargetEntity());
			p.alive = true;
			projectiles.add(p);
			break;
		default:
			break;
		}
	}

	public void update(GameTimer gameTime) {
		StringRenderer.addString("Lives: " + player.getLives(), 16, 32, EnumFontSize.Small);
		StringRenderer.addString("Money: " + player.money, 16, 48, EnumFontSize.Small);

		for (int i = 0; i < projectiles.size(); i++) {
			if (!projectiles.get(i).alive) {
				projectiles.remove(i);
				continue;
			}

			Projectile p = projectiles.get(i);

			projectiles.get(i).update(waveManager.getWaveAt(p.getWaveIndex()).getEntityAt(p.getEntityIndex()));

			if (projectiles.get(i).pathIntersects(waveManager.getWaveAt(p.getWaveIndex()).getEntityAt(p.getEntityIndex()).getHitbox())) {
				waveManager.getWaveAt(p.getWaveIndex()).getEntityAt(p.getEntityIndex()).dealDamage(p.getDamage());
				projectiles.get(i).alive = false;
			}
		}

		waveManager.update(this);

		if (Mouse.isButtonDown(0)) {
			int x = (Mouse.getX() - 32) / Tile.getTileSize();
			int y = (Mouse.getY() - 32) / Tile.getTileSize();

			if (x >= 0 && x < DEFAULT_WIDTH && y >= 0 && y < DEFAULT_HEIGHT) {
				if (tiles[x + y * DEFAULT_WIDTH].canPlaceTower()) {
					buildTower(selectedTowerType, x, y);
				}
			}
		}

		if (Mouse.isButtonDown(1)) {
			if (!MOUSE_3) {
				int x = (Mouse.getX() - 32) / Tile.getTileSize();
				int y = (Mouse.getY() - 32) / Tile.getTileSize();

				if (x >= 0 && x < DEFAULT_WIDTH && y >= 0 && y < DEFAULT_HEIGHT) {
					if (!tiles[x + y * DEFAULT_WIDTH].canPlaceTower()) {
						destroyTower(x, y);
					}
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_B)) {
			if (!KEY_B) {
				changeSelectedTower(EnumTowerType.Basic);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			if (!KEY_F) {
				changeSelectedTower(EnumTowerType.Fast);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (!KEY_SPACE) {
				if (waveManager.isStarted())
					waveManager.nextWave();
				else
					waveManager.start();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_M))
			player.money += 10;

		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).update(gameTime, waveManager);

			if (towers.get(i).canFire(gameTime))
				createProjectile(towers.get(i));
		}

		updatePreviousInput();
	}

	private void changeSelectedTower(EnumTowerType towerType) {
		if (selectedTowerType == towerType) {
			selectedTowerType = EnumTowerType.None;
		} else {
			selectedTowerType = towerType;
		}
	}

	private void updatePreviousInput() {
		MOUSE_3 = Mouse.isButtonDown(1);

		KEY_B = Keyboard.isKeyDown(Keyboard.KEY_B);
		KEY_F = Keyboard.isKeyDown(Keyboard.KEY_F);
		KEY_SPACE = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
	}

	public void render() {
		glPushMatrix();
		glTranslatef(32, 32, 0);

		for (int i = 0; i < tiles.length; i++) {
			int x = (i % width) * Tile.getTileSize();
			int y = (i / width) * Tile.getTileSize();
			tiles[i].render(x, y);
		}

		waveManager.render();

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render();
		}

		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).render();
		}

		glPopMatrix();

		if (selectedTowerType != EnumTowerType.None) {
			renderGhostTower();
		}
	}

	private void renderGhostTower() {
		int x = Mouse.getX() / 32 - 1;
		int y = Mouse.getY() / 32 - 1;

		if (x >= 0 && x < DEFAULT_WIDTH && y >= 0 && y < DEFAULT_HEIGHT) {
			boolean flag = tiles[x + y * DEFAULT_WIDTH].canPlaceTower();

			x = (x + 1) * 32;
			y = (y + 1) * 32;

			switch (selectedTowerType) {
			case Basic:
				TowerBasic.renderGhost(x, y, flag);
				break;
			case Fast:
				TowerFast.renderGhost(x, y, flag);
				break;
			default:
				break;
			}
		}
	}
}
