 package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;


import main.Game;

public class RedOrc extends Enemy {

    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;

    public RedOrc(float x, float y) {
        super(x, y, REDORC_WIDTH, REDORC_HEIGHT, REDORC);
        initHitbox(x, y, (int)(25*Game.SCALE), (int)(31*Game.SCALE));
        initAttackBox();
    }    

    private void initAttackBox(){
        attackBox = new Rectangle2D.Float(x,y,(int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
        attackBoxOffsetX = (int)(20*Game.SCALE);
    }
    
    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox(){
        if(walkDir == LEFT)
            attackBox.x = hitbox.x - attackBoxOffsetX;
        else if(walkDir == RIGHT)
            attackBox.x = hitbox.x + attackBoxOffsetX;
        attackBox.y = hitbox.y;
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
                    case ATTACK:
                        if(animIndex == 0)
                            attackChecked = false;

                        if(animIndex == 3 && !attackChecked)
                            checkEnemyHit(attackBox, player);
                        break;
                    case HURT:
                        if(animIndex == GetSpriteAmount(enemyType, enemyState)-1)
                            newState(WALKING);
                        break;
                    
                }
            } 
    }

    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
    }
    
    // public int flipX(){
    //     if(walkDir == RIGHT)
    //         return width;
    //     else
    //         return 0;  
    // }

    // public int flipW(){
    //     if (walkDir == RIGHT)
    //         return -1;
    //     else
    //         return 1;
    // }

}    

    
