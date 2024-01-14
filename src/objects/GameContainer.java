/**
 * @file GameContainer.java
 * @brief Classe représentant un conteneur de jeu dans le jeu.
 */

package objects;
import static utilz.Constants.ObjectConstants.*;

import main.Game;

/**
 * @class GameContainer
 * @brief Classe représentant un conteneur de jeu dans le jeu.
 * 
 * Hérite de GameObject.
 */
public class GameContainer extends GameObject{
    
    /**
     * Constructeur de la classe GameContainer.
     * @param x La position en x du conteneur.
     * @param y La position en y du conteneur.
     * @param objType Le type de l'objet.
     */
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    /**
     * Initialise la hitbox en fonction du type de l'objet.
     */
    private void createHitbox(){

        if(objType == BOX) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Game.SCALE); // Décalage de 7 pixels depuis la gauche de la hitbox
            yDrawOffset = (int) (12 * Game.SCALE); // Décalage de 12 pixels depuis le haut de la hitbox
        } else {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);
        }

        // Décalage de la hitbox pour que les objets soient en contact direct avec le sol
        hitbox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    /**
     * Met à jour le conteneur de jeu.
     */
    public void update() {
        if(doAnimation)
            updateAnimationTick();
    }
}
