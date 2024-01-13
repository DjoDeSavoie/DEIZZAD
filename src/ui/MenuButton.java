/**
 * @file MenuButton.java
 * @brief Classe représentant un bouton du menu.
 */

package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Gamestates.Gamestate;
import utilz.LoadSave;

import static utilz.Constants.UI.Buttons.*;

/**
 * @class MenuButton
 * @brief Classe représentant un bouton du menu.
 */
public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;

    /**
     * Constructeur de la classe MenuButton.
     * @param xPos La coordonnée x du bouton.
     * @param yPos La coordonnée y du bouton.
     * @param rowIndex L'indice de la ligne d'images dans la sprite sheet.
     * @param state L'état du jeu associé au bouton.
     */
    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    /**
     * Initialise la zone cliquable du bouton.
     */
    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    /**
     * Charge les images du bouton à partir de la sprite sheet.
     */
    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
    }

    /**
     * Dessine le bouton en utilisant l'image appropriée.
     * @param g L'objet Graphics pour dessiner.
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Met à jour l'indice de l'image en fonction de l'état du bouton (survolé, cliqué, normal).
     */
    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;
    }

    /**
     * Vérifie si la souris est en survol du bouton.
     * @return true si la souris est en survol, sinon false.
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Définit l'état de survol du bouton.
     * @param mouseOver true si la souris est en survol, sinon false.
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Vérifie si le bouton est cliqué.
     * @return true si le bouton est cliqué, sinon false.
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Définit l'état de clic du bouton.
     * @param mousePressed true si le bouton est cliqué, sinon false.
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Retourne la zone cliquable du bouton.
     * @return La zone cliquable du bouton.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Applique l'état du jeu associé au bouton.
     */
    public void applyGamestate() {
        Gamestate.state = state;
    }

    /**
     * Réinitialise les états de survol et de clic du bouton.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}
