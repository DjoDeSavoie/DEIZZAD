package entities;

import static utilz.Constants.PlayerConstants.GetSpriteAmount;
import static utilz.Constants.PlayerConstants.IDLE;
import static utilz.Constants.PlayerConstants.WALKING;
import static utilz.Constants.PlayerConstants.ATTACK_3;
import static utilz.HelpMethods.CanMoveHere;



import java.awt.image.BufferedImage;

import main.Game;

import java.awt.Graphics;

import utilz.LoadSave;

//création de la classe Player fille de la classe Entity
public class Player extends Entity {
    private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
	private boolean left, up, right, down;
    private float playerSpeed = 2.0f;
    private int[][] levelData;
    private float xDrawOffset = 21 * Game.SCALE;
    private float yDrawOffset = 21 * Game.SCALE;
    

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height); // x et y définit dans la classe Entity
        loadAnimation();
        initHitbox(x, y, 20 * Game.SCALE, 19 * Game.SCALE);

    }

    public void update() {

		updatePos();
        updateAnimationTick();
		setAnimation();
    }

    public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int)(hitbox.x-xDrawOffset), (int)(hitbox.y-yDrawOffset), width, height, null);
        drawHitbox(g);
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
        if(!left && !up && !right && !down)
            return;
		
        float xSpeed = 0, ySpeed = 0;

        if(left && !right)
            xSpeed = -playerSpeed;
        else if(right && !left)
            xSpeed = playerSpeed;

        if(up && !down) 
            ySpeed = -playerSpeed;
           
        else if(down && !up) 
            ySpeed = playerSpeed;

        if(CanMoveHere(hitbox.x+xSpeed, hitbox.y+ySpeed, hitbox.width, hitbox.height, levelData)){
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
        }


	}




    private void loadAnimation() {
        
            BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[9][8];
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

    public void loadlevelData(int[][] levelData) {
        this.levelData = levelData;
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

    
}
