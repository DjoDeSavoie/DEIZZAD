package main;

public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread thread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

	public Game() {
		gamePanel = new GamePanel();
		new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameloop();
	}

	private void startGameloop() {
		thread = new Thread(this);
		thread.start();
	}

	public void update() {
		gamePanel.updateGame();
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastcheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;

			previousTime = currentTime;

			if (deltaU >= 1) {
				// update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;

			}

			if (System.currentTimeMillis() - lastcheck >= 1000) {
				lastcheck = System.currentTimeMillis();
				System.out.println("FPS :" + frames + "| UPS :" + updates);
				frames = 0;
				updates = 0;
			}
		}
	}
}
