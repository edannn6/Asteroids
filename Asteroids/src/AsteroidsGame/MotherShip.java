package AsteroidsGame;

import java.awt.*;
import java.util.ArrayList;

public class MotherShip extends Debris {
    ArrayList<Dock> docks;
    int HP;
    final int maxHP = 200;
    MotherShip(double x,double y, double dir,int DockNo){
        super(x,y);
        this.dir = dir;
        this.radius = (int)(SpaceCraft.s * 80);
        this.velx *= 0.3;
        this.vely *= 0.3;
        this.spin *=0.05;
        HP = maxHP;
        //this.velx = 0;this.vely = 0;this.spin =0;
        docks = new ArrayList<>();
        for(int i = 0; i < DockNo; i++)
            docks.add(new Dock((i*Math.PI*2)/(DockNo),this));
    }

    @Override
    public void paint(Graphics g) {
        g.drawRect(utilities.SpaceFrame.width/2-300,utilities.SpaceFrame.height-50,600,16);
        g.fillRect(utilities.SpaceFrame.width/2-294,utilities.SpaceFrame.height-44,(int)(588*HP/(double)maxHP),4);
        super.paint(g);
    }

    void draw(Graphics g) {
        //g.drawOval((int)x-radius,(int)y-radius,radius*2,radius * 2);
        double dockAng = Math.toDegrees(Math.asin(10/80.0));
//        for (Dock d: docks) {
//            g.drawOval((int)(this.x+d.x)-10,(int)(this.y+d.y)-10,20,20);
//        }
        for (Dock d: docks) {
            d.draw(g);
            g.drawArc((int)x-radius,(int)y-radius,radius*2,radius * 2,(90-(int)dockAng),(int)(dockAng*2-1-360/(double)docks.size()));
            Graphics2D g2D = (Graphics2D)g;
            g2D.rotate((Math.PI*2)/docks.size(),this.x,this.y);
        }
    }
    public void update(){
        super.update();
        for (Shape p: Asteroids.objects) {
            if(p !=this && (p.isDebris()||p.isCraft()) && Colliding(p)){
                if(p.isPower())
                    Asteroids.objects.remove(p);
                else
                    destroy();
                p.destroy();

            }
        }
        for (Dock d: docks) {
            d.update();
            //d.endSpawning();
        }
    }
    public void destroy(){
        HP--;
        if(HP < 1){
            explode();
            for(int i = 0; i < 5; i++)
                Asteroids.objects.add(new PowerUp(this.x,this.y));
            Asteroids.objects.remove(this);
        }
    }
    boolean Colliding(Shape p){
        if (super.Colliding(p)){
            for (Dock d: docks) {
                if(d.Colliding(p)){
                    if(d.isSpawning()) {
                        d.endSpawning();
                        return true;
                    }
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

class Dock {
    private double x,y,dir;
    private MotherShip m;
    private boolean spawning;
    private float generate;
    private double s = SpaceCraft.s;
    Dock(double ang, MotherShip m){
        this.m = m;
        //this.x = -m.radius * Math.sin(ang);
        //this.y = -m.radius * Math.cos(ang);
        this.dir = ang;
        spawning = false;
        generate = 0;
    }
    public void draw(Graphics g) {
        //g.drawLine((int)(m.x - s*10),(int)(22*s+m.y-m.radius),(int)(s*10+m.x),(int)(22*s+m.y-m.radius));
        g.drawLine((int)(m.x - s*10),(int)(23*s+m.y-m.radius),(int)(m.x),(int)(18*s+m.y-m.radius));
        g.drawLine((int)(m.x + s*10),(int)(23*s+m.y-m.radius),(int)(m.x),(int)(18*s+m.y-m.radius));

        g.drawLine((int)(m.x - s*10),(int)(23*s+m.y-m.radius),(int)(m.x - s*10),(int)(m.y-m.radius + s));
        g.drawLine((int)(s*10+m.x),(int)(23*s+m.y-m.radius),(int)(s*10+m.x),(int)(m.y-m.radius + s));

        if(spawning) {
            Skin skin = new Skin(4, SpaceCraft.s, new Color(255, 255, 255,(int)generate));
            skin.draw(g, (int) m.x, (int) (-m.radius + 8 * s + m.y), false);
        }
        //g.drawLine((int)m.x,(int)m.y,(int)m.x,(int)(m.y+m.radius));
    }
    boolean isSpawning(){
        return spawning;
    }
    public void update(){
        if(spawning){
            generate+=0.4;
            if(generate > 255){
                endSpawning();
                Asteroids.objects.add(new EliteUFO(m.x-(m.radius*Math.sin(-m.dir-dir)),m.y-(m.radius*Math.cos(-m.dir-dir)),m.dir + this.dir,m.velx,m.vely));
            }
        }
        else{
            spawning = Math.random() < 0.001;
        }
    }
    void endSpawning(){
        spawning = false;
        generate = 0;
    }
    boolean Colliding(Shape p) {
        return p.distanceTo(m.x-(m.radius*Math.sin(-m.dir-dir)),m.y-(m.radius*Math.cos(-m.dir-dir)))<s*10-p.radius;
    }
}

