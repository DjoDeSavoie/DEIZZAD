package objects;
import static utilz.Constants.ObjectConstants.*;

import main.Game;

public class GameContainer extends GameObject{
    
    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    private void createHitbox(){

        if(objType == BOX) {
            initHitbox(25, 18);

            xDrawOffset = (int) (7 * Game.SCALE); //décalage de 7 pixel depuis la gauche de la hitbox
            yDrawOffset = (int) (12 * Game.SCALE); //décalage de 12 pixel depuis la haut de la hitbox
        }else {
            initHitbox(23, 25);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (12 * Game.SCALE);
        }

        // Décalage de la hitbox pour que les objets soient en contact direct avec le sol
        hitbox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update() {
        if(doAnimation)
            updateAnimationTick();
    }
}
