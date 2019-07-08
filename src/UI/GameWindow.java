package UI;

import javax.swing.JFrame;

/**
 * This game is a first step to an approach of an easy beatable game for a neural network. It will be the same as the offline
 *  the same as the offline game in google chrome when the Internet is not active.
 * 
 * @author Jordi Jaspers
 * @version V1.0
 * 
 */
public class GameWindow extends JFrame {
    
        public static final int WINDOW_RATIO = 16/9;
        public static final int WINDOW_HEIGHT = 720;
        public static final int WINDOW_WIDTH = WINDOW_HEIGHT * WINDOW_RATIO;
        
        private final GameScreen gameScreen; 

           public static void main(String args[]) {
               GameWindow window = new GameWindow();
               window.startGame();
           }
        
        public GameWindow(){
            super ("Dino-Jump The Offline Chrome Game -- Made By Jordi Jaspers (Beta)");
            
            setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            
            gameScreen = new GameScreen();
            addKeyListener(gameScreen);                  //changing the keylistener to the game and not the window.
            add(gameScreen);                             //adding the game on to the JFRAME
        }
        
       public void startGame() {
        setVisible(true);
        gameScreen.initLevel();
    }
}
