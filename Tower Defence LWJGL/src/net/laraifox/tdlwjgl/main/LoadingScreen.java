package net.laraifox.tdlwjgl.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.laraifox.lib.math.MathHelper;

public class LoadingScreen implements Runnable {
	private Thread thread;
	private Canvas canvas;
	private Image image;
	private boolean isRunning;

	public LoadingScreen(Canvas canvas) throws IOException {
		super();

		this.canvas = canvas;
		this.image = ImageIO.read(new File("res/title/loading_screen.png"));
		this.isRunning = false;
	}

	public void start() {
		if (isRunning)
			return;

		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() throws InterruptedException {
		if (!isRunning)
			return;

		isRunning = false;
		thread.join();
	}

	@Override
	public void run() {
		long previousUpdate = System.nanoTime();
		long nanosecondsPerUpdate = (long) (MathHelper.MILLIARD / 30.0);

		while (isRunning) {
			long currentTime = System.nanoTime();
			long deltaTime = currentTime - previousUpdate;

			if (deltaTime >= nanosecondsPerUpdate) {
				previousUpdate += nanosecondsPerUpdate;

				BufferStrategy bs = canvas.getBufferStrategy();
				if (bs == null) {
					canvas.createBufferStrategy(3);
					continue;
				}
				Graphics graphics = bs.getDrawGraphics();
				graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
				graphics.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
				graphics.dispose();
				bs.show();
			} else {
				// try {
				// Thread.sleep((nanosecondsPerUpdate - deltaTime) / 500000);
				// } catch (InterruptedException e) {
				// e.printStackTrace();
				// }
			}
		}
	}
}
