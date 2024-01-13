/**
 * @file Enemy.java
 * @brief Contient la définition de la classe abstraite Enemy.
 */

package entities;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.awt.Graphics;

import static utilz.Constants.Directions.*;

import main.Game;

/**
 * @brief Classe abstraite représentant un ennemi dans le jeu.
 */
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
    protected Rectangle2D.Float attackBox;
    protected int attackBoxOffsetX;

    /**
     * @brief Fonction abstraite pour dessiner l'ennemi.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param xLvlOffset Décalage horizontal du niveau.
     */
    public abstract void draw(Graphics g, int xLvlOffset);

    /**
     * @brief Constructeur de la classe Enemy.
     * @param x Coordonnée X de l'ennemi.
     * @param y Coordonnée Y de l'ennemi.
     * @param width Largeur de l'ennemi.
     * @param height Hauteur de l'ennemi.
     * @param enemyType Type d'ennemi.
     */
    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height); 
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);
        currenthealth = maxHealth;
    }

    /**
     * @brief Initialise la boîte d'attaque de l'ennemi.
     */
    protected void initAttackBox(){
        attackBox = new Rectangle2D.Float(x,y,(int)(20 * Game.SCALE), (int)(20 * Game.SCALE));
        attackBoxOffsetX = (int)(20*Game.SCALE); 
    }

    /**
     * @brief Met à jour la boîte d'attaque de l'ennemi.
     */
    protected void updateAttackBox(){
        if(walkDir == LEFT)
            attackBox.x = hitbox.x - attackBoxOffsetX;
        else if(walkDir == RIGHT)
            attackBox.x = hitbox.x + attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    /**
     * @brief Dessine la boîte d'attaque de l'ennemi.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param xLvlOffset Décalage horizontal du niveau.
     */
    public void drawAttackBox(Graphics g, int xLvlOffset) {
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xLvlOffset, (int) attackBox.y,(int) attackBox.width,(int) attackBox.height);
    }

    /**
     * @brief Initialise l'entity.
     * @param lvlData Tableau représentant les données du niveau.
     */
    protected void firstUpdateCheck(int[][] lvlData){
        if(!isEntityOnFloor(hitbox, lvlData))
            isAir = true;
        firstUpdate = false;
    }

    /**
     * @brief Met à jour l'ennemi.
     * @param lvlData Tableau représentant les données du niveau.
     * @param player Objet représentant le joueur.
     */
    public void update(int[][] lvlData, Player player){
        updateMove(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    /**
     * @brief Met à jour le mouvement de l'ennemi.
     * @param lvlData Tableau représentant les données du niveau.
     * @param player Objet représentant le joueur.
     */
    protected void updateMove(int[][] lvlData, Player player){
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
                    if(canSeePlayer(lvlData, player)){
                        turnToPlayer(player);
                    if (isPlayerInRangeForAttack(player))
                        newState(ATTACK);   
                    }
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

    /**
     * @brief Met à jour la position de l'ennemi en l'air.
     * @param lvlData Tableau représentant les données du niveau.
     */
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

    /**
     * @brief Gère le mouvement de l'ennemi.
     * @param lvlData Tableau représentant les données du niveau.
     */
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

    /**
     * @brief Met à jour le compteur d'animation de l'ennemi.
     */
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

    /**
     * @brief Change la direction de marche de l'ennemi.
     */
    protected void changeWalkDir(){
        if(walkDir == LEFT)
            walkDir = RIGHT;
        else if(walkDir == RIGHT)
            walkDir = LEFT;
    }

    /**
     * @brief Renvoie l'indice d'animation de l'ennemi.
     * @return Indice d'animation de l'ennemi.
     */
    public int getAnimaIndex(){
        return animIndex;
    }

    /**
     * @brief Renvoie l'état de l'ennemi.
     * @return État de l'ennemi.
     */
    public int getEnemyState(){
        return enemyState;
    }

    /**
     * @brief Vérifie si l'ennemi est actif.
     * @return True si l'ennemi est actif, sinon False.
     */
    public boolean isActive(){
        return active;
    }

    /**
     * @brief Initialise l'état de l'ennemi.
     * @param enemyState Nouvel état de l'ennemi.
     */
    protected void newState(int enemyState){
        this.enemyState = enemyState;
        animTick = 0;
        animIndex = 0;
    }

    /**
     * @brief Inflige des dégâts à l'ennemi.
     * @param amount Quantité de dégâts à infliger.
     */
    public void hurt(int amount){
        currenthealth -= amount;
        if(currenthealth <= 0)
            newState(DEAD);  
        else newState(HURT);
    }

    ///////////////////////Fonctions pour gerer les entity behavior////////////////////////////////////////

    /**
     * @brief Vérifie si l'ennemi peut voir le joueur.
     * @param lvlData Tableau représentant les données du niveau.
     * @param player Objet représentant le joueur.
     * @return True si l'ennemi peut voir le joueur, sinon False.
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player){
        int playerY = (int) (player.getHitbox().y / Game.TILES_SIZE);
        if(playerY == tileY){// si le player est sur la meme ligne que l'entity
            if(isPlayerInRange(player)){// si le player est dans la range de l'entity
                if(isSightClear(lvlData, hitbox, player.hitbox, tileY)) //si il y'a pas d'obstacle entre l'entity et le player
                    return true;
            }
        }
        return false;
    }

    /**
     * @brief Tourne l'ennemi en direction du joueur.
     * @param player Objet représentant le joueur.
     */
    protected void turnToPlayer(Player player){
        if(player.hitbox.x < hitbox.x)
            walkDir = LEFT;
        else
            walkDir = RIGHT;
    }

    /**
     * @brief Vérifie si le joueur est dans la même ligne que l'ennemi.
     * @param player Objet représentant le joueur.
     * @return True si le joueur est dans la même ligne, sinon False.
     */
    protected boolean isPlayerInRange(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    /**
     * @brief Vérifie si le joueur est dans la même ligne pour l'attaque.
     * @param player Objet représentant le joueur.
     * @return True si le joueur est dans la même ligne pour l'attaque, sinon False.
     */
    protected boolean isPlayerInRangeForAttack(Player player){
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    /**
     * @brief Vérifie si l'ennemi a touché le joueur avec son attaque.
     * @param attackBox Boîte d'attaque de l'ennemi.
     * @param player Objet représentant le joueur.
     */
    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player){
        if(attackBox.intersects(player.hitbox))
            player.changeHealth(-getEnemyDamage(enemyType));
            attackChecked = true;
    }

    /**
     * @brief Réinitialise l'ennemi à son état initial.
     */
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currenthealth = maxHealth;
        newState(IDLE);
        active = true;
        fallSpeed = 0;
    }

}
