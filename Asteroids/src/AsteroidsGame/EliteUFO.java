package AsteroidsGame;

import java.awt.*;

public class EliteUFO extends UFO {
    private int life;
    private boolean ejected;
    EliteUFO(double x, double y, double dir,double velx,double vely){
        super(x,y,dir);
        radius = (int)(s*8);
        skin = new Skin(4,s,new Color(255,255,255));
        ejected = false;
        life = 0;

        this.velx = velx +  6 * Math.sin(dir);//0.3
        this.vely = vely - 6 * Math.cos(dir);//0.3
    }
    void draw(Graphics g) {
        //g.drawOval((int) (x - s * 5), (int) (y - s * 5), (int) (s * 10), (int) (s * 10));
        skin.draw(g,(int)x,(int)y,thrust);
    }

    public void update(){
        super.update();
        if (!ejected){
            life++;
            if(life > 6){
                ejected = true;
            }
        }
    }

    void DestroyUs(Shape p){
        if(ejected) {
            super.DestroyUs(p);
        }
    }

    public boolean isCraft() {
        return ejected;
    }
}
