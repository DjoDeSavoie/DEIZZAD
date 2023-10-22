package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private float xDir = 0.003f, yDir = 0.003f;
	private BufferedImage image, img;
	private BufferedImage[][] animations;
	private int anim_tick, anim_index, anim_speed = 15;
	private int player_action = ATTACK_1;
	private int player_direction = -1;
	private boolean isMoving = false;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImage();
		loadAnimation();

		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void importImage() {
		InputStream stream = getClass().getResourceAsStream("../data/player_sprites.png");
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				stream.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}

	private void loadAnimation(){
		animations = new BufferedImage[9][8];
		for(int i = 0; i < animations.length; i++){
			for(int j = 0; j < animations[i].length; j++){
				animations[i][j] = image.getSubimage(j*96, i*100, 96, 100);
			}
		}
	}

	public void setDirection(int direction){
		this.player_direction = direction;
		isMoving = true;
	}

	public void setMoving(boolean isMoving){
		this.isMoving = isMoving;
	}

	private void updateAnimation(){
		anim_tick++;
		if(anim_tick >= anim_speed){
			anim_tick = 0;
			anim_index++;
			if(anim_index >= GetSpriteAmount(player_action)){
				anim_index = 0;
			}
		}
	}

	private void setAnimation(){
		if(isMoving)
			player_action = WALKING;
		else 
			player_action = IDLE;
	}

	private void updatePosition(){
		if(isMoving){
			switch(player_direction){
				case LEFT:
					xDelta -= 5;
					break;
				case RIGHT:
					xDelta += 5;
					break;
				case UP:
					yDelta -= 5;
					break;
				case DOWN :
					yDelta += 5;
					break;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// img = image.getSubimage(6*60, 7*100, 100, 100);
		updateAnimation();
		setAnimation();
		updatePosition();
		g.drawImage(animations[player_action][anim_index], (int) xDelta, (int) yDelta, 200, 200, null);
		
		//g.fillRect(xDelta, yDelta, 200, 50);

	}

	public void updateGame(){
		//updateAnimation();
		//setAnimation();
		//updatePos();
	}

}