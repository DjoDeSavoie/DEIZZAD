/**
 * @file Menu.java
 * @brief Contient la définition de la classe Menu représentant l'état du menu dans le jeu.
 */

package Gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;

import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

/**
 * @class Menu
 * @brief Représente l'état du menu dans le jeu.
 * Elle étend la classe abstraite State et implémente l'interface Statemethods.
 */
public class Menu extends State implements Statemethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;

    /**
     * Constructeur de la classe Menu.
     * @param game Instance du jeu.
     */
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    /**
     * Charge l'image de fond du menu.
     */
    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int) (backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int) (backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (45 * Game.SCALE);
    }

    /**
     * Initialise les boutons du menu.
     */
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
    }

    /**
     * Met à jour l'état du menu.
     */
    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    /**
     * Dessine l'état du menu.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
        
        // Dessine les instructions de jeu
        drawInstructions(g);

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    /**
     * Ecris les instructions de jeu.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    public void drawInstructions(Graphics g) {
        Font originalFont = g.getFont();
        Font boldFont = new Font(originalFont.getName(), Font.BOLD, 18);
        g.setFont(boldFont);

        String instructionsText = "Instructions de Déplacement :\n \n \n" +
        "Aller à gauche : Touche gauche \n \n" +
        "Aller à droite : Touche droite \n \n" +
        "Frapper : Alt gauche\n\n" +
        "Sauter : Espace\n\n" +
        "Pause : Backspace\n \n";

        // Split the text into lines
        String[] lines = instructionsText.split("\n");

        // Draw each line centered vertically
        int startY = 200;
        for (int i = 0; i < lines.length; i++) {
            // Choose a different color for each line
        switch (i) {
            case 3: // Aller à gauche
                g.setColor(Color.BLUE);
                break;
            case 5: // Aller à droite
                g.setColor(Color.BLUE);
                break;
            case 7: // Frapper
                g.setColor(Color.RED);
                break;
            case 9: // Sauter
                g.setColor(Color.GREEN);
                break;
            case 11: // Pause
                g.setColor(Color.BLACK);
                break;
            default:
                g.setColor(Color.BLACK);
                break;
        }
            // Calculate the width of each line
            int textWidth = g.getFontMetrics().stringWidth(lines[i]);

            // Draw the line centered horizontally and vertically
            g.drawString(lines[i], (400 - textWidth) / 2, startY + i * g.getFontMetrics().getHeight());
        }

        // Restore the original font
        g.setFont(originalFont);

    }

    /**
     * Gère l'événement de clic de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseclicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    /**
     * Gère l'événement de pression de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

    /**
     * Gère l'événement de relâchement de souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                if (mb.isMousePressed())
                    mb.applyGamestate();
                break;
            }
        }

        resetButtons();
    }

    /**
     * Réinitialise l'état des boutons après le relâchement de la souris.
     */
    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

    /**
     * Gère le mouvement de la souris.
     * @param e L'événement de la souris.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
    }

    /**
     * Gère l'événement de touche enfoncée.
     * @param e L'événement de la touche.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;
    }

    /**
     * Gère l'événement de touche relâchée.
     * @param e L'événement de la touche.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }

}
