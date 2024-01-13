/**
 * @file Potion.java
 * @brief Classe représentant l'objet potion dans le jeu.
 */

package objects;

import main.Game;

/**
 * @class Potion
 * @brief Classe représentant l'objet potion dans le jeu.
 */
public class Potion extends GameObject {

    /**
     * Constructeur de la classe Potion.
     * @param x La position x de la potion.
     * @param y La position y de la potion.
     * @param objType Le type de la potion.
     */
    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        // Définition des dimensions de la hitbox.
        initHitbox(7, 14);
        xDrawOffset = (int) (3 * Game.SCALE); // Décalage de 3 pixels depuis la gauche de la hitbox.
        yDrawOffset = (int) (2 * Game.SCALE); // Décalage de 2 pixels depuis le haut de la hitbox.
    }

    /**
     * Met à jour la potion.
     */
    public void update() {
        updateAnimationTick();
    }
}
