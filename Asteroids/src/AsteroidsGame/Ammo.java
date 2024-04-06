package AsteroidsGame;

import java.awt.*;

public abstract class Ammo extends Shape {
    int time = 60;
    Color col;
    Ammo(double x, double y, double dir) {
        super(x,y);
        int speed = 15;
        velx = speed * Math.sin(dir);
        vely = - speed * Math.cos(dir);
    }

    public void paint(Graphics g){
        g.setColor(col);
        //g.drawOval((int)x-1,(int)y-1,2,2);//draw a circle for the bullet
        g.drawLine((int)x,(int)y,(int)(x+velx),(int)(y+vely));//draw a line for the bullet
        g.setColor(Color.white);
    }


    abstract boolean isTarget(Shape p);

    void incScore(Shape p){

    }

    public void update() {
        time--;
        this.x+= velx;
        this.y+= vely;
        wrap();
        if (time < 0)//destroy after 80 ticks
            Asteroids.objects.remove(this);
        //check collisions
        for (Shape p: Asteroids.objects) {
            if(isTarget(p) && p.Colliding(this)){//if colliding with this rock
                p.destroy();//destroy the rock
                incScore(p);
                Asteroids.objects.remove(this);//remove the bullet
                break;//we want it to destroy a maximum of 1 rock so don't check the rest
            }
        }
    }
}