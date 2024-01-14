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

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<? extends Enemy> enemies;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnemies();
		calcLvlOffsets();
		calcPlayerSpawn();
	}

	private void calcPlayerSpawn() {
		playerSpawn = GetPlayerSpawn(img);
	}

    //recupere les dimensions du level; utile pour les deplacement dans le level
	private void calcLvlOffsets() {
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	// dans le cas ou on a plusieurs types d'ennemis, on concatene les tableaux
	private void createEnemies() {
		enemies = GetRedOrc(img);
	}

	private void createLevelData() {
		lvlData = GetLevelData(img);
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}

	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	// retourne les ennemis (dans le cas ou on a plusieurs types d'ennemis, on concatene les tableaux)
	public ArrayList<? extends Enemy> getEnemies() {
        return enemies;
    }

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

}
