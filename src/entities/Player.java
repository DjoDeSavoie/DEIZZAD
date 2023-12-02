package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.JUMPING;
import static utilz.Constants.PlayerConstants.WALKING;
import static utilz.Constants.PlayerConstants.ATTACK_1;
import static utilz.HelpMethods.*;
import Gamestates.Playing;



import java.awt.image.BufferedImage;
import java.awt.geom.Rectangle2D;

import main.Game;

import java.awt.Graphics;

import utilz.LoadSave;

//création de la classe Player fille de la classe Entity
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

    //Jumping / Gravity variables
    private float airSpeed = 0f;
    private float gravity = 0.025f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    
    private BufferedImage healthBar;

    //dimensions de l'image
    private int statusBarWidth = (int) (190 * Game.SCALE);
	private int statusBarHeight = (int) (35 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

    //dimensions de la barre de vie
	private int healthBarWidth = (int) (150 * Game.SCALE);
	private int healthBarHeight = (int) (4 * Game.SCALE);
	private int healthBarX = (int) (34 * Game.SCALE);
	private int healthBarY = (int) (14 * Game.SCALE);

	// private int healthBarWidth = (int) (185 * Game.SCALE);
	// private int healthBarHeight = (int) (40 * Game.SCALE);
	// private int healthBarX = (int) (10 * Game.SCALE);
	// private int healthBarY = (int) (10 * Game.SCALE);
    
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //attack box
    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;
    //enelver !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //private EnemyManager enemyManager;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height); // x et y définit dans la classe Entity
        this.playing = playing;
        loadAnimation();
        initHitbox(x, y, 26 * Game.SCALE, 30 * Game.SCALE);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }

    public void update() {
        updateHealthBar();

        if(currentHealth <= 0){
            playing.setGameOver(true);
            return;
        }
        updateAttackBox();
		updatePos();
        if(attacking)
            checkAttack();
        updateAnimationTick();
		setAnimation();
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
    }

    private void updateAttackBox() {
        if(left)
            attackBox.x = hitbox.x - hitbox.width - (int) (5 * Game.SCALE);
        else if(right)
            attackBox.x = hitbox.x + hitbox.width + (int) (5 * Game.SCALE);
        attackBox.y = hitbox.y + (int) (10 * Game.SCALE);
    }

    //permet de mettre a jour la taille de la barre de vie
    private void updateHealthBar() {
        healthWidth = (int) (healthBarWidth * ( (float) currentHealth / (float) maxHealth));
    }

    public void changeHealth(int value){
        currentHealth += value;
        if(currentHealth <= 0) 
            currentHealth = 0;
            //gameOver();
        else if(currentHealth >= maxHealth)
            currentHealth = maxHealth;
    }

    // private BufferedImage getMirroredImage(BufferedImage image) {
    //     AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
    //     at.translate(-image.getWidth(), 0);
    
    //     BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
    //     Graphics2D g2d = mirroredImage.createGraphics();
    //     g2d.setTransform(at);
    //     g2d.drawImage(image, 0, 0, null);
    //     g2d.dispose();
    
    //     return mirroredImage;
    // }

    public void render(Graphics g, int lvlOffset) {
        BufferedImage imageToDraw = animations[playerAction][aniIndex];
        
        int x = (int) (hitbox.x - xDrawOffset) + flipX;
        int y = (int) (hitbox.y - yDrawOffset);
        int width = this.width * flipW; 
        int height = this.height; 
    
        // if (isLeft()) {
        //     // Si le joueur se déplace vers la gauche
        //     imageToDraw = getMirroredImage(imageToDraw);
        // }
    
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHealthBar(g);
        //drawAttackBox(g,0);
        // drawHitbox(g, 0); 
    }

    private void drawAttackBox(Graphics g, int offset) {
        g.setColor(java.awt.Color.RED);
        g.drawRect((int) attackBox.x - offset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }
    
    private void drawHealthBar(Graphics g) {
        g.drawImage(healthBar, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		g.setColor(java.awt.Color.RED);
		g.fillRect(healthBarX + statusBarX, healthBarY+ statusBarY, healthWidth, healthBarHeight);
        // g.drawImage(healthBar, healthBarX, healthBarY, healthBarWidth, healthBarHeight, null);
        // g.setColor(java.awt.Color.RED);
        // g.fillRect(healthBarX + (int)(35 * Game.SCALE) , healthBarY + (int)(15 * Game.SCALE), healthWidth, 10);
    }

	private void updateAnimationTick(){
		aniTick++;
		if(aniTick >= aniSpeed){
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)){
				aniIndex = 0;
                attacking = false;
                attackChecked = false;
			}
		}
	}

	private void setAnimation(){
        int startAni = playerAction;

		if(moving)
			playerAction = WALKING;
		else 
			playerAction = IDLE;

        if(inAir){
            if(airSpeed < 0)
                playerAction = JUMPING;
            else 
                playerAction = IDLE;
        }
        

        if(attacking){
            playerAction = ATTACK_1;
            //si on attaque et que l'on ne faisait pas d'attaque avant
            if(startAni != ATTACK_1 ){
                aniIndex = 1;
                aniTick = 0; 
            }
        }
        if(startAni != playerAction)
            resetAniTick();
	}

    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }

    //pour checkEnemyHit
    public int getAnimaIndex() {
        return aniIndex;
    }


    //permet d'éviter les arrêts du jours lorsque l'on change de direcction au clavier
	//améliore la fluidité des mouvements au clavier
    private void updatePos(){

        moving = false;
        if(jump)
            jump();

        //si aucun input n'est recu, on ne fait rien
        // if(!left && !right && !inAir)
        //     return;
        if(!inAir)
            if((!left && !right) || (right && left))
                return;
		
        //vitesse horizontale a 0
        float xSpeed = 0;

        if(left){
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if(right){
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }
        
        //met a jour la position du joueur si il n'est pas en l'air
        if(!inAir)
            if(!isEntityOnFloor(hitbox, lvlData))
                inAir = true;
        
        if(inAir){
            if(CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)){
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXpos(xSpeed);
            }
            else{
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if(airSpeed > 0) //si on tombe
                    resetInAir();
                else //si on saute
                    airSpeed = fallSpeedAfterCollision;
                updateXpos(xSpeed);
            }

        } else updateXpos(xSpeed);

        moving = true;
	}

    private void jump() {
        if(inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXpos(float xSpeed) {
        if(CanMoveHere(hitbox.x+xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)){
            hitbox.x += xSpeed;
        } else {
            //si il reste de la place sur le coté
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void loadAnimation() {
        
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[10][8];
        for (int i = 0; i < animations.length; i++)
            for (int j = 0; j < animations[i].length; j++)
                animations[i][j] = img.getSubimage(j * 96, i * 96, 96, 96);

        healthBar = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);

    }

    //permet de réinitialiser les variables booléennes de direction
    //Dans le ccas par exemple où l'on quitte la fenetre de jeu pendant que le perso se déplace
    public void resetDirBooleans() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void loadlvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(!isEntityOnFloor(hitbox, lvlData))
            inAir = true;
    }

    //Setters de variable d'attaque
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    //Getters et Setters des variables booléennes de direction
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

    //reset toutes les variables du joueur
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
