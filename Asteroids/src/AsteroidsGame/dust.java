package AsteroidsGame;

import java.awt.*;

public class dust extends Shape {
    private int life = (int)(Math.random()*20)+20;
    dust(double x,double y){
        super(x,y);
        double dir = Math.random() * Math.PI * 2;
        this.velx = 2 * Math.sin(dir);
        this.vely = 2 * Math.cos(dir);
    }
    public void paint(Graphics g){
        g.setColor(new Color(255,255,200));
        g.drawOval((int)x-1,(int)y-1,2,2);
        g.setColor(Color.white);
    }
    public void update(){
        life--;
        x+= velx;
        y+= vely;
        if (life < 0)//destroy once time is up
            Asteroids.objects.remove(this);
    }
}
