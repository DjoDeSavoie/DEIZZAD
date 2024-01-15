package utilz;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

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
        InputStream is = LoadSave.class.getResourceAsStream("../data/" + fileName);
        
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
        //partie pour recuperer les fichiers dans le dossier
		URL url = LoadSave.class.getResource("../data/levels");
		File file = null;

		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		File[] files = file.listFiles();

        //partie pour trier les fichiers dans l'ordre
		File[] filesSorted = new File[files.length];

		for (int i = 0; i < filesSorted.length; i++)
			for (int j = 0; j < files.length; j++) {
				if (files[j].getName().equals((i + 1) + ".png"))
					filesSorted[i] = files[j];

			}

        //partie pour charger les images        
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];
        
		for (int i = 0; i < imgs.length; i++)
			try {
				imgs[i] = ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}

		return imgs;
	}

}
