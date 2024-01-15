package Gamestates;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import objects.ObjectManager;

import main.Game;
import ui.EndLevelOverlay;
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

    public Playing(Game game) {
        super(game);
        initClasses();
        calculatelvlOffset();
        loadStartLevel();
    }

    private void calculatelvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawnPos(levelManager.getCurrentLevel().getPlayerSpawn());
	}

    // charge les enemies du premier level
	private void loadStartLevel() {
		enemyManager.LoadEnemies(levelManager.getCurrentLevel());
	}

    public void setMaxLvlOffset(int LvlOffset) {
        this.maxLvlOffsetX = LvlOffset;
    }

    // initialise toutes les classes relatives au jeu
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

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        //celui qui fait menu juste decommente cette ligne pour le if paused et supp celle d'en bas
        //if(!paused && !gameOver){
        if (levelComplete) {
            endLevelOverlay.update();
        } else if(!gameOver){
            levelManager.update();
            objectManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToboredr();
            
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelComplete = levelCompleted;
    }

    //pour verifier si le joueur est proche du bord gauche ou droit
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

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);

        //celui qui fait le menu, si tu fais un if avant celui la stp met un else if pour gameover a la place du if
        if(gameOver)
            gameOverOverlay.draw(g);
        else if (levelComplete)
            endLevelOverlay.draw(g);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}

    @Override
    public void mousePressed(MouseEvent e) {
        if(!gameOver && levelComplete)
            endLevelOverlay.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!gameOver && levelComplete)
            endLevelOverlay.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!gameOver && levelComplete)
            endLevelOverlay.mouseMoved(e);
    }

    @Override
    public void mouseclicked(MouseEvent e) {
        if(!gameOver)
            if(e.getButton() == MouseEvent.BUTTON1){
                player.setAttacking(true);
		    }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(gameOver)
            gameOverOverlay.keyPressed(e);
        else{
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

    @Override
    public void keyReleased(KeyEvent e) {
        if(!gameOver)
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
    
    // reset toutes les variables pour recommencer une partie
    public void resetAll() {
		gameOver = false;
		//paused = false;
        levelComplete = false;
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

}
