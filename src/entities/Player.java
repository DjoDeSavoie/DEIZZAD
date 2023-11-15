package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.JUMPING;
import static utilz.Constants.PlayerConstants.WALKING;
import static utilz.Constants.PlayerConstants.ATTACK_3;
import static utilz.HelpMethods.*;



import java.awt.image.BufferedImage;

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
    private float xDrawOffset = 25 * Game.SCALE;
    private float yDrawOffset = 40 * Game.SCALE;

    //Jumping / Gravity variables
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height); // x et y définit dans la classe Entity
        loadAnimation();
        initHitbox(x, y, 25 * Game.SCALE, 40 * Game.SCALE);

    }

    public void update() {

		updatePos();
        updateAnimationTick();
		setAnimation();
    }

    public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
        //drawHitbox(g);
    }

	private void updateAnimationTick(){
		aniTick++;
		if(aniTick >= aniSpeed){
			aniTick = 0;
			aniIndex++;
			if(aniIndex >= GetSpriteAmount(playerAction)){
				aniIndex = 0;
                attacking = false;
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
        

        if(attacking)
            playerAction = ATTACK_3;

        if(startAni != playerAction)
            resetAniTick();
	}

    private void resetAniTick(){
        aniTick = 0;
        aniIndex = 0;
    }


    //permet d'éviter les arrêts du jours lorsque l'on change de direcction au clavier
	//améliore la fluidité des mouvements au clavier
    private void updatePos(){

        moving = false;
        if(jump)
            jump();

        //si aucun input n'est recu, on ne fait rien
        if(!left && !right && !inAir)
            return;
		
        //vitesse horizontale a 0
        float xSpeed = 0;

        if(left)
            xSpeed -= playerSpeed;
        if(right)
            xSpeed += playerSpeed;
        
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
                System.out.println("1");
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

    
}
