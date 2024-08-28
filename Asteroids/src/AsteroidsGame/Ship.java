package AsteroidsGame;

import java.awt.*;
import java.awt.geom.AffineTransform;

//the player

public class Ship extends SpaceCraft{
    boolean immune = true;//when the ship respawns it might spawn on rocks. Immunity is true until it navigates away, otherwise the player could lose a life unfairly
    Ship(double x, double y, double dir) {
        super(x,y,dir);
        radius = (int)(5 * s);
        skin = new Skin(0,s,new Color(100,150,255));
        cooldown = 0;
    }

    void draw(Graphics g){
        skin.draw(g,(int)x,(int)y,thrust,immune);
    }

    //move the ship, reduce the cooldown left of the gun, and see if ship has crashed or no longer needs to be immune
    public void update(){
        super.update();

        if (immune) {//if immune check if still should be immune
            immune = false;
            for (Shape p : Asteroids.objects) {
                if ((p.isDebris() && !p.isPower()) && distanceTo(p.x, p.y) < p.radius + 25 * s) {//if too close to this rock
                    immune = true;//still immune
                    break;
                }
            }
        }else{//otherwise see if colliding with a rock
            for (Shape p : Asteroids.objects) {
                if (p.isPower()&& Colliding(p)){
                    if (p.type == 0)
                        Asteroids.lives++;
                    else if(p.type == 1)
                        scatter += 600;
                    else
                        rapid += 600;
                    Asteroids.objects.remove(p);
                }
                else if (p !=this && (p.isDebris()||p.isCraft()) && Colliding(p)) {//if colliding with this rock
                    p.destroy();//destroy the rock
                    destroy();
                    break;//ship is destroyed, don't try and destroy more rocks
                }
            }
        }
        wrap();
    }
    //increase velocity, turn and shoot (depending on what keys are pressed)
    public void move(boolean keys[]){//0 = up, 1 = left, 2 = right, 3 = fire
        //the user controls.
        if(keys[0]){
            velx += 0.29 * Math.sin(dir);//0.3
            vely -= 0.29 * Math.cos(dir);//0.3
            thrust = true;
            //System.out.println("moved");
        }
        else//not accelerating
        {
            thrust = false;
        }
        if(keys[1]){
            dir -= 0.09;
        }
        if(keys[2]){
            dir += 0.09;
        }
        if(keys[3] && cooldown <= 0){
            if(!immune) {//cannot shoot while immune
                Asteroids.objects.add(new Bullet(x + 10 * s * Math.sin(dir), y - 10 * s * Math.cos(dir), dir));

                if (scatter > 0){
                    Asteroids.objects.add(new Bullet(x + 10 * s * Math.sin(dir), y - 10 * s * Math.cos(dir), dir+0.2));
                    Asteroids.objects.add(new Bullet(x + 10 * s * Math.sin(dir), y - 10 * s * Math.cos(dir), dir-0.2));
                }
                if(rapid > 0)
                    cooldown = 4;
                else
                    cooldown = 15; // cannot shoot for this many ticks
            }
        }
    }
    @Override
    public boolean isShip(){
        return true;
    }

    public boolean isFriend(){
        return true;
    }

    public void destroy(){
        if(!immune) {
            super.destroy();
            Asteroids.lives--;//lost a life
        }
    }
}
