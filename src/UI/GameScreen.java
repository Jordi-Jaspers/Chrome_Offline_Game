package UI;

import GameObjects.Clouds;
import GameObjects.Dino;
import GameObjects.Land;
import Utilities.Resources;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * This class will be the game that is going to be added on the JFrame in the
 * Window Class. That is why I am extending it with JPanel. It's the layer on top of the JFrame.
 *
 * @author Jordi Jaspers
 * @version V1.0
 *
 */
public class GameScreen extends JPanel implements Runnable, KeyListener {

    private static final int STATE_GAME_START = 0;
    private static final int STATE_GAME_PLAYING = 1;
    private static final int STATE_GAME_OVER = 2;

    private boolean isKeyPressed;
    private int gameState = STATE_GAME_START;
    
    private final Dino dino;
    private Land land;
    
    private Clouds cloud;
    
    private BufferedImage gameOverButtonImage;
    private BufferedImage resetButtonImage;
    
    private Thread thread;

    public GameScreen() {
        dino = new Dino();
        dino.setSpeedX(4); //TODO: snelheid sneller maken afhankelijk van de punten.

        gameOverButtonImage = Resources.getResourceImage("src/resources/gameover_text.png");
        resetButtonImage = Resources.getResourceImage("src/resources/replay_button.png");
        
        //TODO: alle objecten genereren die daar moeten zijn.
        land = new Land(GameWindow.WINDOW_WIDTH, dino);
        cloud = new Clouds(dino);
        //TODO: eerst alle object classes maken: cactus, wolken, vogel...
    }

    public void initLevel() {
        thread = new Thread(this);
        thread.start();
    }

    public void resetLevel() {
        //TODO: reset het hele spel met charachters.
    }

    public void updateLevel() {
        if (gameState == STATE_GAME_PLAYING) {
            land.update();
            dino.update();
            cloud.update();
            if (false) {
                gameState = STATE_GAME_OVER;
                //collision met enemy
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;

            switch (gameState) {
                case STATE_GAME_START:
                    if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE){
                    gameState = STATE_GAME_PLAYING;
                    }
                    break;

                case STATE_GAME_PLAYING:
                    if (e.getExtendedKeyCode() == KeyEvent.VK_SPACE)
                        dino.jump();
                    else if(e.getExtendedKeyCode() == KeyEvent.VK_DOWN)
                        dino.duck(isKeyPressed);
                    break;

                case STATE_GAME_OVER:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        gameState = STATE_GAME_PLAYING;
                        resetLevel();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
        if (gameState == STATE_GAME_PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                dino.duck(isKeyPressed);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 28));

        switch (gameState) {
            case STATE_GAME_START:
                land.draw(g);
                cloud.draw(g);
                g.drawString("Press Space to start the game ", GameWindow.WINDOW_WIDTH/2 - 190, GameWindow.WINDOW_HEIGHT/4);
                break;
            case STATE_GAME_PLAYING:
                land.draw(g);
                dino.draw(g);
                cloud.draw(g);
                break;
            case STATE_GAME_OVER:
                dino.draw(g);
                land.draw(g);
                g.drawString("Game Over! You scored: " + dino.score, GameWindow.WINDOW_WIDTH/2 - 190, GameWindow.WINDOW_HEIGHT/4);
                g.drawImage(gameOverButtonImage, 200, 30, null);
                g.drawImage(resetButtonImage, 283, 50, null);
                break;
        }
    }
    
    @Override
    public void run() {

        int fps = 100;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = 0;
        long elapsedTime;

        int msSleep;
        int nanoSleep;

        while (true) {
            updateLevel();
            repaint();
            elapsedTime = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int) (elapsedTime / 1000000);
            nanoSleep = (int) (elapsedTime % 1000000);
            if (msSleep <= 0) {
                lastTime = System.nanoTime();
                continue;
            }
            try {
                Thread.sleep(msSleep, nanoSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

            lastTime = System.nanoTime();
        }
    }
}