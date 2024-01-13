/**
 * @file Game.java
 * @brief Classe principale du jeu, gère l'initialisation, la boucle de jeu et les mises à jour.
 */

package main;

import java.awt.Graphics;
import Gamestates.Playing;
import Gamestates.Gamestate;
import Gamestates.Menu;

/**
 * @class Game
 * @brief Classe principale du jeu, gère l'initialisation, la boucle de jeu et les mises à jour.
 */
public class Game implements Runnable {

    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread thread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;

    /**
     * Les dimensions par défaut des tuiles du jeu.
     */
    public final static int TILES_DEFAULT_SIZE = 32;

    /**
     * L'échelle de taille du jeu.
     */
    public final static float SCALE = 1.5f;

    /**
     * Le nombre de tuiles en largeur du jeu.
     */
    public final static int TILES_IN_WIDTH = 26;

    /**
     * Le nombre de tuiles en hauteur du jeu.
     */
    public final static int TILES_IN_HEIGHT = 14;

    /**
     * La taille des tuiles du jeu.
     */
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

    /**
     * La largeur totale du jeu en pixels.
     */
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;

    /**
     * La hauteur totale du jeu en pixels.
     */
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    /**
     * Constructeur de la classe Game, initialise les classes nécessaires.
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameloop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameloop() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Met à jour les éléments du jeu en fonction de l'état actuel du jeu.
     */
    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    /**
     * Dessine les éléments du jeu en fonction de l'état actuel du jeu.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    public void render(Graphics g) {
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
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
                // System.out.println("FPS :" + frames + "| UPS :" + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    /**
     * Gère la perte de focus de la fenêtre du jeu.
     */
    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    /**
     * Retourne l'objet Menu associé au jeu.
     * @return L'objet Menu associé au jeu.
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Retourne l'objet Playing associé au jeu.
     * @return L'objet Playing associé au jeu.
     */
    public Playing getPlaying() {
        return playing;
    }
}
