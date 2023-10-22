package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static utilz.Constants.Directions.*;

import main.GamePanel;

public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_RIGHT:
		gamePanel.setMoving(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			gamePanel.setDirection(UP);
			break;
		case KeyEvent.VK_LEFT:
			gamePanel.setDirection(LEFT);
			break;
		case KeyEvent.VK_DOWN:
			gamePanel.setDirection(DOWN);
			break;
		case KeyEvent.VK_RIGHT:
			gamePanel.setDirection(RIGHT);
			break;
		}

	}

}