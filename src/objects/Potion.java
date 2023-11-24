package objects;

import main.Game;

public class Potion extends GameObject {
    public Potion(int x, int y, int objType) {
        super(x, y, objType);
        doAnimation = true;
        //définition des dimensions de la hitbox
        initHitbox(7, 14);
        xDrawOffset = (int) (3 * Game.SCALE); //décalage de 3 pixel depuis la gauche de la hitbox
        yDrawOffset = (int) (2 * Game.SCALE); //décalage de 2 pixel depuis le haut de la hitbox
    }

    public void update() {
        updateAnimationTick();
    }
}