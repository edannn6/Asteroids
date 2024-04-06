package Listeners;

import AsteroidsGame.Asteroids;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlListener implements KeyListener {

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        checkKeys(key,true);
        if(key == KeyEvent.VK_R && !AsteroidsGame.Asteroids.inGame){//restart the game if showing highscore page
            //AsteroidsGame.Asteroids.isRunning = false;
            Asteroids.inGame = true;
            Asteroids.score = 0;
        }
        if(key == KeyEvent.VK_Q && !AsteroidsGame.Asteroids.inGame){
        AsteroidsGame.PlayGame.isRunning = false;
        AsteroidsGame.Asteroids.isRunning = false;
        }
    }
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        checkKeys(key,false);//
    }
    public void keyTyped(KeyEvent e){

    }

    void checkKeys(int key, boolean b){//see if the key is in this list, tell the Asteroids class it is either being pressed or has been unpressed
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A){
            AsteroidsGame.Asteroids.leftKey = b;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W){
            AsteroidsGame.Asteroids.upKey = b;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_SPACE){
            AsteroidsGame.Asteroids.fireKey = b;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D){
            AsteroidsGame.Asteroids.rightKey = b;
        }
    }
}
