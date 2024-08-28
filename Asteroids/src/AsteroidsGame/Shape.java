package AsteroidsGame;


import java.awt.*;

public abstract class Shape {
    double dir;
    double x;
    double y;
    double velx;
    double vely;
    int radius = 0;
    int type;
    int size;
    int xBound;
    int yBound;

    Shape(double x, double y) {
        this.x = x;
        this.y = y;
        xBound = utilities.SpaceFrame.width;
        yBound = utilities.SpaceFrame.height;
    }

    public abstract void paint(Graphics g);

    public abstract void update();

    public void move(boolean[] bool) {
    }

    public void destroy() {
    }

    void wrap() {//wrap around the screen. When an object moves off the screen it should appear on the other side
        if (x < 0) {
            x += xBound;//800
        } else
            x %= xBound;
        if (y < 0) {
            y += yBound;//600
        } else
            y %= yBound;
    }

    public boolean isRock() {
        return false;
    }//assume this is not a rock

    public boolean isShip() {
        return false;
    }//assume this is not a ship

    public boolean isEgg(){
        return false;
    }

    public boolean isDebris(){
        return false;
    }

    public boolean isPower() {
        return false;
    }//assume this is not a power up

    public boolean isUFO() {
        return false;
    }//assume this is not a ship

    public boolean isCraft() {
        return false;
    }//assume this is not a ship

    public boolean isFriend(){
        return false;
    }

    void explode() {
        for (int i = 0; i < 6; i++) {
            Asteroids.objects.add(new dust(x, y));//create dust particles
        }
    }

    double distanceTo(double x, double y) {//return the distance from the centre of this object to the coords given
        return (Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)));
    }
    boolean Colliding(Shape p){
        return distanceTo(p.x, p.y) < radius + p.radius;
    }
}
