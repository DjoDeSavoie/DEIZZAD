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

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImage();
		//loadAnimation();

		addKeyListener(new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void importImage() {
		InputStream stream = getClass().getResourceAsStream("../data/player_sprites.png");
		try {
			image = ImageIO.read(stream);
		} catch (IOException e) {
			System.out.print("bonjour");
			e.printStackTrace();
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		updateImage();
		img = image.getSubimage(6*60, 7*100, 100, 100);
		g.drawImage(img, (int)xDelta, (int)yDelta, null);
	}
	public void updateImage(){
		xDelta += xDir;
		if(xDelta >= 1400 || xDelta <= 0){
			xDir *= -1;
		}

		yDelta += yDir;
		if(yDelta >= 1400 || yDelta <= 0){
			yDir *= -1;
		}
		
	}

}