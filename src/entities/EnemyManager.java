package entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.w3c.dom.css.Rect;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import utilz.LoadSave;
import Gamestates.Playing;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Directions.*;

public class EnemyManager {

    private Playing playing;   
    private BufferedImage[][] redorcArr; 
    private ArrayList<RedOrc> redorcs = new ArrayList<>();

    public EnemyManager(Playing playing){
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies(){
        redorcs = LoadSave.GetRedOrc();
        // System.out.println("enemies1 size : " + enemies1.size());
    }

    public void update(int[][] lvlData, Player player){
        for(RedOrc redorc : redorcs){
            if(redorc.isActive())
                redorc.update(lvlData, player);
        }
    }

    public void draw(Graphics g){
        drawRedOrc(g);
    }

    // Cette méthode est utilisée pour obtenir une version miroir d'un BufferedImage
    private BufferedImage getMirroredImage(BufferedImage image) {
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-image.getWidth(), 0);
    
        BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mirroredImage.createGraphics();
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    
        return mirroredImage;
    }
    
    // Cette méthode est utilisée pour dessiner un RedOrc
    public void drawRedOrc(Graphics g) {
        for (RedOrc redorc : redorcs) {
            if(redorc.isActive()){
            int x = (int) redorc.getHitbox().x - 15;
            int y = (int) redorc.getHitbox().y - 47;
            int width = REDORC_WIDTH_DEFAULT;
            int height = REDORC_HEIGHT_DEFAULT;
    
            BufferedImage imageToDraw;
    
            if (redorc.walkDir == LEFT) {
                //si le RedOrc se déplace vers la gauche
                imageToDraw = getMirroredImage(redorcArr[redorc.getEnemyState()][redorc.getAnimaIndex()]);
            } else {
                // si le RedOrc se déplace vers la droite
                imageToDraw = redorcArr[redorc.getEnemyState()][redorc.getAnimaIndex()];
            }
    
            g.drawImage(imageToDraw, x, y, width, height, null);
    
            redorc.drawHitbox(g, 0);
            redorc.drawAttackBox(g, 0);
        }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox){
        for(RedOrc redorc : redorcs){
            if(redorc.isActive()){
                if(attackBox.intersects(redorc.getHitbox())){
                    redorc.hurt(10);
                    return;
                }
            }
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
