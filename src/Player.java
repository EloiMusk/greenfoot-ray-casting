import greenfoot.*;

import static util.Labyrinth.isTouchingLabyrinth;

public class Player {

    GreenfootImage costume;

    double speed = 1;

    GreenfootImage background;

    int x;
    int y;
    int rotation;
    int boundWidth;
    int boundHeight;

    public Player(GreenfootImage background) {
        this.background = background;
        this.boundHeight = background.getHeight();
        this.boundWidth = background.getWidth();
        this.x = boundWidth / 2;
        this.y = boundHeight / 2;
        init();
    }

    private void init() {
//        drawTriangle();
        setRotation(0);
    }

    private void setRotation(int rotation) {
        // First normalize
        if (rotation >= 360) {
            // Optimize the usual case: rotation has adjusted to a value greater than
            // 360, but is still within the 360 - 720 bound.
            if (rotation < 720) {
                rotation -= 360;
            } else {
                rotation = rotation % 360;
            }
        } else if (rotation < 0) {
            // Likwise, if less than 0, it's likely that the rotation was reduced by
            // a small amount and so will be >= -360.
            if (rotation >= -360) {
                rotation += 360;
            } else {
                rotation = 360 + (rotation % 360);
            }
        }

        if (this.rotation != rotation) {
            this.rotation = rotation;
        }
    }

    public int getRotation() {
        return rotation;
    }

    private void turn(int degrees) {
        setRotation(rotation + degrees);
    }

    private void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    private boolean isAtEdge() {
        return x < 0 || x > boundWidth || y < 0 || y > boundHeight;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private void drawTriangle() {
        if (costume != null) {
            costume.clear();
        }
        double scale = (0.5 / 900.0) * (double) boundWidth;
        int width = (int) (30 * scale);
        int height = (int) (40 * scale);
        costume = new GreenfootImage(width, height);
        costume.setColor(new Color(0, 0, 0, 0));
        costume.fillPolygon(
                new int[]{(int) (15 * scale), (int) (30 * scale), 0},
                new int[]{0, (int) (40 * scale), (int) (40 * scale)},
                3
        );
    }

    public void act() {
        moveControl();
    }

    private void tryMove(int dx, int dy) {
        setLocation(x + dx, y + dy);
        if (isAtEdge() || isTouchingLabyrinth(x, y, background)) {
            setLocation(x - dx, y - dy);
        }
    }

    public void moveByDistance(double distance) {
        double radians = Math.toRadians(rotation - 90);
        int dx = (int) Math.round(Math.cos(radians) * distance);
        int dy = (int) Math.round(Math.sin(radians) * distance);
        tryMove(0, dy);
        tryMove(dx, 0);
    }

    //    private void rotationControl(){
//        if (Greenfoot.getMouseInfo() != null) {
//            turnTowards(Greenfoot.getMouseInfo().getX(), Greenfoot.getMouseInfo().getY());
//        }
//    }
    private void moveControl() {
        if (Greenfoot.isKeyDown("w")) {
            moveByDistance(speed);
        }
        if (Greenfoot.isKeyDown("s")) {
            moveByDistance(-speed);
        }
        if (Greenfoot.isKeyDown("a")) {
            turn(-5);
//            if (isAtEdge() || isTouchingLabyrinth(x, y, background)) {
//                turn(5);
//            }
        }
        if (Greenfoot.isKeyDown("d")) {
            turn(5);
//            if (isAtEdge() || isTouchingLabyrinth(x, y, background)) {
//                turn(-5);
//            }
        }
    }
}
