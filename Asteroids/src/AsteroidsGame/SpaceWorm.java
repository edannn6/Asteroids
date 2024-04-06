package AsteroidsGame;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SpaceWorm extends NpcShip{
    Tail tail;
    SpaceWorm(double x, double y, double dir){
        super(x,y,dir);
        skin = new Skin(5,s,new Color(255,255,255));
        radius =(int)(10*s);
        tail = new Tail(this.x,this.y,this);


        tail.addSegment();

        Tail t = tail.child;
        for(int i= 0; i < 5; i++){
            t.addSegment();
            t = t.child;
        }


       getTarget();
    }
    public void update(){
        super.update();

        if(Asteroids.objects.contains(target)) {
            double tarAng = getDirection(target.x,target.y,0);
            turnTo(tarAng);
            smartMove(tarAng);
        }
        else
            accel();

        velx += 0.1 * Math.sin(dir);//0.3
        vely -= 0.1 * Math.cos(dir);//0.3

        //dir += 0.02;
        //wrap();
        tail.update();
    }
    public void paint(Graphics g){
        super.paint(g);
        tail.paint(g);
    }
    void fire(){}
    void getTarget(){
        double closest_dist = Integer.MAX_VALUE;
        for (Shape p: Asteroids.objects) {
            if (p.isCraft()){
                double dist = distanceTo(p.x,p.y);
                if (dist  < closest_dist){
                    target = p;
                    closest_dist = dist;
                }
            }
        }
    }
    boolean isFriend(Shape s){
        return false;
    }
    void cooldowns(){}
    @Override
    void turnTo(double tarAng){
        if (Math.abs(tarAng) > 0.02) {
            if (tarAng > 0)
                dir += 0.04;
            else
                dir -= 0.04;
        }
    }
}

class Tail extends Shape{
    Tail child;
    Shape parent;
    Tail(double x, double y, Shape p){
        super(x,y);
        child = null;
        parent = p;
        radius = (int)(8*SpaceCraft.s);
    }

    public void addSegment(){
        child = new Tail(this.x,this.y,this);
    }
    @Override
    public void update() {

        dir = Math.atan2(parent.y-y,parent.x-x)+Math.PI/2.0;
        //dir = ((dir)+Math.PI)%(Math.PI*2)-Math.PI;
        //MaxTurn(0.5);
        //turnTo(parent.dir-dir);
        //dir = (dir*5+parent.dir)/6.0;
        //dir = ((parent.dir-dir)/20.0)+dir;

        this.x = parent.x + radius*Math.sin(dir+Math.PI);
        this.y = parent.y - radius*Math.cos(dir+Math.PI);
        if(child != null)child.update();
    }
    private void MaxTurn(double a){
        double ang = (parent.dir-dir+Math.PI)%(Math.PI*2)-Math.PI;// Math.PI / 2.0;
        if(ang>a){
            dir = parent.dir-a;
        }
        if(ang < -a){
            dir = parent.dir+a;
        }
    }
    double getDirection(double px, double py, double offset){
        double tarAng;
        tarAng = offset + Math.atan2(py - y, px - x) - dir + Math.PI / 2.0;
        if (tarAng > Math.PI)
            tarAng -= Math.PI * 2;
        else if (tarAng < -Math.PI)
            tarAng += Math.PI * 2;
        return tarAng;
    }

    void turnTo(double tarAng){
        if (Math.abs(tarAng) > 1) {
            if (tarAng > 0)
                dir += 0.09;
            else
                dir -= 0.09;
        }
    }
    private void draw(Graphics g){
        //g.drawOval((int) (x - radius/2.0), (int) (y - radius/2.0),radius, radius);
        //g.drawOval((int) (x - radius), (int) (y - radius),radius*2, radius*2);
        g.drawLine((int)(x-radius),(int)y,(int)(x+radius),(int)y);
        g.drawLine((int)(x-radius),(int)y,(int)x,(int)y-radius);
        g.drawLine((int)(x+radius),(int)y,(int)x,(int)y-radius);
    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        AffineTransform prev = g2D.getTransform(); //previous transformation
        g2D.rotate(dir,this.x,this.y);//rotate to correct direction
        draw(g);
        g2D.setTransform(prev);//revert the rotation
        if(child!= null)child.paint(g);
    }
}
