package net.laraifox.tdlwjgl.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameTimer {
	private long startTime;
	private long currentTime;
	private long deltaTime;
	private int framerate;
	private int ticks;

	public GameTimer() {
		this.resetTimer(0);
	}

	public GameTimer(int framerate) {
		this.resetTimer(framerate);
	}

	public void resetTimer() {
		this.resetTimer(0);
	}

	public void resetTimer(int framerate) {
		this.startTime = System.nanoTime();
		this.currentTime = startTime;
		this.deltaTime = currentTime - startTime;
		this.framerate = framerate;
		this.ticks = 0;
	}

	public void update() {
		currentTime = System.nanoTime();
		deltaTime = currentTime - startTime;
		if (framerate > 0) {
			ticks = Math.round((getTotalTimeMilliseconds() / (1000.0f / (float) framerate)));
		}
	}

	public String toString() {
		Date date = new Date(getTotalTimeMilliseconds() - 3600000);
		SimpleDateFormat formatter = new SimpleDateFormat("H:mm:ss");
		return formatter.format(date);
	}

	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}

	public int getTicks() {
		return ticks;
	}

	public int getTimeNanoseconds() {
		return (int) (deltaTime % 1000000000);
	}

	public int getTimeMicroseconds() {
		return (int) (deltaTime / 1000) % 1000000;
	}

	public int getTimeMilliseconds() {
		return (int) (deltaTime / 1000000) % 1000;
	}

	public int getTimeCentiseconds() {
		return (int) (deltaTime / 10000000) % 100;
	}

	public int getTimeDeciseconds() {
		return (int) (deltaTime / 100000000) % 10;
	}

	public int getTimeSeconds() {
		return (int) (deltaTime / 1000000000) % 60;
	}

	public int getTimeMinutes() {
		return (int) (deltaTime / 60000000000L) % 60;
	}

	public long getTotalTimeNanoseconds() {
		return deltaTime;
	}

	public long getTotalTimeMicroseconds() {
		return (long) (deltaTime / 1000);
	}

	public long getTotalTimeMilliseconds() {
		return (long) (deltaTime / 1000000);
	}

	public long getTotalTimeCentiseconds() {
		return (long) (deltaTime / 10000000);
	}

	public long getTotalTimeDeciseconds() {
		return (long) (deltaTime / 100000000);
	}

	public int getTotalTimeSeconds() {
		return (int) (deltaTime / 1000000000);
	}

	public int getTotalTimeMinutes() {
		return (int) (deltaTime / 60000000000L);
	}

	public int getTotalTimeHours() {
		return (int) (deltaTime / 3600000000000L);
	}
}
