package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    // private Playing playing;   
    private BufferedImage[][] redorcArr; 
    private ArrayList<RedOrc> redorcs = new ArrayList<>();

    public EnemyManager(/*Playing playing*/){
        //this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies(){
        redorcs = LoadSave.GetRedOrc();
        // System.out.println("enemies1 size : " + enemies1.size());
    }

    public void update(int[][] lvlData){
        for(RedOrc redorc : redorcs){
            redorc.update(lvlData);
        }
    }

    public void draw(Graphics g){
        drawEnemies1(g);
    }

    public void drawEnemies1(Graphics g){
        for(RedOrc redorc : redorcs){
            g.drawImage(redorcArr[redorc.getEnemyState()][redorc.getAnimaIndex()], (int) redorc.getHitbox().x-15, (int) redorc.getHitbox().y-47, REDORC_WIDTH_DEFAULT, REDORC_HEIGHT_DEFAULT, null);
        }  
    }

    private void loadEnemyImages(){
        redorcArr = new BufferedImage[5][6];// mettre 6 pour le 2ème apres !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.REDORC_SPRITE);
        for(int j=0; j<redorcArr.length; j++){
            for(int i=0; i<redorcArr[j].length; i++){
                redorcArr[j][i] = temp.getSubimage(i*REDORC_WIDTH_DEFAULT, j*REDORC_HEIGHT_DEFAULT, REDORC_WIDTH_DEFAULT, REDORC_HEIGHT_DEFAULT);
            }   
        }


    }
}
