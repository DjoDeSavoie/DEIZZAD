package objects;

import main.Game;

public class Potion extends GameObject {

    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;

    /**
     * Constructeur de la classe Potion.
     * @param x La position x de la potion.
     * @param y La position y de la potion.
     * @param objType Le type de la potion.
     */
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
        updateHover();
    }

    private void updateHover() {
        hoverOffset += (0.75f * Game.SCALE * hoverDir);

        if(hoverOffset >= maxHoverOffset)
            hoverDir = -1;
        else if(hoverOffset < 0)
            hoverDir = 1;

        hitbox.y = y + hoverOffset;
    }
}