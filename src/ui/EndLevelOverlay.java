package ui;
import Gamestates.Gamestate;
import Gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;   
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.IMGButtons.*;

public class EndLevelOverlay {
    
    private Playing playing;
    private ImgButton menu, next;
    private BufferedImage img;
    private int x, y, width, height;

    public EndLevelOverlay(Playing playing ) {
        this.playing = playing;
        initImg();
        initButtons();
    }
    private void initImg(){
        img = LoadSave.GetSpriteAtlas(LoadSave.END_LVL);
        width = (int) (img.getWidth() * Game.SCALE) ;
        height = (int) (img.getHeight() * Game.SCALE);
        x = Game.GAME_WIDTH/2 - width/2;
        y = (int) (73 * Game.SCALE);
    }

    private void initButtons() {
		int menuX = (int) (330 * Game.SCALE);
		int nextX = (int) (445 * Game.SCALE);
		int y = (int) (195 * Game.SCALE);
		next = new ImgButton(nextX, y, IMG_SIZE, IMG_SIZE, 0);
		menu = new ImgButton(menuX, y, IMG_SIZE, IMG_SIZE, 2);
	}

    // dessine l'image de fond et les boutons
    public void draw(Graphics g) {
		// Added after youtube upload
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

		g.drawImage(img, x, y, width, height, null);
		next.draw(g);
		menu.draw(g);
	}

	public void update() {
		next.update();
		menu.update();
	}

	private boolean isIn(ImgButton b, MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);

		if (isIn(menu, e))
			menu.setMouseOver(true);
		else if (isIn(next, e))
			next.setMouseOver(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(menu, e)) {
			if (menu.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.MENU;
			}
		} else if (isIn(next, e))
			if (next.isMousePressed())
				playing.loadNextLevel();

		menu.resetBools();
		next.resetBools();
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(menu, e))
			menu.setMousePressed(true);
		else if (isIn(next, e))
			next.setMousePressed(true);
	}

}

