package AsteroidsGame;

import java.awt.*;

public abstract class NpcShip extends SpaceCraft{
    double TarX = 0;
    double TarY = 0;
    int range = 0;
    boolean friend;
    Shape target;
    NpcShip(double x, double y, double dir){
        super(x, y, dir);
    }

    public void update(){
        super.update();
        thrust = false;
    }
    double assignClosest(){
        double closest = Integer.MAX_VALUE;
        for (Shape p : Asteroids.objects) {
            if (p!= this && ((p.isDebris() && !p.isPower())|| p.isCraft())) {

                double tx = p.x;
                double ty = p.y;

                double dist = distanceTo(p.x, p.y);
                double dist2;

                if (p.x > x) {
                    dist2 = distanceTo(p.x - xBound, p.y);
                    if (dist > dist2) {
                        dist = dist2;
                        tx = p.x - xBound;
                    }
                } else {
                    dist2 = distanceTo(p.x + xBound, p.y);
                    if (dist > dist2) {
                        dist = dist2;
                        tx = p.x + xBound;
                    }
                }

                if (p.y > y) {
                    dist2 = distanceTo(p.x, p.y - yBound);
                    if (dist > dist2) {
                        dist = dist2;
                        ty = p.y - yBound;
                    }
                } else {
                    dist2 = distanceTo(p.x, p.y + yBound);
                    if (dist > dist2) {
                        dist = dist2;
                        ty = p.y + yBound;
                    }
                }


                if (dist < p.radius + radius) {//if colliding with this rock
                    DestroyUs(p);
                    break;
                }

                if (dist < closest){// && isDangerous(p)) {
                        closest = dist;
                        TarX = tx;
                        TarY = ty;
                        range = p.radius;
                        friend = isFriend(p);
                }
            }
        }
        return closest;
    }

    void DestroyUs(Shape p){
        p.destroy();//destroy the rock
        destroy();
    }

    void turnTo(double tarAng){
        if (Math.abs(tarAng) > 0.09) {
            if (tarAng > 0)
                dir += 0.09;
            else
                dir -= 0.09;
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
    void smartMove(double tarAng){
        if (Math.abs(tarAng) < Math.PI / 4.0) {
            accel();
        }
    }
    void accel(){
        thrust = true;
        velx += 0.15 * Math.sin(dir);//0.3
        vely -= 0.15 * Math.cos(dir);//0.3
    }
    void draw(Graphics g) {
        //g.drawOval((int) (x - s * 5), (int) (y - s * 5), (int) (s * 10), (int) (s * 10));
        skin.draw(g,(int)x,(int)y,thrust);
    }
    abstract void getTarget();
    abstract boolean isFriend(Shape p);
}
