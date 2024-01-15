/**
 * @file GameObject.java
 * @brief Classe représentant un objet de jeu dans le jeu.
 */

package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;
import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class GameObject {
    protected int x, y, objType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    /**
     * Constructeur de la classe GameObject.
     * @param x La position en x de l'objet.
     * @param y La position en y de l'objet.
     * @param objType Le type de l'objet.
     */
    public GameObject(int x, int y, int objType) {
        this.x = x;
        this.y = y;
        this.objType = objType;
    }

    //mise a jour de la valeur de l'incrémentation de l'animation
    protected void updateAnimationTick(){
        aniTick++;
        if(aniTick >= ANI_SPEED){
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmount(objType)){
                aniIndex = 0;
                if(objType == BARREL || objType == BOX){
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    public void reset(){
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if(objType == BARREL || objType == BOX)
            doAnimation = false;
        else
            doAnimation = true;
    }

    protected void initHitbox(float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, (int) (width * Game.SCALE), (int) (height * Game.SCALE));
    }

    public void drawHitbox(Graphics g, int xLvlOffset){
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x - xLvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    /**
     * Obtient le type de l'objet.
     * @return Le type de l'objet.
     */
    public int getObjType() {
        return objType;
    }

    /**
     * Obtient la hitbox de l'objet.
     * @return La hitbox de l'objet.
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * Indique si l'objet est actif.
     * @return True si l'objet est actif, sinon False.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Définit si l'objet est actif ou non.
     * @param active True pour activer l'objet, sinon False.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public int getxDrawOffset() {
        return xDrawOffset;
    }

    /**
     * Définit si l'animation de l'objet doit être effectuée.
     * @param doAnimation True pour effectuer l'animation, sinon False.
     */
    public void setAnimation(boolean doAnimation) {
		this.doAnimation = doAnimation;
	}

    /**
     * Obtient le décalage en y pour le dessin de l'objet.
     * @return Le décalage en y.
     */
    public int getyDrawOffset() {
        return yDrawOffset;
    }

    /**
     * Obtient l'indice de l'animation de l'objet.
     * @return L'indice de l'animation.
     */
    public int getAniIndex(){
        return aniIndex;
    }
}
