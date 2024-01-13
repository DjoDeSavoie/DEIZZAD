/**
 * @file RedOrc.java
 * @brief Contient la définition de la classe RedOrc.
 */

package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @brief Classe représentant un ennemi RedOrc dans le jeu.
 */
public class RedOrc extends Enemy {

    private static BufferedImage[][] redorcArr;

    static {
        loadRedOrcImages();
    }

    /**
     * @brief Constructeur de la classe RedOrc.
     * @param x Position horizontale initiale du RedOrc.
     * @param y Position verticale initiale du RedOrc.
     */
    public RedOrc(float x, float y) {
        super(x, y, REDORC_WIDTH, REDORC_HEIGHT, REDORC);
        initHitbox(x, y, (int)(25*Game.SCALE), (int)(31*Game.SCALE));
        initAttackBox();
    }

    /**
     * @brief Charge les images du RedOrc à partir d'une feuille de sprites.
     */
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

    /**
     * @brief Dessine le RedOrc.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param xLvlOffset Décalage horizontal du niveau.
     */
    public void draw(Graphics g, int xLvlOffset) {
        // Logique de dessin spécifique à RedOrc
        int x = (int) getHitbox().x - xLvlOffset - REDORC_DRAWOFFSET_X;
        int y = (int) getHitbox().y - REDORC_DRAWOFFSET_Y ;
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
