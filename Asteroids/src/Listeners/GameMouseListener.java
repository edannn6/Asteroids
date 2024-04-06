package Listeners;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseListener implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        //if(!AsteroidsGame.Asteroids.inGame){
            AsteroidsGame.PlayGame.isRunning = false;
            AsteroidsGame.Asteroids.isRunning = false;
        //}
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
}
