package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
	private JFrame jframe;

	public GameWindow(GamePanel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setLocationRelativeTo(null);
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e){//lorsque l'on n'est plus sur la fenetre de jeu (ex : update out alt+tab)
				System.out.println("BYYYYE");
				gamePanel.getGame().windowFocusLost();

			}

			@Override
			public void windowGainedFocus(WindowEvent e){
				System.out.println("HIIIII");

			}

		});

	}

}