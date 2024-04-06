package AsteroidsGame;

import java.awt.*;

public class PowerUp extends Debris{
    private int life;
    PowerType power;

    PowerUp(double x,double y){
        super(x,y);
        radius = 30;
        life = 900;
        determineType();
        //if(type == 0)
            //determineType();
        //if(type == 0)
            //determineType();

        if(type == 0)
            power = new LifeUp();
        else if (type == 1)
            power = new ScatterUp();
        else
            power = new RapidUp();
    }
    private void determineType(){
        type = (int)Math.round(Math.random()*2);
    }
    @Override
    void draw(Graphics g) {
        g.setColor(new Color(200,230,255));
        g.drawRect((int)x-15,(int)y-15,30,30);
        power.draw(g,(int)x,(int)y);
        g.setColor(Color.white);
    }
    public void update(){
        super.update();
        life--;
        if(life < 0){
            Asteroids.objects.remove(this);
        }
    }
    public boolean isPower(){
        return true;
    }
}
abstract class PowerType{
    abstract void draw(Graphics g, int x, int y);
}
class LifeUp extends PowerType{
    void draw(Graphics g,int x, int y){
        g.setColor(new Color(170,255,150));
        g.drawLine(x-6,y-8,x-6,y+8);
        g.drawLine(x-6,y+8,x+7,y+8);
    }
}

class ScatterUp extends PowerType{
    @Override
    void draw(Graphics g, int x, int y) {
        g.setColor(new Color(170,255,150));
        g.drawLine(x,y-8,x,y+8);
        g.drawLine(x-8,y-8,x-3,y+8);
        g.drawLine(x+8,y-8,x+3,y+8);
    }
}

class RapidUp extends PowerType{
    @Override
    void draw(Graphics g, int x, int y) {
        g.setColor(new Color(170,255,150));
        g.drawLine(x,y-8,x,y+8);
        g.drawLine(x+5,y-8,x+5,y+8);
        g.drawLine(x-5,y-8,x-5,y+8);
    }
}