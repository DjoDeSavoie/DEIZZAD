/**
 * @file Level.java
 * @brief Représente un niveau du jeu avec ses caractéristiques telles que les données de niveau, les ennemis, etc.
 */

package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//import les constantes d'objets
import static utilz.Constants.ObjectConstants.*;


import entities.Enemy;
import main.Game;
import objects.Potion;
import utilz.HelpMethods;
import objects.GameContainer;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetRedOrc;
import static utilz.HelpMethods.GetPlayerSpawn;

/**
 * @class Level
 * @brief Représente un niveau du jeu avec ses caractéristiques telles que les données de niveau, les ennemis, etc.
 */
public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<? extends Enemy> enemies;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    /**
     * Constructeur de la classe Level.
     * @param img L'image du niveau.
     */
    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        calcLvlOffsets();
        calcPlayerSpawn();
    }


    /**
     * Crée les potions du niveau.
     */
    private void createPotions() {
        potions = HelpMethods.GetPotions(img);

    }

    /**
     * Crée les conteneurs du niveau.
     */
    private void createContainers() {
        containers = HelpMethods.GetContainers(img);

    }


	/**
	 * Calcule la position de spawn du joueur.
	 */
    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    /**
     * Calcule les décalages du niveau.
     */
    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    /**
     * Crée les ennemis du niveau.
     */
    private void createEnemies() {
        enemies = GetRedOrc(img);
    }

    /**
     * Crée les données de niveau.
     */
    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    /**
     * Retourne l'indice du sprite à la position spécifiée.
     * @param x La coordonnée X de la position.
     * @param y La coordonnée Y de la position.
     * @return L'indice du sprite à la position spécifiée.
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    /**
     * Retourne les données de niveau.
     * @return Les données de niveau.
     */
    public int[][] getLevelData() {
        return lvlData;
    }

    /**
     * Retourne le décalage maximal du niveau en X.
     * @return Le décalage maximal du niveau en X.
     */
    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    /**
     * Retourne la liste des ennemis du niveau.
     * @return La liste des ennemis du niveau.
     */
    public ArrayList<? extends Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Retourne la position de spawn du joueur.
     * @return La position de spawn du joueur.
     */
    public Point getPlayerSpawn() {
        return playerSpawn;
    }


    /**
     * Retourne la liste des potions du niveau.
     * @return La liste des potions du niveau.
     */
    public ArrayList<Potion> getPotions() {
        return potions;
    }

    /**
     * Retourne la liste des conteneurs du niveau.
     * @return La liste des conteneurs du niveau.
     */
    public ArrayList<GameContainer> getContainers() {
        return containers;
    }
}
