package utilz;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String REDORC_SPRITE = "RedOrc.png";
    public static final String HEALTH_BAR = "health_bar.png";

    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String END_LVL = "end_lvl.png";
    public static final String IMG_BUTTON = "img_buttons.png";
    public static final String GAME_OVER = "game_over.png";


    // recupere une image dans le dossier data
    public static BufferedImage GetSpriteAtlas(String fileName) {

        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/data/" + fileName);
        
        try {
            img = ImageIO.read(is);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    //recupere tous les levels dans le dossier lvls
    public static BufferedImage[] GetAllLevels() {
        String[] levelFiles = {"1.png", "2.png", "3.png"}; // Utilisez ici les noms de fichiers que vous avez montré dans votre JAR.
        List<BufferedImage> images = new ArrayList<>();
    
        for (String fileName : levelFiles) {
            try (InputStream is = LoadSave.class.getResourceAsStream("/data/levels/" + fileName)) {
                if (is == null) {
                    throw new IOException("Cannot find resource: " + fileName);
                }
                BufferedImage img = ImageIO.read(is);
                if (img != null) {
                    images.add(img);
                } else {
                    throw new IOException("Cannot load image for resource: " + fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception here, maybe log it or re-throw as a runtime exception
            }
        }
    
        return images.toArray(new BufferedImage[0]);
    }
    


}
