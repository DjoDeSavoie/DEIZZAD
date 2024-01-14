/**
 * @file LevelManager.java
 * @brief Gère les niveaux du jeu, charge les niveaux et permet de passer au niveau suivant.
 */

package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Gamestates.Gamestate;
import main.Game;
import utilz.LoadSave;

/**
 * @class LevelManager
 * @brief Gère les niveaux du jeu, charge les niveaux et permet de passer au niveau suivant.
 */
public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int currentLevel = 0;

    /**
     * Constructeur de la classe LevelManager.
     * @param game Le jeu associé à ce gestionnaire de niveaux.
     */
    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levels  = new ArrayList<>();
        buildAllLevels();
    }

    /**
     * Charge le niveau suivant.
     */
    public void loadNextLevel() {
        currentLevel++;
        if (currentLevel >= levels.size()) {
            currentLevel = 0;
            System.out.println("Plus de niveaux");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(currentLevel);
        game.getPlaying().getEnemyManager().LoadEnemies(newLevel); 
        game.getPlaying().getPlayer().loadlvlData(newLevel.getLevelData()); 
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset()); 
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    /**
     * Construit tous les niveaux.
     */
    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for (BufferedImage img : allLevels)
            levels.add(new Level(img));
    }

    /**
     * Importe les sprites du niveau.
     */
    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for(int j = 0; j < 4; j++){
            for(int i = 0; i < 12; i++){
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    /**
     * Dessine le niveau actuel.
     * @param g L'objet Graphics utilisé pour dessiner.
     * @param lvlOffset Le décalage horizontal du niveau.
     */
    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levels.get(currentLevel).getLevelData()[0].length; i++) {
                int index = levels.get(currentLevel).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
    }

    /**
     * Met à jour le gestionnaire de niveaux.
     */
    public void update(){

    }

    /**
     * Retourne le niveau actuel.
     * @return Le niveau actuel.
     */
    public Level getCurrentLevel(){
        return levels.get(currentLevel);
    }

    /**
     * Retourne le nombre total de niveaux.
     * @return Le nombre total de niveaux.
     */
    public int getAmountOfLevels() {
        return levels.size();
    }
}
