/**
 * @file Statemethods.java
 * @brief Définit l'interface Statemethods décrivant les méthodes communes pour les états du jeu.
 */

package Gamestates;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.Graphics;

/**
 * @interface Statemethods
 * @brief Interface décrivant les méthodes communes pour les états du jeu.
 */
public interface Statemethods {

    /**
     * Met à jour l'état du jeu.
     */
    public void update();

    /**
     * Dessine l'état du jeu.
     * @param g L'objet Graphics utilisé pour dessiner.
     */
    public void draw(Graphics g);

    /**
     * Gère l'événement de pression d'une touche de la souris.
     * @param e L'événement de la souris.
     */
    public void mousePressed(MouseEvent e);

    /**
     * Gère l'événement de relâchement d'une touche de la souris.
     * @param e L'événement de la souris.
     */
    public void mouseReleased(MouseEvent e);

    /**
     * Gère l'événement de mouvement de la souris.
     * @param e L'événement de la souris.
     */
    public void mouseMoved(MouseEvent e);

    /**
     * Gère l'événement de clic de la souris.
     * @param e L'événement de la souris.
     */
    public void mouseclicked(MouseEvent e);

    /**
     * Gère l'événement de pression d'une touche du clavier.
     * @param e L'événement du clavier.
     */
    public void keyPressed(KeyEvent e);

    /**
     * Gère l'événement de relâchement d'une touche du clavier.
     * @param e L'événement du clavier.
     */
    public void keyReleased(KeyEvent e);
}
