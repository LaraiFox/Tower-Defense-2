package net.laraifox.tdlwjgl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.laraifox.tdlwjgl.enums.EnumFontSize;

import net.laraifox.lib.graphics.Texture;
import net.laraifox.lib.graphics.TextureLoader;

public class SpriteSheet {
	public static final SpriteSheet Entities = new SpriteSheet("res/level/entities.png", 32, 32);
	public static final SpriteSheet Objects = new SpriteSheet("res/level/terrain.png", 32, 32);
	public static final SpriteSheet Projectiles = new SpriteSheet("res/level/projectiles.png", 8, 8);
	public static final SpriteSheet Terrain = new SpriteSheet("res/level/terrain.png", 32, 32);
	public static final SpriteSheet Towers = new SpriteSheet("res/level/towers.png", 32, 32);

	public static final SpriteSheet Font_Small = new SpriteSheet("res/gui/font_small.png", EnumFontSize.Small.getWidth(), EnumFontSize.Small.getHeight());
	public static final SpriteSheet Font_Medium = new SpriteSheet("res/gui/font_medium.png", EnumFontSize.Medium.getWidth(), EnumFontSize.Medium.getHeight());
	public static final SpriteSheet Font_Large = new SpriteSheet("res/gui/font_large.png", EnumFontSize.Large.getWidth(), EnumFontSize.Large.getHeight());

	private Texture texture;
	private int tileWidth, tileHeight;
	private float textureTileWidth, textureTileHeight;

	public SpriteSheet(String path, int tileWidth, int tileHeight) {
		try {
			this.texture = TextureLoader.getTexture(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.textureTileWidth = (float) tileWidth / (float) texture.getTextureWidth();
		this.textureTileHeight = (float) tileHeight / (float) texture.getTextureHeight();
	}

	public void bindSheetTexture() {
		texture.bindTexture();
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public float getLeftOfTile(int x) {
		return textureTileWidth * x;
	}

	public float getRightOfTile(int x, int width) {
		return textureTileWidth * (x + width);
	}

	public float getBottomOfTile(int y) {
		return textureTileHeight * y;
	}

	public float getTopOfTile(int y, int height) {
		return textureTileHeight * (y + height);
	}
}
