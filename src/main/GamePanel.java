package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private float xDir = 0.003f, yDir = 0.003f;
	private BufferedImage image, img;
	private BufferedImage[][] animations;
	private int anim_tick, anim_index, anim_speed = 30;

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
		animations = new BufferedImage[10][8];
		for(int i = 0; i < animations.length; i++){
			for(int j = 0; j < animations[i].length; j++){
				animations[i][j] = image.getSubimage(j*20, i*50, 100, 100);
			}
		}
	}

	public void changeXDelta(int value) {
		this.xDelta += value;
	}

	public void changeYDelta(int value) {
		this.yDelta += value;
	}
	
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}

	private void updateAnimation(){
		// anim_tick++;
		// if(anim_tick >= anim_speed){
		// 	anim_tick = 0;
		// 	anim_index++;
		// 	if(anim_index >= Idle_anim.length){
		// 		anim_index = 0;
		// 	}
		// }
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// img = image.getSubimage(6*60, 7*100, 100, 100);
		updateAnimation();
		g.drawImage(animations[0][0], (int) xDelta, (int) yDelta, null);
		
		//g.fillRect(xDelta, yDelta, 200, 50);

	}

	public void updateGame(){
		//updateAnimation();
		//setAnimation();
		//updatePos();
	}

}