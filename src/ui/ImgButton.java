/**
 * @file ImgButton.java
 * @brief Classe représentant un bouton avec une image.
 */

package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import utilz.LoadSave;

import static utilz.Constants.UI.IMGButtons.*;

/**
 * @class ImgButton
 * @brief Classe représentant un bouton avec une image.
 */
public class ImgButton {
    protected int x, y, width, height;
    protected Rectangle bounds;
    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    /**
     * Constructeur de la classe ImgButton.
     * @param x La coordonnée x du bouton.
     * @param y La coordonnée y du bouton.
     * @param width La largeur du bouton.
     * @param height La hauteur du bouton.
     * @param rowIndex L'indice de la ligne d'images dans la sprite sheet.
     */
    public ImgButton(int x, int y, int width, int height, int rowIndex) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds();
        this.rowIndex = rowIndex;
        loadImgs();
    }

    /**
     * Crée la zone cliquable du bouton.
     */
    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Retourne la zone cliquable du bouton.
     * @return La zone cliquable du bouton.
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Charge les images du bouton à partir de la sprite sheet.
     */
    private void loadImgs() {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.IMG_BUTTON);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; i++)
            imgs[i] = temp.getSubimage(i * IMG_DEFAULT_SIZE, rowIndex * IMG_DEFAULT_SIZE, IMG_DEFAULT_SIZE, IMG_DEFAULT_SIZE);

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
     * Dessine le bouton en utilisant l'image appropriée.
     * @param g L'objet Graphics pour dessiner.
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], x, y, IMG_SIZE, IMG_SIZE, null);
    }

    /**
     * Réinitialise les états de survol et de clic du bouton.
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
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
}
