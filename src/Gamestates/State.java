/**
 * @file State.java
 * @brief Contient la définition de la classe State servant de base pour les différents états du jeu.
 */

package Gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

/**
 * @class State
 * @brief Classe de base pour les différents états du jeu.
 */
public class State {

    /** Instance du jeu. */
    protected Game game;

    /**
     * Constructeur de la classe State.
     * @param game Instance du jeu.
     */
    public State(Game game) {
        this.game = game;
    }

    /**
     * Vérifie si le pointeur de la souris est à l'intérieur d'un bouton du menu.
     * @param e L'événement de la souris.
     * @param mb Le bouton du menu.
     * @return true si le pointeur est à l'intérieur du bouton, sinon false.
     */
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Obtient l'instance du jeu.
     * @return L'instance du jeu.
     */
    public Game getGame() {
        return game;
    }
}
