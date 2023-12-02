package utilz;

import main.Game;



public class Constants {

    public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 45;

    //classe de constantes pour les objets
	public static class ObjectConstants {

        //identifiants des objets
		public static final int RED_POTION = 0;
		public static final int BLUE_POTION = 1;
		public static final int BARREL = 2;
		public static final int BOX = 3;

        //identifiants des type de potions
		public static final int RED_POTION_VALUE = 15;
		public static final int BLUE_POTION_VALUE = 10;

        //définition des dimensions des barils et des caisses
		public static final int CONTAINER_WIDTH_DEFAULT = 40;
		public static final int CONTAINER_HEIGHT_DEFAULT = 30;
		public static final int CONTAINER_WIDTH = (int) (Game.SCALE * CONTAINER_WIDTH_DEFAULT);
		public static final int CONTAINER_HEIGHT = (int) (Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        //définition des dimensions des potions
		public static final int POTION_WIDTH_DEFAULT = 12;
		public static final int POTION_HEIGHT_DEFAULT = 16;
		public static final int POTION_WIDTH = (int) (Game.SCALE * POTION_WIDTH_DEFAULT);
		public static final int POTION_HEIGHT = (int) (Game.SCALE * POTION_HEIGHT_DEFAULT);

        //accesseur de la valeur du sprite propre a l'objet
		public static int GetSpriteAmount(int object_type) {
			switch (object_type) {
			case RED_POTION:
				return 7;
            case  BLUE_POTION:
                return 7;
			case BARREL:
				return 8;
            case BOX:
                return 8;
			}
			return 1;
		}
	}

    public static class EnemyConstants {
        public static final int REDORC = 0;
        //enum !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        

        //enum pour les states
        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 3;
        public static final int DEAD = 4;
        
        // enum EnemyState{
        //     IDLE,
        //     WALKING,
        //     ATTACK,
        //     HURT,
        //     DEAD
        // }

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
                            return 5;
                        case ATTACK:
                            return 4;
                        case HURT:
                            return 2;
                        case DEAD:
                            return 4;
                    }
            }
            return 0;
        }

        public static int getMaxHealth(int enemyType){
            switch (enemyType) {
                case REDORC:
                    return 10;
            
                default:
                    return 0;
            }
        }

        public static int getEnemyDamage(int enemyType){
            switch (enemyType) {
                case REDORC:
                    return 15;
            
                default:
                    return 0;
            }
        }

    }
    //enum a la place + mettre constante dans fichier meta + mettre a jour enemy et enemymanager enlever fcts redondantes + 
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
                case HURT:
                    return 2;
                case DEAD:
                    return 4;
                default:
                    return 1;
            }
        }
    }
    
}
