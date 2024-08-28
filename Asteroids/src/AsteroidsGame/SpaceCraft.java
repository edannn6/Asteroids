package AsteroidsGame;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class SpaceCraft extends Shape {
    final static double s = 3;//just for making the ship a different size if you want.
    int cooldown; // how often you can shoot
    boolean thrust = false;
    int scatter;
    int rapid;
    Skin skin;
    SpaceCraft(double x, double y, double dir){
        super(x,y);
        this.dir = dir;
        scatter = 0;
        rapid = 0;
    }
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform prev = g2D.getTransform(); //previous transformation
        g2D.rotate(dir,this.x,this.y);//rotate to correct direction
        draw(g);
        g2D.setTransform(prev);//revert the rotation
    }
    abstract void draw(Graphics g);
    public void update(){
        velx *= 0.98;//the ship has friction unlike other objects
        vely *= 0.98;//0.97
        this.x+= velx;//chance the coords by the velocity
        this.y+= vely;
        //dir = ((dir)+Math.PI)%(Math.PI*2)-Math.PI;
        cooldowns();
    }
    void fire(){
        if(cooldown <= 0) {
            makeBullet(0);
            if (scatter > 0) {
                makeBullet(0.2);
                makeBullet(-0.2);
            }
            if (rapid > 0)
                cooldown = 4;
            else
                cooldown = 15; // cannot shoot for this many ticks
        }
    }

    void makeBullet(double ang){

    }
    void cooldowns(){
        if(cooldown > 0)
            cooldown--;//the cooldown for the gun
        if(scatter > 0)
            scatter--;
        if(rapid > 0)
            rapid--;
    }
    public void destroy() {
        explode();
        Asteroids.objects.remove(this);//remove the ship
    }

    public boolean isCraft() {
        return true;
    }
}
