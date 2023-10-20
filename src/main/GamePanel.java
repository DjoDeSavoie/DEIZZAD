package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private int xDelta = 100, yDelta = 100;
	private BufferedImage image, img;

	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		importImage();
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
		repaint();
	}

	public void changeYDelta(int value) {
		this.yDelta += value;
		repaint();
	}
	
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		img = image.getSubimage(6*60, 7*100, 100, 100);
		g.drawImage(img, xDelta, yDelta, null);
		
		//g.fillRect(xDelta, yDelta, 200, 50);

	}

}