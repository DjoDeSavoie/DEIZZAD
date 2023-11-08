package utilz;

import main.Game;

public class Constants {

    public static class EnemyConstants {
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

        public static int GetSpriteAmount(int enemyType, int enemyState){
            switch(enemyType){
                case REDORC :
                    switch(enemyState){
                        case IDLE:
                            return 4;
                        case WALKING:
                            return 7;
                        case ATTACK:
                            return 5;
                        case HURT:
                            return 2;
                        case DEAD:
                            return 4;
                    }
            }
            return 0;
        }

    }

    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int RUNNING = 2;
        public static final int ATTACK_1 = 3;
        public static final int ATTACK_2 = 4;
        public static final int ATTACK_3 = 5;
        public static final int ATTACK_4 = 6;
        public static final int JUMPING = 7;
        public static final int HURT = 8;
        public static final int DEAD = 9;

        public static int GetSpriteAmount(int player_action){

            switch(player_action){
                case RUNNING:
                    return 6;
                case IDLE:
                    return 6;
                case JUMPING:
                    return 5;
                case WALKING:
                    return 8;
                case ATTACK_1:
                    return 4;
                case ATTACK_2:
                    return 4;
                case ATTACK_3:
                    return 4;
                case DEAD:
                    return 4;
                default:
                    return 1;
            }
        }
    }
    
}
