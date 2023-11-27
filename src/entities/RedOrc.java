 package entities;

import static utilz.Constants.EnemyConstants.*;

import main.Game;

public class RedOrc extends Enemy {

    public RedOrc(float x, float y) {
        super(x, y, REDORC_WIDTH, REDORC_HEIGHT, REDORC);
        initHitbox(x, y, (int)(25*Game.SCALE), (int)(31*Game.SCALE));
    }

}
