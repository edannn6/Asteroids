package AsteroidsGame;

import java.awt.*;

public class UFO extends NpcShip {
    UFO(double x, double y, double dir) {
        super(x, y, dir);
        radius = (int)(s*8);
        skin = new Skin(2,s,new Color(200,200,200));
    }

    public void update() {
        super.update();
        if(!Asteroids.objects.contains(target)) {
            getTarget();
            cooldown = 100;
        }
        double tarAng = 0;
        if (assignClosest() < 150 + range) {
            tarAng = getDirection(TarX ,TarY ,Math.PI);
        }
        else if (target != null) {
            if (distanceTo(target.x, target.y) < 300)
                tarAng = getDirection(target.x,target.y,Math.PI/1.5);
            else
                tarAng = getDirection(target.x,target.y,0);
        }
        turnTo(tarAng);
        smartMove(tarAng);
        if(cooldown <= 0 && Asteroids.objects.contains(target)&& Math.abs(getDirection(target.x,target.y,0)) < 0.09){//shoot if pointing at
            fire();
        }
        wrap();
    }
    @Override
    void makeBullet(double ang) {
        Asteroids.objects.add(new EnemyBullet(x+Math.sin(dir)*radius, y-Math.cos(dir)*radius, dir+ang));
    }

    @Override
    public boolean isUFO(){
        return true;
    }

    void getTarget(){
        target = null;
        for (Shape p : Asteroids.objects) {
            if ((p.isCraft()&&!p.isUFO())) {
                target = p;
                return;
            }
        }
    }
    boolean isFriend(Shape p){
        return p.isUFO();
    }
}
