package GameObjects;

import UI.GameWindow;
import Utilities.Animation;
import Utilities.Resources;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the mainCharacter class that can move on the land. The dinosaur will
 * have different States where it can be in. (jump, death, running, ducking).
 * 
 * TODO: make a StateMachine so you'll always know the movements and the states 
 * of the dinosaur.
 * 
 * TODO: build a collision detector
 * 
 * TODO: draw, update and reset the dinosaur
 * 
 * @author jordi
 */
public class Dino {
    
    public final static int LAND_POSY = (GameWindow.WINDOW_HEIGHT / 2) - 23;
    
    //these are the states of the dinosaur.
    public final static int DEATH = 0;
    public final static int RUNNING = 1;
    public final static int JUMPING = 2;
    public final static int DUCKING = 3;
    
    public int currentState = RUNNING;
   
    private double posY;
    private double posX;
    private double speedX;
    private double speedY;
    private double GRAVITY = 0.4;
    
    private Rectangle hitBox;

    public int score = 0;

    private BufferedImage deathImage;
    private BufferedImage jumping;
    
    private Animation an_Running;
    private Animation an_Ducking;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;
    
    public Dino(){
        posX = 75;
        posY = LAND_POSY;
        
        hitBox = new Rectangle();
        
        deathImage = Resources.getResourceImage("src/resources/main-character4.png");
        jumping = Resources.getResourceImage("src/resources/main-character3.png");
        
        an_Running = new Animation(90);
        an_Running.addFrame(Resources.getResourceImage("src/resources/main-character1.png"));
        an_Running.addFrame(Resources.getResourceImage("src/resources/main-character2.png"));
        
        an_Ducking = new Animation(90);
        an_Ducking.addFrame(Resources.getResourceImage("src/resources/main-character5.png"));
        an_Ducking.addFrame(Resources.getResourceImage("src/resources/main-character6.png"));

        try {
            jumpSound = Applet.newAudioClip(new URL("file","","src/resources/jump.wav"));
            deadSound = Applet.newAudioClip(new URL("file","","src/resources/dead.wav"));
            scoreUpSound = Applet.newAudioClip(new URL("file","","src/resources/scoreup.wav"));
        } catch (MalformedURLException ex) {
            Logger.getLogger(Dino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reset() {
        posY = LAND_POSY;
    }
    
    public double getSpeedX() {
        return speedX;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }
    
    public void draw(Graphics g){
        switch(currentState){
            case 0: //Death
                g.drawImage(deathImage, (int) posX, (int) posY, null);
                break;
            case 1: //running
                g.drawImage(an_Running.getFrame(), (int) posX, (int) posY, null);
                break;
            case 2: //jumping
                g.drawImage(jumping, (int) posX, (int) posY, null);
                break;
            case 3: //ducking
                g.drawImage(an_Ducking.getFrame(), (int) posX, (int) posY+23, null);
                break;
            default:    
        }
        
        visualizeHitBox(g);
        
    }

    private void visualizeHitBox(Graphics g) {
        hitBox = new Rectangle();
        
        if (currentState == DUCKING) {
            hitBox.x = (int) posX + 5;
            hitBox.y = (int) posY + 20;
            hitBox.width = an_Ducking.getFrame().getWidth() - 10;
            hitBox.height = an_Ducking.getFrame().getHeight();
        } else {
            hitBox.x = (int) posX + 5;
            hitBox.y = (int) posY;
            hitBox.width = an_Running.getFrame().getWidth() - 10;
            hitBox.height = an_Running.getFrame().getHeight();
        }
        
        g.setColor(Color.red);
        g.drawRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
    }
    
    public void update() {
        an_Running.updateFrame();
        an_Ducking.updateFrame();
        
        if (posY >= LAND_POSY) {
            posY = LAND_POSY;
            if (currentState != DUCKING) {
                currentState = RUNNING;
            }
        } else {
            speedY += GRAVITY;
            posY += speedY;
        }
    }
    
    public void jump() {
        if (posY >= LAND_POSY) {
            if (jumpSound != null) {
                jumpSound.play();
            }
            speedY = -9.5;
            posY += speedY - 10 ;
            currentState = JUMPING;
        }
    }

    public void duck(boolean isDown) {
        if (currentState == JUMPING) {
            return;
        }
        if (isDown) {
            currentState = DUCKING;
        } else {
            currentState = RUNNING;
        }
    }
    
    public void dead(boolean isDeath) {
        if (isDeath) {
            currentState = DEATH;
            deadSound.play();
        } else {
            currentState = RUNNING;
        }
    }

    public void upScore() {
        score += 20;
        if (score % 100 == 0) {
            scoreUpSound.play();
        }
    }
}
