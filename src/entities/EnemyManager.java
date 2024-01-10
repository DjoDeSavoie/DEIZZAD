package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import levels.Level;
import Gamestates.Playing;

public class EnemyManager {

    private Playing playing;

    //tableau d'enemies
    private ArrayList<? extends Enemy> enemies = new ArrayList<>(); 

    public EnemyManager(Playing playing){
        this.playing = playing;
    }

    // on ajoute les enemies a partir des fichiers images
    public void LoadEnemies(Level level){
        enemies = level.getEnemies(); 
        // System.out.println("enemies size : " + enemies.size());
    }

    // mise a jour des enemies
    public void update(int[][] lvlData, Player player){
        boolean OneRemainingEnemy = false;
        //on update les enemies
        for(Enemy enemy : enemies) 
            if(enemy.isActive()){
                enemy.update(lvlData, player);
                OneRemainingEnemy = true;
            }
        if(!OneRemainingEnemy)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset){
        // on dessine les enemies
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                enemy.draw(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                if (attackBox.intersects(enemy.getHitbox())) {
                    // Attendre que l'animation de l'attaque soit terminée pour infliger des dégâts avec un timer

                    int delay = 500; // Délai en millisecondes
                    Timer timer = new Timer(delay, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Le code ici sera exécuté après le délai spécifié
                            enemy.hurt(10);
                        }
                    });

                    timer.setRepeats(false); 
                    timer.start();

                    return;
                }
            }
        }
    }

    // reset tous les enemies
    public void resetAllEnemies() {
		for (Enemy enemy : enemies)
			enemy.resetEnemy();
	}
}
