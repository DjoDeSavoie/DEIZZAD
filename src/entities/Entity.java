/**
 * @file Entity.java
 * @brief Contient la définition de la classe Entity.
 */

package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @brief Classe abstraite représentant une entité dans le jeu.
 */
public abstract class Entity {
    
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;

    /**
     * @brief Constructeur de la classe Entity.
     * @param x Position horizontale de l'entité.
     * @param y Position verticale de l'entité.
     * @param width Largeur de l'entité.
     * @param height Hauteur de l'entité.
     */
    public Entity(float x, float y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @brief Dessine la hitbox de l'entité.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param xLvlOffset Décalage horizontal du niveau.
     */
    protected void drawHitbox(Graphics g, int xLvlOffset){
        // pour debugger le hitbox
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    /**
     * @brief Initialise la hitbox de l'entité.
     * @param x Position horizontale de la hitbox.
     * @param y Position verticale de la hitbox.
     * @param width Largeur de la hitbox.
     * @param height Hauteur de la hitbox.
     */
    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    /**
     * @brief Renvoie la hitbox de l'entité.
     * @return Objet représentant la hitbox de l'entité.
     */
    public Rectangle2D.Float getHitbox(){
        return hitbox;
    }

}
