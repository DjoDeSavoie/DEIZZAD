/**
 * @file GameOverOverlay.java
 * @brief Contient la définition de la classe GameOverOverlay.
 */

package Gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import main.Game;
import utilz.LoadSave;
import java.awt.Font;
import java.awt.Color;

import java.awt.image.BufferedImage;

/**
 * @brief Classe représentant l'écran de fin de jeu (Game Over).
 */
public class GameOverOverlay {

    private BufferedImage img;
    private int x, y, width, height;
    private Playing playing;

    /**
     * @brief Constructeur de la classe GameOverOverlay.
     * @param playing Instance de la classe Playing pour interagir avec le jeu.
     */
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        initImg();
    }

    /**
	 * Charge l'image de fond.
	 */
    private void initImg() {
        img = LoadSave.GetSpriteAtlas(LoadSave.GAME_OVER);
        width = (int) (img.getWidth() * Game.SCALE);
        height = (int) (img.getHeight() * Game.SCALE);
        x = Game.GAME_WIDTH / 2 - 200;
        y = (int) (40 * Game.SCALE);
    }

    /**
     * @brief Dessine l'écran Game Over.
     * @param g Objet Graphics utilisé pour dessiner.
     */
    public void draw(Graphics g) {
        // Fond semi-transparent en noir
        //g.setColor(new Color(0, 0, 0, 200));
        //g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        // Texte "Game Over" en blanc centré
        //g.setColor(Color.white);
        //g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);

        // Instructions pour retourner au menu
        Font boldFont = new Font(g.getFont().getName(), Font.BOLD, 18);
        float alpha = (float) Math.random();

        // Set the font and color with the random alpha value
        g.setFont(boldFont);
        g.setColor(new Color(255, 0, 0, (int) (255 * alpha))); // Use alpha for opacity
        g.setFont(boldFont);
        g.drawString("Cliquez sur Echap pour retourner au Menu", 400, 320);
        g.drawImage(img, x, y, width, height, null);
    }

    /**
     * @brief Gère les touches pressées lors de l'écran Game Over.
     * @param e Objet KeyEvent représentant l'événement de touche pressée.
     */
    public void keyPressed(KeyEvent e) {
        // Si la touche pressée est la touche Echap
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            // Réinitialiser les éléments de jeu et passer à l'état du menu
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }
}
