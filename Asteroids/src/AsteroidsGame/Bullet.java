package AsteroidsGame;

import java.awt.*;
//what the ship uses to shoot
public class Bullet extends Ammo{

    Bullet(double x, double y, double dir){
        super(x,y,dir);
        col = new Color(170,255,150);
    }
    boolean isTarget(Shape p){
        return p.isDebris() || p.isUFO();
    }
    void incScore(Shape p){
        //increase the score
        if(p.isUFO()){
            Asteroids.score += 200;
        }
        else if (p.isRock()){
            if (p.size <= 1) {//smaller rocks give a higher score
                Asteroids.score += 50;
            } else if (p.size <= 2) {//medium rocks
                Asteroids.score += 20;
            } else {//large rocks
                Asteroids.score += 10;
            }
        }
    }
}
