/**
 * @file ObjectManager.java
 * @brief Classe représentant le gestionnaire d'objets dans le jeu.
 */

package objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;

import Gamestates.Playing;
import utilz.LoadSave;
import levels.*;
import main.Game;

import static utilz.Constants.ObjectConstants.*;

/**
 * @class ObjectManager
 * @brief Classe représentant le gestionnaire d'objets dans le jeu.
 */
public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;

    /**
     * Constructeur de la classe ObjectManager.
     * @param playing L'état de jeu "Playing".
     */
    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();
    }


    public void checkObjectTouched(Rectangle2D.Float hitbox){
        for(Potion p : potions){
            if(p.isActive()) {
                if(hitbox.intersects(p.getHitbox()))
                p.setActive(false);
                applyEffectToPlayer(p); 
            }
        } 
    }

    public void applyEffectToPlayer(Potion p){
        if(p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    public void checkObjectHit(Rectangle2D.Float attackbox){
        for(GameContainer gc : containers){
            if(gc.isActive()){
                if(gc.getHitbox().intersects(attackbox)){
                    gc.setAnimation(true);

                    int type = 0;
                    if(gc.getObjType() == BARREL){
                        type = 1;
                    potions.add(new Potion ((int) (gc.getHitbox().x + gc.getHitbox().width / 2), 
                    (int) (gc.getHitbox().y - gc.getHitbox().height / 2), 
                    type));
                    return;

                    }

                }
            }
        }
    }

    public void loadObjects(levels.Level level) {
        potions = level.getPotions();
        containers = level.getContainers();
    }
 
    /**
     * Charge les images des objets.
     */
    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for(int j = 0; j < potionImgs.length; j++){
            for(int i = 0; i < potionImgs[j].length; i++){
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for(int j = 0; j < containerImgs.length; j++){
            for(int i = 0; i < containerImgs[j].length; i++){
                containerImgs[j][i] = containerSprite.getSubimage(i * 40, j * 30, 40, 30);
            }
        }
    }

    /**
     * Met à jour les textures des objets.
     */
    public void update(){
        for(Potion p : potions){
           if(p.isActive())
                p.update();

        for(GameContainer gc : containers)
            if(gc.isActive())
                gc.update();
        }
    }

    /**
     * Dessine les objets sur l'écran.
     * @param g Le contexte graphique.
     * @param xLvlOffset Le décalage en x du niveau.
     */
    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
    }

    /**
     * Dessine les conteneurs sur l'écran.
     * @param g Le contexte graphique.
     * @param xLvlOffset Le décalage en x du niveau.
     */
    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer gc : containers){
            if(gc.isActive()){
                int type = 0; //0 = box, 1 = barrel
                if(gc.getObjType() == BARREL){
                    type = 1;
                }
                // Appel de la fonction drawImage de la classe Graphics en fonction de la valeur de type.
                g.drawImage(containerImgs[type][gc.getAniIndex()],
                        (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                        (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
        }
    }

    /**
     * Dessine les potions sur l'écran.
     * @param g Le contexte graphique.
     * @param xLvlOffset Le décalage en x du niveau.
     */
    private void drawPotions(Graphics g, int xLvlOffset) {
        for(Potion p : potions){
            if(p.isActive()){
                int type = 0; //0 = red, 1 = blue
                if(p.getObjType() == BLUE_POTION){
                    type = 1;
                }
                // Appel de la fonction drawImage de la classe Graphics en fonction de la valeur de type.
                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitbox().y - p.getyDrawOffset()),
                        POTION_WIDTH,
                        POTION_HEIGHT,
                        null);
            }
        }
    }
    
    public void resetAllObjects() {
        for (Potion p : potions) {
            p.reset();

        for(GameContainer gc : containers)
            gc.reset();
        }
    }

}
