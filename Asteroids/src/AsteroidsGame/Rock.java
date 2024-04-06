package AsteroidsGame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class Rock extends Debris{

    private ArrayList<Point2D> points = new ArrayList<>();
    Rock(double x, double y, double velx, double vely, int size){
        super(x,y);
        this.size = size;
        //double d = Math.random() * Math.PI * 2;//choose a random velocity to move
        this.velx += velx;//combine velocities of this rock and its parent
        this.vely += vely;
        radius = size * 15;
        createPoints();
    }

    void draw(Graphics g){
        //g.drawOval((int)x-radius,(int)y-radius,radius*2,radius * 2);//draw a circle the correct size of the rock
        for (int i = 0; i < points.size(); i++) {
            g.drawLine((int)(x+points.get(i).getX()),(int)(y+points.get(i).getY()),(int)(x+points.get((i+1)%points.size()).getX()),(int)(y+points.get((i+1)%points.size()).getY()));
        }
    }

    public void destroy(){
        //generate smaller rocks if not the smallest
        if(size > 2){
            for (int i = 0; i < 4; i++) {//spawn more rocks of half the size
                Asteroids.objects.add(new Rock(x,y,velx,vely,size/2));
            }
        }
        else if(size > 1) {
            for (int i = 0; i < 3; i++) {//spawn more rocks of half the size
                Asteroids.objects.add(new Rock(x,y,velx,vely,size/2));
            }
        }
        explode();//create particles around explosion

        Asteroids.objects.remove(this);//remove this object
    }
    @Override
    public boolean isRock(){
        return true;
    }//this is a rock

    private void createPoints(){
        int no = size * 2 + 9;
        for(int i = 0; i < no; i++){
            double rad = ((Math.random()*0.5)+0.8) * radius;//(((Math.random() * 5)+8) * size;//random radius
            double ang = i*2*Math.PI/no;//angle
            points.add(new Point2D.Double(Math.sin(ang)*rad,Math.cos(ang)*rad));
        }
    }
}
