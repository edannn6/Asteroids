package AsteroidsGame;

import java.awt.*;

public class EnemyBullet extends Ammo{
    EnemyBullet(double x, double y, double dir){
        super(x,y,dir);
        col = new Color(255,100,100);
    }
    boolean isTarget(Shape p){
        return p.isDebris() || (p.isCraft()&&!p.isUFO());
    }
}
