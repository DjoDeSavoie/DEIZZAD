/**
 * @file EnemyManager.java
 * @brief Contient la définition de la classe EnemyManager.
 */

package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import levels.Level;
import Gamestates.Playing;

/**
 * @brief Classe gérant la gestion des ennemis dans le jeu.
 */
public class EnemyManager {

    private Playing playing;

    // tableau d'enemies
    private ArrayList<? extends Enemy> enemies = new ArrayList<>(); 

    /**
     * @brief Constructeur de la classe EnemyManager.
     * @param playing Objet représentant l'état de jeu actuel.
     */
    public EnemyManager(Playing playing){
        this.playing = playing;
    }

    /**
     * @brief Charge les ennemis à partir du niveau spécifié.
     * @param level Objet représentant le niveau du jeu.
     */
    public void LoadEnemies(Level level){
        enemies = level.getEnemies(); 
    }

    /**
     * @brief Met à jour les ennemis en fonction du joueur et des données du niveau.
     * @param lvlData Tableau représentant les données du niveau.
     * @param player Objet représentant le joueur.
     */
    public void update(int[][] lvlData, Player player){
        boolean OneRemainingEnemy = false;
        // on update les enemies
        for(Enemy enemy : enemies) 
            if(enemy.isActive()){
                enemy.update(lvlData, player);
                OneRemainingEnemy = true;
            }
        if(!OneRemainingEnemy)
            playing.setLevelCompleted(true);
    }

    /**
     * @brief Dessine les ennemis sur l'écran.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param xLvlOffset Décalage horizontal du niveau.
     */
    public void draw(Graphics g, int xLvlOffset){
        // on dessine les enemies
        for (Enemy enemy : enemies) {
            if (enemy.isActive()) {
                enemy.draw(g, xLvlOffset);
            }
        }
    }

    /**
     * @brief Vérifie si l'ennemi a été touché par une attaque.
     * @param attackBox Boîte d'attaque de l'attaque du joueur.
     */
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

    /**
     * @brief Réinitialise tous les ennemis.
     */
    public void resetAllEnemies() {
		for (Enemy enemy : enemies)
			enemy.resetEnemy();
	}
}
