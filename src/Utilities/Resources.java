package Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * This is an easy subclass to get all the image needed for my objects instead
 * of constantly performing this method.
 *
 * @author Jordi Jaspers
 * @version V1.0
 */
public class Resources {

    public static BufferedImage getResourceImage(String path) {
         BufferedImage img = null;
         
        try {
            img = ImageIO.read(new File(path));      
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE, null, ex);
        }
        return img;
    }
}
