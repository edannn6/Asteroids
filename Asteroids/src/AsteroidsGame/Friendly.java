package AsteroidsGame;

import java.awt.*;

public class Friendly extends NpcShip {
    Friendly(double x, double y, double dir) {
        super(x, y, dir);
        radius = (int)(s*8);
        skin = new Skin(1,s,new Color(100,250,150));
        cooldown = 100;
    }

    public void update() {
        super.update();
        //searchUFO();
        getTarget();
        double tarAng = 0;
        double dist = 0;
        double closest= assignClosest();
        if (closest < 150 + range) {
            tarAng = getDirection(TarX,TarY,0);
            if(!friend && tarAng < Math.PI/4.0){
                turnTo(tarAng);
                if(tarAng < 0.09){
                    shoot();
                }
            }
            else {
                tarAng = getDirection(TarX, TarY, Math.PI);
                turnTo(tarAng);
                smartMove(tarAng);
            }
        }
        else if (target != null) {
            tarAng = getDirection(target.x,target.y,0);
            turnTo(tarAng);
            dist = distanceTo(target.x,target.y);
            if(target.isUFO() || dist > 400 || (target.isRock()&&target.size == 1&&dist>100)){
                smartMove(tarAng);
            }
        }
        if(target != null && (!(target.isRock()&&target.size <2)|| dist< 100))
            fire();
        wrap();
    }

    @Override
    void fire() {
        if(cooldown <= 0 && Asteroids.objects.contains(target)&& Math.abs(getDirection(target.x,target.y,0)) < 0.09){//shoot if pointing at
            cooldown = 15;
            Asteroids.objects.add(new Bullet(x+Math.sin(dir)*radius, y-Math.cos(dir)*radius, dir));
        }
    }
    private void shoot(){
        if(cooldown <= 0){
            cooldown = 15;
            Asteroids.objects.add(new Bullet(x+Math.sin(dir)*radius, y-Math.cos(dir)*radius, dir));
        }
    }
    void searchUFO(){
        for(Shape p: Asteroids.objects) {
            if (p.isUFO()) {
                target = p;
                return;
            }
        }
    }

    @Override
    void getTarget() {
        target = null;
        double dist = Integer.MAX_VALUE;
//        double size = Integer.MAX_VALUE;
        for(Shape p: Asteroids.objects){
            if (p.isUFO()) {
                target = p;
                return;
            }
            if(p.isRock()){
//                if(size > p.size){
//                    size = p.size;
//                    target = null;
//                }
                double dist2 =  distanceTo(p.x,p.y);
                if(dist > dist2){
                    dist = dist2;
//                    if(size == p.size)
                    if(p.size < 4)
                        target = p;
                    else
                        target = null;
//                    else
//                        target = null;
                }
            }
        }
    }
    boolean isFriend(Shape p){
        return p.isFriend()||p.isEgg();
    }

    public boolean isFriend(){
        return false;
    }
}
