package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.Directions.*;

import main.Game;

public abstract class Enemy extends Entity {
    protected int animIndex, enemyState, enemyType;
    protected int animTick, animSpeed = 45;
    protected boolean firstUpdate = true;
    protected boolean isAir;
    protected float fallSpeed;
    protected float gravity = 0.04f*Game.SCALE;
    protected float walkSpeed = 0.2f*Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currenthealth;
    protected boolean active = true;
    protected boolean attackChecked;


    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height); 
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currenthealth = maxHealth;
    }

    // fonction qui initialise l'entity
    protected void firstUpdateCheck(int[][] lvlData){
        if(!isEntityOnFloor(hitbox, lvlData))
            isAir = true;
        firstUpdate = false;
    }

    // fonction qui permet de savoir si l'entity est en l'air ou non
    protected void updateInAir(int[][] lvlData){
        if(CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.y += fallSpeed;
            fallSpeed += gravity;
        }else {
            isAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    // fonction qui gere les deplacements
    protected void move(int[][] lvlData){
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
    }

    // fonction qui gere les animations
    protected void updateAnimationTick(){
        animTick++;
        if(animTick >= animSpeed){
            animTick = 0;
            animIndex++;
            if(animIndex > GetSpriteAmount(enemyType, enemyState)){
                animIndex = 0;

                // si l'entity est en train d'attaquer, on le remet en idle
                switch (enemyState) {
                    case ATTACK, HURT -> newState(IDLE);
                    case DEAD -> active = false;
                }
            }
        }
    }

    protected void changeWalkDir(){
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

    public boolean isActive(){
        return active;
    }

    // pour commencer l'anima a 0 et pas au milieu
    protected void newState(int enemyState){
        this.enemyState = enemyState;
        animTick = 0;
        animIndex = 0;
    }

    public void hurt(int amount){
        currenthealth -= amount;
        if(currenthealth <= 0)
            newState(DEAD);  
        else newState(HURT);
    }
    ///////////////////////Fonctions pour gerer les entity behavior////////////////////////////////////////

    // fonction qui gere le comportement de l'entity quand elle voit le player
    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if(playerY == tileY){// si le player est sur la meme ligne que l'entity
            //System.out.println(tileY);
            if(isPlayerInRange(player)){// si le player est dans la range de l'entity
                if(isSightClear(lvlData, hitbox, player.hitbox, tileY)) //si il y'a pas d'obstacle entre l'entity et le player
                    return true;
            }
        }
        return false;
    }

    // fonction qui gere le comportement de l'entity quand elle voit le player
    protected void turnToPlayer(Player player){
        if(player.hitbox.x < hitbox.x)
            walkDir = LEFT;
        else
            walkDir = RIGHT;
    }

    // fonction qui gere le comportement de l'entity si le player est dans la meme ligne 
    protected boolean isPlayerInRange(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    // fonction qui gere le comportement de l'entity si le player est dans la meme ligne pour l'attaque
    protected boolean isPlayerInRangeForAttack(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player){
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-getEnemyDamage(enemyType));
            attackChecked = true;
    }

}