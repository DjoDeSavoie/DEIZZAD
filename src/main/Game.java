package main;

import entities.Enemy;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;

import java.awt.Graphics;

public class Game implements Runnable {

	private GamePanel gamePanel;
	private Thread thread;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200; 

	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1.5f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);
		gamePanel.requestFocus();
		
		startGameloop();
	}

	private void initClasses() {
		levelManager = new LevelManager(this);
		enemyManager = new EnemyManager();
		player = new Player(200, 200, (int) (80 * SCALE), (int) (80 * SCALE));
		player.loadlevelData(levelManager.getCurrentLevel().GetLevelData());
		
	}

	private void startGameloop() {
		thread = new Thread(this);
		thread.start();
	}

	public void update() {
		player.update();
		levelManager.update();
		enemyManager.update(levelManager.getCurrentLevel().GetLevelData());
	}

	public void render(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		enemyManager.draw(g);
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
				update();
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

	public void windowFocusLost(){
		player.resetDirBooleans();
	}

	public Player getPlayer(){
		return player;
	}
}
