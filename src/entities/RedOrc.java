 package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RedOrc extends Enemy {

    private static BufferedImage[][] redorcArr;

    static {
        loadRedOrcImages();
    }

    public RedOrc(float x, float y) {
        super(x, y, REDORC_WIDTH, REDORC_HEIGHT, REDORC);
        initHitbox(x, y, (int)(25*Game.SCALE), (int)(31*Game.SCALE));
        initAttackBox();
    }    
    
    private static void loadRedOrcImages() {
        redorcArr = new BufferedImage[5][6];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.REDORC_SPRITE);
        for (int j = 0; j < redorcArr.length; j++) {
            for (int i = 0; i < redorcArr[j].length; i++) {
                redorcArr[j][i] = temp.getSubimage(i * REDORC_WIDTH_DEFAULT, j * REDORC_HEIGHT_DEFAULT,
                        REDORC_WIDTH_DEFAULT, REDORC_HEIGHT_DEFAULT);
            }
        }
    }

    public void draw(Graphics g) {
        // Logique de dessin spécifique à RedOrc
        int x = (int) getHitbox().x - 15;
        int y = (int) getHitbox().y - 47;
        int width = REDORC_WIDTH_DEFAULT;
        int height = REDORC_HEIGHT_DEFAULT;

        BufferedImage imageToDraw;

        if (walkDir == LEFT) {
            imageToDraw = getMirroredImage(redorcArr[getEnemyState()][getAnimaIndex()]);
        } else {
            imageToDraw = redorcArr[getEnemyState()][getAnimaIndex()];
        }

        g.drawImage(imageToDraw, x, y, width, height, null);
    }

    
}    

    
