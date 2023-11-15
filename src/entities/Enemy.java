package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
    private int animIndex, enemyState, enemyType;
    private int animTick, animSpeed = 45;
    private boolean firstUpdate = true;
    private boolean isAir;
    private float fallSpeed;
    private float gravity = 0.04f*Game.SCALE;
    private float walkSpeed = 0.2f*Game.SCALE;
    private int walkDir = LEFT;


    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height); 
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick(){
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if(animIndex > GetSpriteAmount(enemyType, enemyState)){
                animIndex = 0;
            }
        }
    }

    public void update(int[][] lvlData){
        updateMove(lvlData);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData){
        if(firstUpdate){
            if(!isEntityOnFloor(hitbox, lvlData))
                isAir = true;
            firstUpdate = false;
        }
        if(isAir) {
            if(CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += fallSpeed;
                fallSpeed += gravity;
            }else {
                isAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        }else {
            switch (enemyState) {
                case IDLE:
                    enemyState = WALKING;
                    break;
                case WALKING:
                    float xSpeed = 0;
                    if(walkDir == LEFT)
                        xSpeed = -walkSpeed;
                    else if(walkDir == RIGHT)
                        xSpeed = walkSpeed;
                    
                    if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
                        if(isFloor(hitbox, xSpeed, lvlData)){
                            hitbox.x += xSpeed;
                            return;
                        }
                    changeWalkDir();
                    break;
            }
        } 
    }

    private void changeWalkDir(){
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else if(walkDir == RIGHT)
            walkDir = LEFT;
    }

    public int getAnimaIndex(){
        return animIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }
}