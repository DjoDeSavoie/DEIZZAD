package entities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

import utilz.LoadSave;
import levels.Level;
import Gamestates.Playing;

public class EnemyManager {

    private Playing playing;

    //on crée un tableau d'ennemis pour chaque type d'ennemi
    private ArrayList<RedOrc> redorcs = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
    }

    // on ajoute les enemies a partir des fichiers images
    public void LoadEnemies(Level level){
        // on ajoute les redorcs
        redorcs = level.getRedOrcs();
        // System.out.println("enemies1 size : " + enemies1.size());

        // on ajoute les autres enemies
    }

    // mise a jour des enemies
    public void update(int[][] lvlData, Player player){
        boolean allEnemiesDead = false;
        //on update les redorcs
        for(RedOrc redorc : redorcs)
            if(redorc.isActive()){
                redorc.update(lvlData, player);
                allEnemiesDead = true;
            }
        if(!allEnemiesDead)
            playing.setLevelCompleted(true);
        
        // on update les autres enemies
    }

    public void draw(Graphics g, int xLvlOffset){
        // on dessine les redorcs
        for (RedOrc redorc : redorcs) {
            if (redorc.isActive()) {
                redorc.draw(g, xLvlOffset);
            }
        }
        // on dessine les autres enemies
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (RedOrc redorc : redorcs) {
            if (redorc.isActive()) {
                if (attackBox.intersects(redorc.getHitbox())) {
                    // Attendre que l'animation de l'attaque soit terminée pour infliger des dégâts avec un timer

                    int delay = 500; // Délai en millisecondes
                    Timer timer = new Timer(delay, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Le code ici sera exécuté après le délai spécifié
                            redorc.hurt(10);
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
		for (RedOrc redorc : redorcs)
			redorc.resetEnemy();
	}
}
