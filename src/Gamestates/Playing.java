/**
 * @file Playing.java
 * @brief Contient la définition de la classe Playing représentant l'état de jeu en cours.
 */

package Gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import objects.ObjectManager;
import java.awt.Graphics;

import main.Game;
import ui.EndLevelOverlay;

/**
 * @class Playing
 * @brief Représente l'état de jeu en cours.
 * Cette classe étend la classe abstraite State et implémente l'interface Statemethods.
 */
public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private GameOverOverlay gameOverOverlay;
    private EndLevelOverlay endLevelOverlay;
    private boolean gameOver;
    private boolean levelComplete;

    private int xLvlOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;

    /**
     * Constructeur de la classe Playing.
     * @param game Instance du jeu.
     */
    public Playing(Game game) {
        super(game);
        initClasses();
        calculatelvlOffset();   
        loadStartLevel();
    }

    /**
     * Calcule le décalage maximal du niveau.
     */
    private void calculatelvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    /**
     * Charge le niveau suivant.
     */
    public void loadNextLevel() {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawnPos(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    /**
     * Charge les ennemis du premier niveau.
     */
    private void loadStartLevel() {
        enemyManager.LoadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    /**
     * Définit le décalage maximal du niveau.
     * @param LvlOffset Le décalage maximal du niveau.
     */
    public void setMaxLvlOffset(int LvlOffset) {
        this.maxLvlOffsetX = LvlOffset;
    }

    /**
     * Initialise toutes les classes relatives au jeu.
     */
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player = new Player(200, 200, (int) (75 * Game.SCALE), (int) (73 * Game.SCALE), this);
        player.loadlvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawnPos(levelManager.getCurrentLevel().getPlayerSpawn());
       
        gameOverOverlay = new GameOverOverlay(this);
        endLevelOverlay = new EndLevelOverlay(this);
    }

    /**
     * Gère la perte de focus de la fenêtre du jeu.
     */
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    /**
     * Obtient le joueur.
     * @return L'instance du joueur.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Met à jour l'état de jeu.
     */
    @Override
    public void update() {
        if (levelComplete) {
            endLevelOverlay.update();
        } else if (!gameOver) {
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToboredr();
            
        }
    }

    /**
     * Définit l'état de fin de niveau.
     * @param levelCompleted L'état de fin de niveau.
     */
    public void setLevelCompleted(boolean levelCompleted) {
        this.levelComplete = levelCompleted;
    }

    /**
     * Vérifie si le joueur est proche du bord gauche ou droit.
     */
    private void checkCloseToboredr() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLvlOffset;
        if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        } else if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        }

        if (xLvlOffset < 0) {
            xLvlOffset = 0;
        } else if (xLvlOffset > maxLvlOffsetX) {
            xLvlOffset = maxLvlOffsetX;
        }
    }

    /**
     * Définit l'état de fin de jeu.
     * @param gameOver L'état de fin de jeu.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Dessine l'état de jeu.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        if (gameOver)
            gameOverOverlay.draw(g);
        else if (levelComplete)
            endLevelOverlay.draw(g);
    }

    /**
     * Vérifie si l'attaque du joueur touche un ennemi.
     * @param attackBox La boîte d'attaque du joueur.
     */
    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    /**
     * Gère l'événement de pression de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (!gameOver && levelComplete)
            endLevelOverlay.mousePressed(e);
    }

    /**
     * Gère l'événement de relâchement de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gameOver && levelComplete)
            endLevelOverlay.mouseReleased(e);
    }

    /**
     * Gère le mouvement de la souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gameOver && levelComplete)
            endLevelOverlay.mouseMoved(e);
    }

    /**
     * Gère l'événement de clic de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseclicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            }
    }

    /**
     * Gère l'événement de touche enfoncée.
     * @param e L'événement de la touche.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.setLeft(true);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(true);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(true);
                    break;
                case KeyEvent.VK_CONTROL:
                    player.setAttacking(true);
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    Gamestate.state = Gamestate.MENU;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Gère l'événement de touche relâchée.
     * @param e L'événement de la touche.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    player.setLeft(false);
                    break;
                case KeyEvent.VK_RIGHT:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    /**
     * Réinitialise toutes les variables pour recommencer une partie.
     */
    public void resetAll() {
        gameOver = false;
        levelComplete = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    /**
     * Obtient le gestionnaire d'ennemis.
     * @return L'instance du gestionnaire d'ennemis.
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

}
