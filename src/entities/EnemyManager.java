package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import utilz.LoadSave;

import static utilz.Constants.EnemyConstants.*;

public class EnemyManager {

    // private Playing playing;   
    private BufferedImage[][] enemy1Arr; 
    private ArrayList<Enemy1> enemies1 = new ArrayList<>();

    public EnemyManager(/*Playing playing*/){
        //this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies(){
        enemies1 = LoadSave.GetEnemy1();
        System.out.println("enemies1 size : " + enemies1.size());
    }

    public void update(){
        for(Enemy1 enemy1 : enemies1){
            enemy1.update();
        }
    }

    public void draw(Graphics g){
        drawEnemies1(g);
    }

    public void drawEnemies1(Graphics g){
        for(Enemy1 enemy1 : enemies1){
            g.drawImage(enemy1Arr[enemy1.getEnemyState()][enemy1.getAnimaIndex()], (int) enemy1.getHitbox().x, (int) enemy1.getHitbox().y, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT, null);
        }
    }

    private void loadEnemyImages(){
        enemy1Arr = new BufferedImage[5][5];// mettre 6 pour le 2ème apres !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.ENEMY1_SPRITE);
        for(int j=0; j<enemy1Arr.length; j++){
            for(int i=0; i<enemy1Arr[j].length; i++){
                enemy1Arr[j][i] = temp.getSubimage(i*ENEMY1_WIDTH_DEFAULT, j*ENEMY1_HEIGHT_DEFAULT, ENEMY1_WIDTH_DEFAULT, ENEMY1_HEIGHT_DEFAULT);
            }   
        }


    }
}
