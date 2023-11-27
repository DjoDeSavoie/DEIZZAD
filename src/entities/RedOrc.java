 package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;


import main.Game;

public class RedOrc extends Enemy {

    public RedOrc(float x, float y) {
        super(x, y, REDORC_WIDTH, REDORC_HEIGHT, REDORC);
        initHitbox(x, y, (int)(25*Game.SCALE), (int)(31*Game.SCALE));
    }    
    
    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
    }
    
    private void updateMove(int[][] lvlData, Player player){
            if(firstUpdate){
                firstUpdateCheck(lvlData);
            }
            if(isAir) {
                updateInAir(lvlData);
            }else {
                switch (enemyState) {
                    case IDLE:
                        newState(WALKING);
                        break;
                    case WALKING:
                    
                        if(canSeePlayer(lvlData, player))
                            turnToPlayer(player);
                        if (isPlayerInRangeForAttack(player))
                            newState(ATTACK);   

                        move(lvlData);
                        break;
                }
            } 
        }
}    

    
