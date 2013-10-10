package net.laraifox.tdlwjgl.level;

import java.util.ArrayList;
import java.util.List;

import net.laraifox.lib.graphics.VectorFont;
import net.laraifox.tdlwjgl.entity.Entity;
import net.laraifox.tdlwjgl.enums.EnumFontSize;
import net.laraifox.tdlwjgl.enums.EnumTowerType;
import net.laraifox.tdlwjgl.projectile.Projectile;
import net.laraifox.tdlwjgl.projectile.ProjectileBasic;
import net.laraifox.tdlwjgl.projectile.ProjectileFast;
import net.laraifox.tdlwjgl.tower.Tower;
import net.laraifox.tdlwjgl.tower.TowerBasic;
import net.laraifox.tdlwjgl.tower.TowerFast;
import net.laraifox.tdlwjgl.util.GameTimer;
import net.laraifox.tdlwjgl.util.MouseHandler;
import net.laraifox.tdlwjgl.util.StringRenderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Level {
	protected Tile[] tiles;
	protected int width, height;
	protected WaveManager waveManager;
	protected List<WaypointList> waypointLists;

	private Player player;
	private List<Projectile> projectiles;
	private List<Tower> towers;

	private EnumTowerType selectedTowerType;

	private boolean MOUSE_3;
	private boolean KEY_B;
	private boolean KEY_F;
	private boolean KEY_SPACE;

	public Level() {
		this.player = new Player();
		this.projectiles = new ArrayList<Projectile>();
		this.tiles = new Tile[0];
		this.towers = new ArrayList<Tower>();
		this.selectedTowerType = EnumTowerType.None;
		this.waveManager = new WaveManager();
		this.waypointLists = new ArrayList<WaypointList>();
	}

	public Tile getTileAt(int i) {
		return tiles[i];
	}

	public boolean isLevelComplete() {
		return waveManager.isFinished();
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
			tiles[x + y * width].setTowerPlaceable(false);
		}
	}

	private void destroyTower(int x, int y) {
		for (int i = 0; i < towers.size(); i++) {
			if (towers.get(i).getTileX() == x && towers.get(i).getTileY() == y) {
				player.money += (int) (towers.get(i).getCost() * 0.75);
				towers.remove(i);
				tiles[x + y * width].setTowerPlaceable(true);
			}
		}
	}

	private void createProjectile(Tower tower) {
		Projectile projectile = null;
		switch (tower.getTowerType()) {
		case Basic:
			projectile = new ProjectileBasic(tower.getCenter(), tower.getTheta(), tower.getTargetWave(), tower.getTargetEntity());
			projectile.setAlive(true);
			break;
		case Fast:
			projectile = new ProjectileFast(tower.getCenter(), tower.getTheta(), tower.getTargetWave(), tower.getTargetEntity());
			projectile.setAlive(true);
			break;
		default:
			break;
		}
		if (projectile != null)
			projectiles.add(projectile);
	}

	public void update(GameTimer gameTimer) {
		StringRenderer.addString("Lives: " + player.getLives(), 16, 32, EnumFontSize.Small, VectorFont.ALIGN_LEFT);
		StringRenderer.addString("Money: " + player.money, 16, 48, EnumFontSize.Small, VectorFont.ALIGN_LEFT);

		for (int i = 0; i < projectiles.size(); i++) {
			if (!projectiles.get(i).isAlive()) {
				projectiles.remove(i);
				continue;
			}

			Entity target = waveManager.getEntityAt(projectiles.get(i).getWaveIndex(), projectiles.get(i).getEntityIndex());
			projectiles.get(i).update(target);
			if (projectiles.get(i).pathIntersects(target.getHitbox())) {
				target.dealDamage(projectiles.get(i).getDamage());
				projectiles.get(i).setAlive(false);
			}
		}

		waveManager.update(gameTimer, this);

		if (MouseHandler.isButtonDown(0)) {
			int x = MouseHandler.getX() / Tile.getTileSize() - 1;
			int y = MouseHandler.getY() / Tile.getTileSize() - 1;
			if (x >= 0 && x < width && y >= 0 && y < height) {
				if (tiles[x + y * width].isTowerPlaceable()) {
					buildTower(selectedTowerType, x, y);
				}
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
			for (int i = 0; i < tiles.length; i++) {
				int x = i % width;
				int y = i / width;
				if (tiles[x + y * width].isTowerPlaceable()) {
					player.money += selectedTowerType.getBaseCost();
					buildTower(selectedTowerType, x, y);
				}
			}
		}

		if (MouseHandler.isButtonDown(1)) {
			if (!MOUSE_3) {
				if (selectedTowerType != EnumTowerType.None) {
					selectedTowerType = EnumTowerType.None;
				} else {
					int x = MouseHandler.getX() / Tile.getTileSize() - 1;
					int y = MouseHandler.getY() / Tile.getTileSize() - 1;
					if (x >= 0 && x < width && y >= 0 && y < height) {
						if (!tiles[x + y * width].isTowerPlaceable()) {
							destroyTower(x, y);
						}
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
				if (waveManager.isGameStarted())
					waveManager.startNextWave(gameTimer);
				else
					waveManager.start();
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_M))
			player.money += 10;

		for (int i = 0; i < towers.size(); i++) {
			towers.get(i).update(gameTimer, waveManager);
			if (towers.get(i).canFire(gameTimer))
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
		MOUSE_3 = MouseHandler.isButtonDown(1);

		KEY_B = Keyboard.isKeyDown(Keyboard.KEY_B);
		KEY_F = Keyboard.isKeyDown(Keyboard.KEY_F);
		KEY_SPACE = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
	}

	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(Tile.getTileSize(), Tile.getTileSize(), 0);
		for (int i = 0; i < tiles.length; i++) {
			int x = (i % width) * Tile.getTileSize();
			int y = (i / width) * Tile.getTileSize();
			tiles[i].render(x, y);
		}

		waveManager.render();
		for (int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).render();
		for (int i = 0; i < towers.size(); i++)
			towers.get(i).render();

		if (selectedTowerType != EnumTowerType.None) {
			int x = MouseHandler.getX() / Tile.getTileSize() - 1;
			int y = MouseHandler.getY() / Tile.getTileSize() - 1;
			if (x >= 0 && x < width && y >= 0 && y < height) {
				Tower.renderGhost(selectedTowerType, x * Tile.getTileSize(), y * Tile.getTileSize(), tiles[x + y * width].isTowerPlaceable());
			}
		}
		GL11.glPopMatrix();
	}

	public int getWaypointListLength(int i) {
		return waypointLists.get(i).getLength();
	}

	public Waypoint getWaypoint(int i, int j) {
		return waypointLists.get(i).getWaypoint(j);
	}

	public Player getPlayer() {
		return player;
	}
}
