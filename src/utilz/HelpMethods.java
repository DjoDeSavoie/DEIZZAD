package utilz;

import java.awt.geom.Rectangle2D;

import main.Game;
import java.awt.geom.Rectangle2D;

public class HelpMethods {

	//test pour verifier si l'entity peut se deplacer a la position indiquée
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData))
			if (!IsSolid(x + width, y + height, lvlData))
				if (!IsSolid(x + width, y, lvlData))
					if (!IsSolid(x, y + height, lvlData))
						return true;
		return false;
	}

	//test pour savoir si la position indiquée est interdite pour un deplacement
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		if (x < 0 || x >= Game.GAME_WIDTH)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		int value = lvlData[(int) yIndex][(int) xIndex];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}

	// recupere le y de la pos si l'entity est en l'air ou sous un sol
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// chite
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yoffset = (int) (Game. TILES_SIZE - hitbox.height);
			return tileYPos + yoffset - 1;
		} else
		// saute
		return currentTile * Game. TILES_SIZE;
	}

	//verifie si l'entity est sur le sol
	public static boolean isEntityOnFloor (Rectangle2D.Float hitbox, int[][] lvlData) {
		//On check le pixel en dessous de bottom left et bottom right
		if (!IsSolid (hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height+ 1,lvlData))
			return false;
		return true;
	}

	//verifie si il y'a un floor a la position donnée
	public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height+1, lvlData);
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){

		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
	}
}