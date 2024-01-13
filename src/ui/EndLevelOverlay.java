/**
 * @file EndLevelOverlay.java
 * @brief Classe représentant la superposition de fin de niveau dans l'interface utilisateur.
 */

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

/**
 * @class EndLevelOverlay
 * @brief Classe représentant la superposition de fin de niveau dans l'interface utilisateur.
 */
public class EndLevelOverlay {

    private Playing playing;
    private ImgButton menu, next;
    private BufferedImage img;
    private int x, y, width, height;

    /**
     * Constructeur de la classe EndLevelOverlay.
     * @param playing L'état de jeu.
     */
    public EndLevelOverlay(Playing playing) {
        this.playing = playing;
        initImg();
        initButtons();
    }

	/**
	 * Charge l'image de fond.
	 */
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.END_LVL);
        width = (int) (img.getWidth() * Game.SCALE);
        height = (int) (img.getHeight() * Game.SCALE);
        x = Game.GAME_WIDTH / 2 - width / 2;
        y = (int) (73 * Game.SCALE);
    }

	/**
	 * Initialise les boutons.
	 */
    private void initButtons() {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int y = (int) (195 * Game.SCALE);
        next = new ImgButton(nextX, y, IMG_SIZE, IMG_SIZE, 0);
        menu = new ImgButton(menuX, y, IMG_SIZE, IMG_SIZE, 2);
    }

    /**
     * Dessine l'image de fond et les boutons.
     * @param g L'objet Graphics pour dessiner.
     */
    public void draw(Graphics g) {
        // Ajouté après la mise en ligne sur YouTube
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(img, x, y, width, height, null);
        next.draw(g);
        menu.draw(g);
    }

    /**
     * Met à jour l'état des boutons.
     */
    public void update() {
        next.update();
        menu.update();
    }

    /**
     * Vérifie si le pointeur de la souris est sur un bouton.
     * @param b Le bouton à vérifier.
     * @param e L'événement de la souris.
     * @return true si le pointeur de la souris est sur le bouton, sinon false.
     */
    private boolean isIn(ImgButton b, MouseEvent e) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Gère le déplacement de la souris.
     * @param e L'événement de la souris.
     */
    public void mouseMoved(MouseEvent e) {
        next.setMouseOver(false);
        menu.setMouseOver(false);

        if (isIn(menu, e))
            menu.setMouseOver(true);
        else if (isIn(next, e))
            next.setMouseOver(true);
    }

    /**
     * Gère le relâchement du bouton de la souris.
     * @param e L'événement de la souris.
     */
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

    /**
     * Gère l'appui du bouton de la souris.
     * @param e L'événement de la souris.
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(menu, e))
            menu.setMousePressed(true);
        else if (isIn(next, e))
            next.setMousePressed(true);
    }
}
