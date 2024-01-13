package main;

import javax.swing.JFrame;

/**
 * @class GameWindow
 * @brief Fenêtre principale du jeu.
 */
public class GameWindow {
	private JFrame jframe;
	/**
     * Constructeur de la classe GameWindow.
     * @param gamePanel Le panneau principal du jeu.
     */
	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);

	}

}