package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// implements différent de extends, implements permet d'implémenter une interface comme un cercle implemente 
// dessinable et extends est l'héritage comme chien à animal

public class Input implements KeyListener {   

    private boolean[] keys;  // Tableau pour stocker l'état de chaque touche

    public Input() {
        keys = new boolean[256];  // Vous pouvez ajuster la taille du tableau en fonction de vos besoins
    }
    
    // Méthodes pour vérifier l'état des touches
    public boolean isKeyPressed(int keyCode) {
        return keys[keyCode];
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // vide ne pas supprimer
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
        }
    }
}
