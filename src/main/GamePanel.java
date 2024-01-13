/**
 * @file GamePanel.java
 * @brief Panneau principal du jeu qui gère les entrées utilisateur et le rendu graphique.
 */

package main;

import java.awt.Graphics;
import java.awt.Dimension;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

/**
 * @class GamePanel
 * @brief Panneau principal du jeu qui gère les entrées utilisateur et le rendu graphique.
 */
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private Game game;

    /**
     * Constructeur de la classe GamePanel.
     * @param game L'objet Game associé au panneau.
     */
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

	/**
	 * Définit la taille du panneau.
	 */
    private void setPanelSize(){
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("size : " + GAME_WIDTH + " : " + GAME_HEIGHT);
    }

    /**
     * Méthode appelée automatiquement lors du rendu du composant.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * Retourne l'objet Game associé au panneau.
     * @return L'objet Game associé au panneau.
     */
    public Game getGame(){
        return game;
    }

}
