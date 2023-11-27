package utilz;

import main.Game;

public class ConstantsEnemy {

        public static final int REDORC = 0;

        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 3;
        public static final int DEAD = 4;

        public static final int REDORC_WIDTH_DEFAULT = 96;
        public static final int REDORC_HEIGHT_DEFAULT = 98;

        public static final int REDORC_WIDTH = (int)(REDORC_WIDTH_DEFAULT * Game.SCALE);
        public static final int REDORC_HEIGHT = (int)(REDORC_HEIGHT_DEFAULT * Game.SCALE);

        public static final int REDORC_DRAWOFFSET_X = (int)(26 * Game.SCALE);
        public static final int REDORC_DRAWOFFSET_Y = (int)(9 * Game.SCALE);
}
