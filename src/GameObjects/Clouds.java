package GameObjects;

import UI.GameScreen;
import UI.GameWindow;
import Utilities.Resources;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author jordi
 */
public class Clouds {
    
    private List<ImageCloud> listClouds;
    private BufferedImage cloud;
    
    private Dino dino;
    
    public Clouds(Dino dino){
        this.dino = dino;
        cloud = Resources.getResourceImage("src/resources/cloud.png");
        listClouds = new ArrayList<>();
        ImageCloud imageCloud; 
        
        for (int i = 0; i < 5; i++) {
            imageCloud = new ImageCloud();
            imageCloud.posX = i*150;
            imageCloud.posY = i*10 + 20;
            listClouds.add(imageCloud);
        } 
    }
    
    public void update(){
        Iterator<ImageCloud> itr = listClouds.iterator();
        ImageCloud firstElement = itr.next();
        firstElement.posX -= dino.getSpeedX()/8;
        
        while(itr.hasNext()){
            ImageCloud element = itr.next();
            element.posX -= dino.getSpeedX()/8;
        }
        
        if (firstElement.posX < -cloud.getWidth()) {
            listClouds.remove(firstElement);
            firstElement.posX = GameWindow.WINDOW_WIDTH;
            listClouds.add(firstElement);
        }
    }
    
    public void draw(Graphics g){
        for (ImageCloud listCloud : listClouds) {
            g.drawImage(cloud, (int) listCloud.posX, listCloud.posY, null);
        }
    }
    
   // -------------------------------------------- SubClass ----------------------------------------------//    
    
    private class ImageCloud {
        double posX;
        int posY;
    }
}
