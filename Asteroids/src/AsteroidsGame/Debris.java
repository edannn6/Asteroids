package AsteroidsGame;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Debris extends Shape{
    double dir;
    double spin;
    Debris(double x,double y){
        super(x,y);
        dir = 0;
        spin = Math.random()/10.0 -0.05;

        double d = Math.random() * Math.PI * 2;//choose a random velocity to move
        this.velx = Math.sin(d)*1.4;//combine velocities of this rock and its parent
        this.vely = Math.cos(d)*1.4;
    }
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform prev = g2D.getTransform();
        g2D.rotate(dir,this.x,this.y);
        draw(g);
        g2D.setTransform(prev);
    }
    abstract void draw(Graphics g);
    public void update(){
        this.x+= velx;
        this.y+= vely;
        wrap();
        dir+= spin;
    }
    @Override
    public boolean isDebris(){
        return true;
    }
}
