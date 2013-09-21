package net.laraifox.tdlwjgl.level;

public class Player {
	private byte lives;
	
	public int money;
	
	public Player() {
		this.lives = 10;
		this.money = 100;
	}
	
	public void reset() {
		lives = 10;
		money = 100;
	}
	
	public byte getLives() {
		return lives;
	}
	
	public void removeLife() {
		lives--;
	}
}
