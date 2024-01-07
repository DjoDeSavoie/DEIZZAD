package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.RedOrc;
import main.Game;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetRedOrc;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {

	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<RedOrc> redorcs;
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

	private void createEnemies() {
		redorcs = GetRedOrc(img);
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

	public ArrayList<RedOrc> getRedOrcs() {
		return redorcs;
	}

	public Point getPlayerSpawn() {
		return playerSpawn;
	}

}