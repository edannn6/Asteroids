package AsteroidsGame;

import utilities.SpaceFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Asteroids extends JPanel{
    //all the game objects. Each object's class extends Shape
    static public CopyOnWriteArrayList<Shape> objects;
    static public CopyOnWriteArrayList<Shape> particles;
    public static boolean isRunning = true;
    public static boolean inGame= false;
    //the keyboard inputs
    public static boolean upKey;
    public static boolean fireKey;
    public static boolean leftKey;
    public static boolean rightKey;

    //variables used in games accessed by other classes. (Bullet increases score when it hits a rock, ship decreases lives when it is destroyed)
    static int lives;
    public static int score = 0;
    //private variables for recording the level/knowing when to start a new level
    private int level;
    private String levelType;
    private boolean anyRocks = true;
    //for showing the place in score table
    private int rank = -1;
    //for highscores
    private ArrayList<Integer> scores;
    private File file = new File("AsteroidsScores.txt");

    public Asteroids(){
        //read the high score list
        readFile();
        //setRank();
    }

    private void readFile(){
        scores = new ArrayList<>();
        try{//read the scores
            BufferedReader read = new BufferedReader(new FileReader(file));
            String line;
            while ((line = read.readLine()) != null) {
                scores.add(Integer.parseInt(line));
                //System.out.println(line);
            }
            //System.out.println("file read");
        }catch(Exception e){//Error in reading file. Make a new file
            try{
                file.createNewFile();
                //System.out.println("file made");
            }catch (Exception ex){}
        }
    }

    private void writeFile(){
        Collections.sort(scores,Collections.reverseOrder());//sort the list first
        try{
            FileWriter fw = new FileWriter(file);
            for(int i = 0; i < scores.size(); i++){
                fw.write(Integer.toString(scores.get(i))+"\r\n");
            }
            fw.close();
        }catch(Exception e){
            //System.out.println("failed to write file");
        }
    }

    //the game loop
    public void StartGame(){
        objects = new CopyOnWriteArrayList<>();
        particles = new CopyOnWriteArrayList<>();

        //used for the gameloop
        final int idealFps = 60;
        final long optimalTime = 1000000000 / idealFps;

        //initialise variables for new game
        level = 1;
        lives = 3;
        //score = 0;
        upKey = false;
        fireKey = false;
        leftKey = false;
        rightKey = false;
        //game loop ends when isRunning is false;
        isRunning = true;
        inGame = false;

        //variables used to know what tick to do something
        //int lifeDelay = 60;//how long to wait to respawn. Will be set to 60(1 second) in the game loop. The ship will first spawn immediately
        //int levelDelay = 60;//how long to wait before starting a new level. Will be set to 60(1 second) in the game loop. Level 1 will start immediately
        Delay delay = new Delay(60,60);
        int newLifeCount = 5000;

        Ship ship = new Ship(utilities.SpaceFrame.width/2, utilities.SpaceFrame.height/2,0);
        objects.add(ship);//create a ship
        setupNewLevel(ship);//create rocks

        //objects.add(new Friendly(utilities.SpaceFrame.width/4, utilities.SpaceFrame.height/4,0));//create a ship
        objects.add(new Friendly(500, 500,0));//create a ship
        //objects.add(new SpaceWorm(300,300,0));
        objects.add(new MotherShip(500, 500,0,3));//create a ship
        while (isRunning) {//do until game is over
            long lastTime =  System.nanoTime();//the current time

            if(inGame) {
                tryNewLevel(anyRocks, delay, ship);
                trySpawnPowerUp();
                trySpawnUFO(level);

                if (!objects.contains(ship)){
                    if (delay.life < 0) {
                        if (lives > 0) {//it must be respawned after 1 second if there are lives left
                            delay.life = 60;
                            ship = new Ship(utilities.SpaceFrame.width/2, utilities.SpaceFrame.height/2,0);
                            objects.add(ship);//create a ship
                        } else {//otherwise the game is over. We still want to wait 1 second before the game ends just to watch the ship explode.
                            //inGame = false;
                            isRunning = false;

                            //the game is over. Write the score to the text file
                            scores.add(score);
                            writeFile();

                            setRank();
                        }
                    } else {//decrement the amount of ticks left to wait for it to respawn
                        delay.life--;
                    }
                }

                UpdateObjects();
            }

            repaint();//repaint all the objects

            try {//wait 1/60th of a second
                Thread.sleep((lastTime - System.nanoTime() + optimalTime) / 1000000);//this is so frames are more accurate in time rather than just using a short delay.
            } catch (Exception e) {
            }
        }
    }
    private void removeEggs(){
        for(Shape p : objects){
            if(p.isEgg()){
                objects.remove(p);
            }
        }
    }
    private void setRank() {
        //find place in list
        rank = 0;
        while (rank < scores.size() && score != scores.get(rank)) {
            rank++;
        }
    }
    //add rocks to game
    private void setupNewLevel(Shape p){
        //The rock must not spawn too close to the ship or the player will lose a life unfairly
        removeEggs();

        setLevelType();

        if(Objects.equals(levelType, "bigrock")){
            placeObj(p, new Rock(0,0,0,0, 16));
        }
        else {//every normal level gets harder each time. The amount of rocks in a level is the level number + 2
            boolean egg = false;
            for (int i = 0; i < level + 2; i++) {//create rocks
                if(!egg && Math.random() < 0.5){
                    egg = true;
                    placeObj(p, new Egg(0,0));
                }
                else {
                    int size = 4;//size of the rock
                    placeObj(p, new Rock(0,0,0,0,size));
                }
            }
        }
    }
    private void setLevelType(){
        //every 10 levels spawn 1 'boss' rock instead of many smaller rocks
        if(level%10 == 0){
            levelType = "bigrock";
        }
        else if (level%5 == 0){
            levelType = "mothership";
        }
        else
        {
            levelType = "standard";
        }
    }
    //place the rock in a safe enough place for the player
    private void placeObj(Shape s, Shape p){

        double placeX = Math.random()*utilities.SpaceFrame.width;
        double placeY = Math.random()*utilities.SpaceFrame.height;
        while(s.distanceTo(placeX,placeY) < p.radius*2 + 150){//find a place far enough away from the ship.
            placeX = Math.random()*utilities.SpaceFrame.width;
            placeY = Math.random()*utilities.SpaceFrame.height;
        }
        p.x = placeX;
        p.y = placeY;
        objects.add(p);//new Rock(placeX,placeY,0,0,size));//place the rock here
    }

    //update objects
    public void UpdateObjects(){
        anyRocks = false;
        for (Shape p : objects){
            if(p.isShip()){// if this is a ship
                boolean[] bool = new boolean[]{upKey,leftKey,rightKey,fireKey};
                p.move(bool);//give the ship information on whether is should move or not
            }
            if(p.isRock()){//see if there are any rocks left
                anyRocks = true;
            }
            p.update();//run the objects update method
        }
    }
    private void tryNewLevel(boolean anyRocks, Delay delay, Ship s){
        if (!anyRocks) {//if there are no rocks then the level is won. Start the next level after 1 second
            if (delay.level < 0) {
                level++;
                setupNewLevel(s);
                delay.level = 60;
            } else {
                delay.level--;
            }
        }
    }


    private void trySpawnUFO(int level){
        if(Objects.equals(levelType, "standard") && Math.random()*18000 < 10+level){
            double x;
            double y;
            if(Math.random()> 0.5){
                x = Math.random()*SpaceFrame.width;
                y = Math.random()* 100 + (SpaceFrame.height - 100)* Math.round(Math.random());
            }
            else {
                x = Math.random()* 100 + (SpaceFrame.width - 100)* Math.round(Math.random());
                y = Math.random() * SpaceFrame.height;
            }
            objects.add(new UFO(x, y,0));//create a ship
        }
    }
    private void trySpawnPowerUp(){
        if(Math.random()*1200 < 1){
            objects.add(new PowerUp(Math.random()*utilities.SpaceFrame.width,Math.random()*utilities.SpaceFrame.height));
        }
    }

    private String Ordinal(int p){//return the number as an ordinal number, ie. number 21 returns 21st.
        String num = Integer.toString(p);
        char last = num.charAt(num.length()-1);
        if(p >= 11 && p <= 13 )
        {
            num += "TH";
        }
        else if(last == '1'){
            num += "ST";
        }
        else if(last == '2'){
            num += "ND";
        }
        else if(last == '3'){
            num += "RD";
        }
        else{
            num+= "TH";
        }
        return num;
    }

    //paint game
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(0, 0, getWidth(), getHeight());//paint space
        //setBackground(Color.black);

        Graphics2D g2D = (Graphics2D) g;
        g2D.setStroke(new BasicStroke(2));//make the lines wider
        g2D.setColor(Color.white);//draw in white
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//make lines 'smoother'
        g.setFont(new Font("OCR A Extended", Font.PLAIN, 35));
        if(inGame) {
            //draw the objects
            for (Shape p : objects) {//run each obejects paint method
                p.paint(g);
            }

            //draw hotbar
            g.drawString("LEVEL: " + level + " SCORE: " + score, 5, 30);//write the score and level

            for (int i = 0; i < lives; i++) {//draw the lives
                int liveX = 20 * (i + 1);
                double s = 1.5;
                //each life is represented by a drawing of the ship
                g.drawLine(liveX, (int) (60 - 10 * s), (int) (liveX - 5 * s), (int) (60 + 10 * s));
                g.drawLine(liveX, (int) (60 - 10 * s), (int) (liveX + 5 * s), (int) (60 + 10 * s));
                g.drawLine((int) (liveX - 4 * s), (int) (60 + 6 * s), (int) (liveX + 4 * s), (int) (60 + 6 * s));
            }
        }
        else{//the high-score page
            g.drawString("HIGH-SCORES:", 5, 30);//title

            int i;
            for(i = 0; i < 10 && i < scores.size(); i++)//show the top ten or all the scores (whichever is shortest)
            {
                String line = Ordinal(i+1)+": ";
                if(i < 9){//add a space before all but the 10th place so the line up
                    line = " " + line;
                }
                if(rank == i){
                    line += Integer.toString(scores.get(i)) + "(YOU)";//indicate to the player if this is their score
                }
                else{
                    line += Integer.toString(scores.get(i));
                }
                g.drawString(line, 5, i*30+90);
            }
            if(rank != -1) {
                g.drawString("YOU CAME: " + Ordinal(rank + 1), 5, i * 30 + 120);
                g.drawString("YOU SCORED:" + score, 5, i * 30 + 150);
            }

            //state the options to the user
            g.drawString("PRESS R KEY TO PLAY AGAIN", 5, getHeight()-35);
            g.drawString("PRESS Q TO QUIT", 5, getHeight()-5);
        }
    }
}

class Delay {
    public int level,life;
    Delay (int level, int life)
    {
        this.level = level; this.life = life;
    }
}
