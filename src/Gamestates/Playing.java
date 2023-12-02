package Gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements Statemethods {

    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;

    private boolean gameOver = false;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (75 * Game.SCALE), (int) (73 * Game.SCALE), this);
        player.loadlvlData(levelManager.getCurrentLevel().GetLevelData());
        gameOverOverlay = new GameOverOverlay(this);
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
        if(!gameOver){
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().GetLevelData(), player);
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        enemyManager.draw(g);

        //celui qui fait le menu, si tu fais un if avant celui la stp met un else if pour gameover a la place du if
        if(gameOver)
            gameOverOverlay.draw(g);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
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
		player.resetAll();
		enemyManager.resetAllEnemies();
	}

}
