import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import util.Actor;
import util.Location;

import java.util.ArrayList;

import static util.Labyrinth.isTouchingLabyrinth;

public class Main extends World {
    GreenfootImage background;
    GreenfootImage image;
    int fov = 120;
    int resolution = 100;
    Player player;
//    int step = 1;
//    boolean wDown = false;
//    boolean sDown = false;

    public Main() {
        super(600, 600, 1, false);
        image = new GreenfootImage("background.png");
        image.scale(image.getWidth()*10, image.getHeight()*10);
        background = getBackground();
        init();
    }

    private int castRay(int rotation) {
        int steps = 1;
        int distance = 0;
        double x = player.getX();
        double y = player.getY();
        double sin = Math.sin(Math.toRadians(rotation)) * steps;
        double cos = Math.cos(Math.toRadians(rotation)) * steps;
        while (!isTouchingLabyrinth((int) x, (int) y, image) && distance < 1000) {
            x += cos;
            y += sin;
            if (x < image.getWidth() && x > 0 && y < image.getHeight() && y > 0) {
                distance++;
            } else {
                break;
            }
        }
        x -= cos;
        y -= sin;
//        background.setColor(Color.BLACK);
//        background.getFont().deriveFont(30);
//        background.drawString(distance + "", 0, 30);
//        drawRay((int) x, (int) y);
        return distance;
    }

    private void drawRay(int x, int y) {
        background.setColor(new Color(Greenfoot.getRandomNumber(255), Greenfoot.getRandomNumber(255), Greenfoot.getRandomNumber(255)));
        background.drawLine(player.getX(), player.getY(), x, y);
    }

    private void init() {
        generateBackground();
        player = new Player(image);
    }

    private void generateBackground() {
        background.clear();
        background.setColor(Color.WHITE);
        background.fill();
//        background.drawImage(image, 0, 0);
    }

    private void refresh() {
        generateBackground();
        int rotation = player.getRotation() - (fov / 2) - 90;
        for (int i = 0; i < fov; i++) {
            int distance = castRay(rotation + i);
            Color color = new Color((int)(i*1.5), i, (int) (255 - (255 * (distance / 1000.0))));
            background.setColor(color);
            int drawHeight = 4000 / (distance + 1);
            for (int j = 0; j < getWidth() / fov; j++) {
                background.drawLine((getWidth() / fov * i) + j, (getHeight() / 2) - (drawHeight / 2), (getWidth() / fov * i) + j, (getHeight() / 2) + (drawHeight / 2));
            }
        }
    }

    public void act() {
        player.act();
        refresh();
    }

//    private void generateCube(){
//        cube = new Actor();
//        cube.location = new Location(250, 250, 300);
//        cube.mesh = new ArrayList<Location>();
//        cube.mesh.add(new Location(0, 0, 0));
//        cube.mesh.add(new Location(0, 0, 100));
//        cube.mesh.add(new Location(0, 100, 0));
//        cube.mesh.add(new Location(0, 100, 100));
//        cube.mesh.add(new Location(100, 0, 0));
//        cube.mesh.add(new Location(100, 0, 100));
//        cube.mesh.add(new Location(100, 100, 0));
//        cube.mesh.add(new Location(100, 100, 100));
//    }

//    private void refresh() {
//        background.clear();
//        Color color = new Color(0, 0, 0, 0);
//        for (int x = 0; x < background.getHeight(); x += step) {
//            for (int y = 0; y < background.getWidth(); y += step) {
//                if (Math.sin(Math.cos(x * y)) > Math.tan(Math.sin(x * y))) {
//                    color = Color.BLUE;
//                } else {
//                    color = Color.WHITE;
//                }
//                background.setColorAt(x, y, color);
//            }
//        }
//    }

//    public void act() {
//        if (Greenfoot.isKeyDown("w") && !wDown) {
//            wDown = true;
//            step += 10;
//            refresh();
//            System.out.println("Step: " + step);
//        } else if (!Greenfoot.isKeyDown("w") && wDown) {
//            wDown = false;
//        }
//
//        if (Greenfoot.isKeyDown("s") && !sDown) {
//            sDown = true;
//            step -= 10;
//            refresh();
//            System.out.println("Step: " + step);
//        } else if (!Greenfoot.isKeyDown("s") && sDown) {
//            sDown = false;
//        }
//
//        if (step > 900) {
//            step = 900;
//        }
//        if (step < 1) {
//            step = 1;
//        }
////        background.clear();
////        background.setColor(Color.BLUE);
////        refresh();
//    }
}
