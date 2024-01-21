/**
 * @file Player.java
 * @brief Contient la définition de la classe Player.
 */

package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import Gamestates.Playing;

import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;
import java.awt.Point;

import main.Game;

import java.awt.Graphics;

import utilz.LoadSave;

/**
 * @brief Classe représentant le joueur dans le jeu.
 */
public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 25;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.0f;
    private int[][] lvlData;
    private float xDrawOffset = 24 * Game.SCALE;
    private float yDrawOffset = 40 * Game.SCALE;

    // Jumping / Gravity variables
    private float airSpeed = 0f;
    private float gravity = 0.025f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    private BufferedImage healthBar;

    // Dimensions de l'image
    private int statusBarWidth = (int) (190 * Game.SCALE);
    private int statusBarHeight = (int) (35 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    // Dimensions de la barre de vie
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarX = (int) (34 * Game.SCALE);
    private int healthBarY = (int) (14 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    // Attack box
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height); // x et y définit dans la classe Entity
        this.playing = playing;
        loadAnimation();
        initHitbox(x, y, 26 * Game.SCALE, 30 * Game.SCALE);
        initAttackBox();
    }

    /**
     * @brief Définit la position de spawn du joueur.
     * @param s Point représentant la position de spawn.
     */
    public void setSpawnPos(Point s) {
        this.x = s.x;
        this.y = s.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    /**
     * @brief Initialise l'attack box
     */
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    /**
     * @brief Met à jour l'état du joueur.
     */
    public void update() {
        updateHealthBar();

        if(currentHealth <= 0 || hitbox.y >= 600){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
        updatePos();
        if (attacking)
            checkAttack();
        updateAnimationTick();
		setAnimation();
    }

    private void checkPotionTouched() {
        //playing.checkPotionTouched(hitbox);
    }

    /**
     * @brief verifie si le joueur a touché un ennemi.
     */
    private void checkAttack() {
        if (attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        //playing.checkObjectHit(attackBox);
    }

    /**
     * @brief Met à jour la position de l'attack box.
     */
    private void updateAttackBox() {
        if  (left)
            attackBox.x = hitbox.x - hitbox.width - (int) (-10 * Game.SCALE);
        else if  (right)
            attackBox.x = hitbox.x + hitbox.width + (int) (- 10 * Game.SCALE);
        attackBox.y = hitbox.y + (int) (10 * Game.SCALE);
    }

    /**
     * @brief Met à jour la taille de la barre de vie.
     */
    private void updateHealthBar() {
        healthWidth = (int) (healthBarWidth * ((float) currentHealth / (float) maxHealth));
    }

    /**
     * @brief Modifie la santé du joueur.
     * @param value Valeur à ajouter ou soustraire à la santé actuelle.
     */
    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    /**
     * @brief Modifie la puissance du joueur.
     * @param value Valeur à ajouter ou soustraire à la puissance actuelle.
     */

    public void changePower(int value) {
        System.out.println("Puissance Ajoutée !");
    }

    /**
     * @brief Render l'image du joueur avec les éléments associés (animations, barre de vie, etc.).
     * @param g Objet Graphics utilisé pour dessiner.
     * @param lvlOffset Décalage horizontal du niveau.
     */
    public void render(Graphics g, int lvlOffset) {
        BufferedImage imageToDraw = animations[playerAction][aniIndex];

        int x = (int) (hitbox.x - xDrawOffset) + flipX;
        int y = (int) (hitbox.y - yDrawOffset);
        int width = this.width * flipW;
        int height = this.height;

        g.drawImage(imageToDraw, x - lvlOffset, y, width, height, null);
        drawHealthBar(g);
        //drawAttackBox(g,0);
        //drawHitbox(g, 0); 
    }

    /**
     * @brief Dessine la healthbar du joueur.
     * @param g Objet Graphics utilisé pour dessiner.
     */
    private void drawHealthBar(Graphics g) {
        g.drawImage(healthBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(java.awt.Color.RED);
        g.fillRect(healthBarX + statusBarX, healthBarY + statusBarY, healthWidth, healthBarHeight);
        // g.drawImage(healthBar, healthBarX, healthBarY, healthBarWidth, healthBarHeight, null);
        // g.setColor(java.awt.Color.RED);
        // g.fillRect(healthBarX + (int)(35 * Game.SCALE) , healthBarY + (int)(15 * Game.SCALE), healthWidth, 10);
    }

    /**
     * @brief Dessine la hitbox du joueur.
     * @param g Objet Graphics utilisé pour dessiner.
     * @param lvlOffset Décalage horizontal du niveau.
     */
    private void drawAttackBox(Graphics g, int offset) {
        g.setColor(java.awt.Color.RED);
        g.drawRect((int) attackBox.x - offset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    /**
     * @brief Met à jour l'animation du joueur.
     */
    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    /**
     * @brief Définit l'animation du joueur.
     */
    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = WALKING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMPING;
            else
                playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACK_1;
            if (startAni != ATTACK_1) {
                aniIndex = 1;
                aniTick = 0;
            }
        }
        if (startAni != playerAction)
            resetAniTick();
    }

    /**
     * @brief Réinitialise l'animation du joueur.
     */
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    /**
     * @brief Renvoie l'index de l'animation en cours.
     * @return Index de l'animation en cours.
     */
    public int getAnimaIndex() {
        return aniIndex;
    }

    /**
     * @brief Met à jour la position du joueur.
     */
    private void updatePos() {
        moving = false;
        if (jump)
            jump();

        //si aucun input n'est recu, on ne fait rien
        // if(!left && !right && !inAir)
        //     return;
        if (!inAir && ((!left && !right) || (right && left)))
            return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!isEntityOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)//si on tombe
                    resetInAir();
                else //si on saute
                    airSpeed = fallSpeedAfterCollision;
                updateXpos(xSpeed);
            }

        } else
            updateXpos(xSpeed);

        moving = true;
    }

    /**
     * @brief Fait sauter le joueur.
     */
    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    /**
     * @brief Réinitialise les variables de saut.
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    /**
     * @brief Met à jour la position horizontale du joueur.
     * @param xSpeed Vitesse horizontale du joueur.
     */
    private void updateXpos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    /**
     * @brief Charge les animations du joueur.
     */
    private void loadAnimation() {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[10][8];
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(j * 96, i * 96, 96, 96);

        healthBar = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
    }

    /**
     * @brief Réinitialise les variables booléennes de direction.
     * //permet de réinitialiser les variables booléennes de direction
     * //Dans le ccas par exemple où l'on quitte la fenetre de jeu pendant que le perso se déplace
     */
    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    /**
     * @brief Charge les données du niveau.
     * @param lvlData Tableau 2D représentant les données du niveau.
     */
    public void loadlvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    /**
     * @brief Définit l'état d'attaque du joueur.
     * @param attacking Valeur booléenne indiquant si le joueur est en train d'attaquer.
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    // Getters et Setters des variables booléennes de direction

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    /**
     * @brief Réinitialise toutes les variables du joueur.
     */
    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

}
