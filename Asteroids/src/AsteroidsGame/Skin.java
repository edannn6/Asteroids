package AsteroidsGame;

import java.awt.*;

public class Skin {
    type t;
    Skin(int i, double s, Color col){
        if (i == 0)
            t = new player(col,s);
        else if(i == 1)
            t = new ally(col,s);
        else if(i==2)
            t = new UFOa(col,s);
        else if(i==3)
            t = new UFOb(col,s);
        else if(i==4)
            t = new UFOc(col,s);
        else
            t = new wormHead(col,s);
    }
    void draw(Graphics g,int x, int y,boolean thrust){
        t.draw(g,x,y,thrust);
    }
    void draw(Graphics g,int x, int y,boolean thrust,boolean immune){
        if(immune) {//indicate immunity
            t.col = new Color(t.col.getRed(),t.col.getGreen(),t.col.getBlue(),100);
            //t.setColor(new Color(200, 230, 255, 100));
            t.drawShield(g,x,y);
            t.draw(g,x,y,thrust);
            t.col = new Color(t.col.getRed(),t.col.getGreen(),t.col.getBlue(),255);
        }
        else{
            t.draw(g,x,y,thrust);
        }
    }
}

abstract class type {
    Color col;
    double s;
    int flameY;
    type(Color col,double s){
        this.col = col;
        this.s = s;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        if(thrust){
            //draw flame out from behind if moving forward
            g.setColor(new Color(255,240,150));
            drawThrust(g,x,y+(int)(flameY*s));
        }
        g.setColor(col);
    }
    void drawShield(Graphics g,int x, int y){
        int rad = 25;
        g.setColor(col);
        g.drawOval((int) (x - rad * s), (int) (y - rad * s), rad*2 * (int) s, rad*2 * (int) s);
    }
    void drawThrust(Graphics g, int x, int y){
        g.drawLine((int)(x - 2*s), y,x,(int)(y +7*s));
        g.drawLine((int)(x + 2*s), y,x,(int)(y +7*s));
    }
}
class player extends type{
    player(Color col,double s){
        super(col,s);
        flameY = 6;
    }

    void draw(Graphics g, int x, int y,boolean thrust){
        super.draw(g,x,y,thrust);

        //the ship is made up of these 3 lines
        g.drawLine(x, (int)(y - 10*s),(int)(x - 5*s),(int)(y+10*s));
        g.drawLine(x, (int)(y - 10*s),(int)(x + 5*s),(int)(y+10*s));
        g.drawLine((int)(x - 4*s), (int)(y +6*s),(int)(x + 4*s),(int)(y +6*s));

        g.setColor(Color.white);//reset the colour ro white
    }
}

class ally extends type{
    ally(Color col,double s){
        super(col,s);
        flameY = 8;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        super.draw(g,x,y,thrust);
        g.drawLine(x, (int)(y - 10*s),(int)(x - 5*s),(int)(y+10*s));
        g.drawLine(x, (int)(y - 10*s),(int)(x + 5*s),(int)(y+10*s));
        g.drawLine(x, (int)(y +6*s),(int)(x + 5*s),(int)(y +10*s));
        g.drawLine(x, (int)(y +6*s),(int)(x - 5*s),(int)(y +10*s));

        g.setColor(Color.white);//reset the colour ro white
    }
}

class UFOa extends type{
    UFOa(Color col, double s){
        super(col,s);
        flameY = 8;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        super.draw(g,x,y,thrust);
        g.drawOval((int) (x - s * 5), (int) (y - s * 5), (int) (s * 10), (int) (s * 10));
        //(flat back)
        g.drawArc((int) (x - 8 * s), (int) (y - 8 * s), (int) (16 * s), (int) (16 * s), 180, -180);
        g.drawLine((int) (x - 8 * s), (int) y, (int) (x - 8 * s), (int) (y + 12 * s));//long side
        g.drawLine((int) (x + 8 * s), (int) y, (int) (x + 8 * s), (int) (y + 12 * s));//long side
        g.drawLine((int) (x + 8 * s), (int) (y + 8 * s), (int) (x - 8 * s), (int) (y + 8 * s));//back

        g.setColor(Color.white);//reset the colour ro white
    }
}
class UFOb extends type{
    UFOb(Color col,double s){
        super(col,s);
        flameY = 8;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        g.drawOval((int) (x - s * 5), (int) (y - s * 5), (int) (s * 10), (int) (s * 10));
        super.draw(g,x,y,thrust);
        //(round)
        g.drawOval(x-(int)(8*s),y-(int)(8*s),(int)(16*s),(int)(16*s));
        g.drawLine(x-(int)(8*s),y,(int)(x-8*s),(int)(y+8*s));//short side
        g.drawLine(x+(int)(8*s),y,(int)(x+8*s),(int)(y+8*s));//short side
        g.setColor(Color.white);//reset the colour to white
    }
}
class UFOc extends type{
    UFOc(Color col, double s){
        super(col,s);
        flameY = 10;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        super.draw(g,x,y,thrust);
        g.drawOval((int) (x - s * 5), (int) (y - s * 5), (int) (s * 10), (int) (s * 10));

        g.drawArc((int) (x - 8 * s), (int) (y - 8 * s), (int) (16 * s), (int) (16 * s), 180, -180);
        g.drawLine((int) (x - 8 * s), y, (int) (x - 8 * s), (int) (y + 12 * s));//long side
        g.drawLine((int) (x + 8 * s), y, (int) (x + 8 * s), (int) (y + 12 * s));//long side
        g.drawLine((int) (x + 8 * s), (int) (y + 12 * s),x,(int) (y + 8 * s));
        g.drawLine((int) (x - 8 * s), (int) (y + 12 * s),x,(int) (y + 8 * s));

        g.setColor(Color.white);//reset the colour to white
    }
}
class wormHead extends type{
    wormHead(Color col, double s){
        super(col,s);
        flameY = 10;
    }
    void draw(Graphics g, int x, int y,boolean thrust){
        g.setColor(col);
        g.drawArc((int) (x - 10 * s), (int) (y - 10 * s), (int) (20 * s), (int) (20 * s), 180, -180);
        g.drawLine((int) (x - 10 * s), y, (int) (x + 10 * s), y);
        g.setColor(Color.white);//reset the colour to white
    }
}



