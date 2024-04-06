package AsteroidsGame;

import java.awt.*;

public class Egg extends Debris {
    Egg(double x, double y){
        super(x,y);
        radius = 30;
    }
    void draw(Graphics g){
        //g.drawOval((int)x-radius,(int)y-radius,radius*2,radius * 2);//draw a circle the correct size of the rock
        g.drawArc((int) (x - radius), (int) (y - radius * 1.5)+radius/4, radius * 2, radius*3, 180, -180);
        g.drawArc((int) (x - radius), (int) (y - radius)+radius/4, radius * 2, radius*2, 180, 180);

        g.drawOval((int) (x - radius * 0.5),(int)(y - radius * 0.2),radius/10,radius/10);
        g.drawOval((int) (x + radius * 0.2),(int)(y - radius * 0.5),radius/5,radius/5);
        g.drawOval((int) (x + radius * 0.1),(int)(y + radius * 0.4),radius/5,radius/5);
    }
    public void destroy(){
        explode();
        //Asteroids.objects.add(new SpaceWorm(this.x,this.y,this.dir));
        Asteroids.objects.remove(this);
    }
    @Override
    public boolean isEgg(){
        return true;
    }
}
