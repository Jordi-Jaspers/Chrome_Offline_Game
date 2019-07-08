package GameObjects;

import UI.GameWindow;
import Utilities.Resources;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * This is an object class called Land. Hereby I can generate and visualise the land where the dino can stand on.
 *
 * @author Jordi Jaspers
 * @version V1.0
 *
 */
public class Land {

    public static final int LAND_POSY = GameWindow.WINDOW_HEIGHT / 2;

    private final ArrayList<CurrentLand> listLand;
    
    private final BufferedImage land1;
    private final BufferedImage land2;
    private final BufferedImage land3;
    
    private final Dino dino;

    public Land(int width, Dino dino) {
        this.dino = dino;
        
        land1 = Resources.getResourceImage("src/resources/land1.png");
        land2 = Resources.getResourceImage("src/resources/land2.png");
        land3 = Resources.getResourceImage("src/resources/land3.png");

        int numberOfImageLand = width / land1.getWidth() + 2;

        listLand = new ArrayList<>();

        for (int i = 0; i < numberOfImageLand; i++) {
            CurrentLand imageLand = new CurrentLand();
            imageLand.posX = i * land1.getWidth();
            setImageLand(imageLand);
            listLand.add(imageLand);
        }
    }

    /**
     * Update function to update the land object.
     */
    public void update() {
        Iterator<CurrentLand> iteratorLand = listLand.iterator();
        
        CurrentLand firstElement = iteratorLand.next();
        firstElement.posX -= dino.getSpeedX();              //de dino staat stil en het land beweegt
        double previousXPos = firstElement.posX;            //hierbij gaat de speed == dinolocatie.
        
        while(iteratorLand.hasNext()){                      //volledig scherm vullen met het land.
            CurrentLand element = iteratorLand.next();
            element.posX = previousXPos + land1.getWidth();
            previousXPos = element.posX;
        }
        
	if(firstElement.posX < -land1.getWidth()){         //alles achter de dino verwijderen en nieuwe achteraan toevoegen.
            listLand.remove(firstElement);
            firstElement.posX = previousXPos + land1.getWidth();
            setImageLand(firstElement);
            listLand.add(firstElement);
}	
    }

    /**
     * After the returned value of the number generator, it will generate a
     * type of land.
     * 
     * @param imgLand  
     */
    private void setImageLand(CurrentLand imgLand) {
        Random rand = new Random();
        int type = rand.nextInt(10);
        
        switch (type) {
            case 1:
                imgLand.image = land1;
                break;
            case 9:
                imgLand.image = land3;
                break;
            default:
                imgLand.image = land2;
                break;
        }
    }
    
    /**
     * A method that will draw the image with the chosen variables.
     * 
     * @param g graphics of the land
     */
    public void draw(Graphics g) {
        
        for (CurrentLand imgLand : listLand) {
            g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
        }
        
    }

    
// -------------------------------------------- SubClass ----------------------------------------------//    
    
    /**
     * This class will hold the random generated values for the drawable land.
     */
    private class CurrentLand {
       public double posX;
       public BufferedImage image;
    }
    
}
