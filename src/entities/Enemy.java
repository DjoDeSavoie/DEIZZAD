package entities;

import static utilz.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity {

    private int animIndex, enemyState, enemyType;
    private int animTick, animSpeed = 25;


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

    public void update(){
        updateAnimationTick();
    }

    public int getAnimaIndex(){
        return animIndex;
    }

    public int getEnemyState(){
        return enemyState;
    }
}