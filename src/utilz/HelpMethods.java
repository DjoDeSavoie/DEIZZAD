//fcts utilisées partout

package utilz;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Point;

import entities.RedOrc;
import java.awt.Color;
import java.awt.geom.AffineTransform;

import static utilz.Constants.EnemyConstants.REDORC;

import java.awt.Graphics2D;

import main.Game;

public class HelpMethods {

	//test pour verifier si l'entity peut se deplacer a la position indiquée
	public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!IsSolid(x, y, lvlData)) // top left
			if (!IsSolid(x + width, y + height, lvlData)) // bottom right
				if (!IsSolid(x + width, y, lvlData)) // top right
					if (!IsSolid(x, y + height, lvlData)) // bottom left
						return true;
		return false;
	}

	//test pour savoir si la position indiquée est interdite pour un deplacement
	private static boolean IsSolid(float x, float y, int[][] lvlData) {
		// parceque la taille du level est plus grande que la taille de l'ecran
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= Game.GAME_HEIGHT)
			return true;

		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;

		return isTileSolid((int) xIndex, (int) yIndex, lvlData);
	}

	//verifie si il y'a un obstacle a la position donnée
	public static boolean isTileSolid(int x, int y, int[][] lvlData) {
		int value = lvlData[y][x];

		if (value >= 48 || value < 0 || value != 11)
			return true;
		return false;
	}
	
	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed){

		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if(xSpeed > 0) {
			//Right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1; // -1 pour ne pas entrer dans le mur
		}else {
			//Left
			return currentTile * Game.TILES_SIZE;
		}
	}

	// recupere le y de la pos si l'entity est en l'air ou sous un sol
	public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed){
		int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
		if (airSpeed > 0) {
			// chite
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yoffset = (int) (Game.TILES_SIZE - hitbox.height);
			return tileYPos + yoffset - 1;
		} else
			// saute
			return currentTile * Game.TILES_SIZE;
	}

	//verifie si l'entity est sur le sol
	public static boolean isEntityOnFloor (Rectangle2D.Float hitbox, int[][] lvlData) {
		//On check le pixel en dessous de bottom left et bottom right
		if (!IsSolid (hitbox.x, hitbox.y + hitbox.height + 1, lvlData))
			if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height+ 1,lvlData))
			return false;
		return true;
	}

	//verifie si il y'a un floor a la position donnée et si on depasse les limites du niveau
	public static boolean isFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData){
		if(xSpeed > 0)
			return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height+1, lvlData);
		else
		return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height+1, lvlData);
	}

	//verifie si il y'a un obstacle a la position donnée
	public static boolean isSightClear(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile){
		//Chaque int correspond a la position du joueur ou de l'enemy sur la map
		int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
		int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

		if(firstXTile > secondXTile){
			for(int i=0; i<firstXTile-secondXTile; i++){
				if(isTileSolid(secondXTile+i, yTile, lvlData))
					return false;
				//si il y'a un trou
				if(!isTileSolid(secondXTile+i, yTile+1, lvlData))
					return false;
					
			}
			return true;
		}else {
			for(int i=0; i<secondXTile-firstXTile; i++){
				if(isTileSolid(firstXTile+i, yTile, lvlData))
					return false;
			}
			return true;
		}
	}

	// Cette méthode est utilisée pour obtenir une version miroir d'un BufferedImage
    public static BufferedImage getMirroredImage(BufferedImage image) {
        AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
        at.translate(-image.getWidth(), 0);
    
        BufferedImage mirroredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = mirroredImage.createGraphics();
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
    
        return mirroredImage;
    }

    public static ArrayList<RedOrc> GetRedOrc(BufferedImage img){ 
        ArrayList<RedOrc> list = new ArrayList<>();
         for(int j = 0; j < img.getHeight(); j++)
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if(value == REDORC)
                    list.add(new RedOrc(i*Game.TILES_SIZE, j*Game.TILES_SIZE));
            }
        return list; 
    }

    public static int[][] GetLevelData(BufferedImage img ){
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        
        for(int j = 0; j < img.getHeight(); j++)
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
    }

	public static Point GetPlayerSpawn(BufferedImage img) {
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value == 100)
					return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
			}
		return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
	}
}