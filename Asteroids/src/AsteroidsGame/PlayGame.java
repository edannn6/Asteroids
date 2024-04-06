package AsteroidsGame;

//by Edan Bradbury

//use WASD or Arrow Keys to control the ship (S, Down arrow or Space key to shoot)
//avoid asteroids (circles)
//destroy all the asteroids to progress to the next level
//every 10 levels has a boss instead of a normal level
//asteroids will not spawn too close to/on the ship when a level starts
//you start with 3 lives and lose one every time you crash. You gain a life every 5000 points
//if you respawn on an asteroid you will be 'immune' from all damage until you are far enough away from all the asteroids
//immunity is indicated by the ship being translucent
//you cannot shoot asteroids while immune
//The game ends when you have lost all of your lives.

import utilities.SpaceFrame;

import javax.swing.*;

public class PlayGame {
    public static boolean isRunning = true;
    public static Asteroids game;

    //what starts the game
    public static void main(String[] args) {
        game = new Asteroids();//make a new game
        JFrame theFrame = new SpaceFrame(game, "Asteroids");//make the frame

        while(isRunning) {//until the game ends
            game.StartGame();
        }

        //close the window
        theFrame.setVisible(false);
        theFrame.dispose();
    }
}
