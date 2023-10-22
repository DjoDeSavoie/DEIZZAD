package main;

public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread thread;
	private GameWindow gameWindow;
	private final int FPS_SET = 120;

	public Game() {
		gamePanel = new GamePanel();
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameloop();
	}

	private void startGameloop() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();

		int frames = 0;
		long lastcheck = System.currentTimeMillis();
		while (true) {
			now = System.nanoTime();
			if (now - lastFrame >= timePerFrame) {

				gamePanel.repaint();
				lastFrame = now;
				frames++;
			}

			if (System.currentTimeMillis() - lastcheck >= 1000) {
				lastcheck = System.currentTimeMillis();
				System.out.println("FPS :" + frames);
				frames = 0;
			}
		}
	}

}