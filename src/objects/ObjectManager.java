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
 * La classe ObjectManager gère les objets du jeu, tels que les potions et les conteneurs.
 */

public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;

    public ObjectManager(Playing playing){
        this.playing = playing;
        loadImgs();

        potions = new ArrayList<>();
        potions.add(new Potion(300, 300, RED_POTION));
        potions.add(new Potion(400, 300, BLUE_POTION));

        containers = new ArrayList<>();
        containers.add(new GameContainer(600, 300, BOX));
        containers.add(new GameContainer(500, 300, BARREL));
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
 * mise a jour des textures objets 
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
/*
 * dessine les objets
 */
    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);

    }
/*
 * dessine les containers
 */
    private void drawContainers(Graphics g, int xLvlOffset) {
        for(GameContainer gc : containers){
            if(gc.isActive()){
                int type = 0; //0 = box, 1 = barrel
                if(gc.getObjType() == BARREL){
                    type = 1;
                }
                //appel de la fonction drawImage de la classe Graphics en fonction de la valeur de type
            g.drawImage(containerImgs[type][gc.getAniIndex()],
                    (int) (gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset),
                    (int) (gc.getHitbox().y - gc.getyDrawOffset()),
                    CONTAINER_WIDTH,
                    CONTAINER_HEIGHT,
                    null);

            }
        }
    }
/*
 * dessine les potions
 */
    private void drawPotions(Graphics g, int xLvlOffset) {
        for(Potion p : potions){
            if(p.isActive()){
                int type = 0; //0 = red, 1 = blue
                if(p.getObjType() == BLUE_POTION){
                    type = 1;
                }
                //appel de la fonction drawImage de la classe Graphics en fonction de la valeur de type
            g.drawImage(potionImgs[type][p.getAniIndex()],
                    (int) (p.getHitbox().x - p.getxDrawOffset() - xLvlOffset),
                    (int) (p.getHitbox().y - p.getyDrawOffset()),
                    POTION_WIDTH,
                    POTION_HEIGHT,
                    null);

            }
        }
    }
}
