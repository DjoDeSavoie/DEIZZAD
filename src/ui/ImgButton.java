package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilz.LoadSave;
import static utilz.Constants.UI.IMGButtons.*;

public class ImgButton {
	protected int x, y, width, height;
	protected Rectangle bounds;
	private BufferedImage[] imgs;
	private int rowIndex, index;
	private boolean mouseOver, mousePressed;

	public ImgButton(int x, int y, int width, int height, int rowIndex) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		createBounds();
		this.rowIndex = rowIndex;
		loadImgs();
	}

	//creer la zone cliquable
	private void createBounds() {
		bounds = new Rectangle(x, y, width, height);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	private void loadImgs() {
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.IMG_BUTTON);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * IMG_DEFAULT_SIZE, rowIndex * IMG_DEFAULT_SIZE, IMG_DEFAULT_SIZE, IMG_DEFAULT_SIZE);

	}

	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;

	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, IMG_SIZE, IMG_SIZE, null);
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

}