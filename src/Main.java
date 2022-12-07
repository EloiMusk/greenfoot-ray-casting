import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;

import static util.Labyrinth.isTouchingLabyrinth;

public class Main extends World {
    GreenfootImage background;
    GreenfootImage image;
    GreenfootImage wallTexture;
    int fov = 80;
    double resolution = 1;
    int scanLines;
    Player player;
//    int step = 1;
//    boolean wDown = false;
//    boolean sDown = false;

    public Main() {
        super(600, 600, 1, false);
        image = new GreenfootImage("background.png");
        wallTexture = new GreenfootImage("textures/wall/" + (Greenfoot.getRandomNumber(4) + 1) + ".png");
        image.scale(image.getWidth() * 8, image.getHeight() * 8);
        background = getBackground();
        init();
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
        scanLines = (int) (getWidth() / resolution);
        double rotation = player.getRotation() - (fov / 2.0) - 90;
        for (double i = 0; i <= scanLines; i += resolution) {
//            castRay(rotation + (fov / (double) resolution), i);
            rotation += (fov / (double) scanLines);
            castRay(rotation, i);
        }
    }

    private void castRay(double rotation, double i) {
        int steps = 1;
        double distance = 0;
        double x = player.getX();
        double y = player.getY();
        double sin = Math.sin(Math.toRadians(rotation)) * steps;
        double cos = Math.cos(Math.toRadians(rotation)) * steps;
        while (!isTouchingLabyrinth((int) x, (int) y, image, 1) && distance < image.getWidth() / 2.0) {
            x += cos;
            y += sin;
            if (x < image.getWidth() && x > 0 && y < image.getHeight() && y > 0) {
                distance++;
            } else {
                break;
            }
        }
//        System.out.println(player.getRotation()-rotation);

        distance = distance * Math.sin(Math.toRadians(player.getRotation() - rotation));
        double g = Math.sqrt(Math.pow(getWidth(), 2) + Math.pow(getHeight(), 2)) * (1.0 / Math.sqrt(Math.pow(image.getWidth(), 2) + Math.pow(image.getHeight(), 2)) * distance);
        double B = (getHeight() * 3) / g;
        int drawScale = getHeight() * (getHeight() / 80);
        int drawHeight = (int) B;
        for (int j = 0; j < getWidth() / (scanLines / resolution); j++) {
            int txX = (int) ((resolution * i) + j);
            drawWallLine(txX, drawHeight, (int) x, (int) y, distance);
            drawFloorLine((int) x, (int) y, distance, rotation);
        }
    }

    private void drawFloorLine(int x, int y, double distance, double rotation) {
        int playerX = player.getX();
        int playerY = player.getY();
        int playerHeight = 200;
        for (int i = 0; i < distance; i++) {
            int drawX = (int) (x + (Math.sin(Math.toRadians(rotation-90)) * i));
            int drawY = (int) (y + (Math.cos(Math.toRadians(rotation-90)) * i));
            if (drawX < image.getWidth() && drawX > 0 && drawY < image.getHeight() && drawY > 0) {
                Color color = image.getColorAt(drawX, drawY);
                int floorHeight = (int) (getHeight() - (getHeight() / distance * i));
                int ceilingHeight = (int) (getHeight() / distance * i);
                int floorColor = (int) (255 - (255 / distance * i));
                int ceilingColor = (int) (255 - (255 / distance * i));
                background.setColor(new Color(floorColor, floorColor, floorColor));
                background.drawLine((int) (getWidth() / 2.0 + (drawX - playerX)), floorHeight, (int) (getWidth() / 2.0 + (drawX - playerX)), getHeight());
                background.setColor(new Color(ceilingColor, ceilingColor, ceilingColor));
                background.drawLine((int) (getWidth() / 2.0 + (drawX - playerX)), 0, (int) (getWidth() / 2.0 + (drawX - playerX)), ceilingHeight);
            }
        }
    }

    private void drawWallLine(int x, int height, int dx, int dy, double distance) {
        for (int i = 0; i < height; i++) {
            int y = (getHeight() / 2) - (height / 2) + i;
            if (y <= 0 || y >= getHeight() || x <= 0 || x >= getWidth()) {
                continue;
            }
            double scale = (double) image.getWidth() / (double) wallTexture.getWidth();
            int verticalShadow = (int) (255 * (i / (height * 1.0)));
            double distanceShadow = (distance / (image.getWidth() / 2.0) * 2.0);
            Color textureColor = wallTexture.getColorAt((int) (((dx % wallTexture.getWidth()) + (dy % wallTexture.getHeight())) * scale) % wallTexture.getWidth(), i * wallTexture.getHeight() / height % wallTexture.getHeight());
            int verticalShadowIntensity = 3;
            int distanceShadowIntensity = 3;
            int textureColorIntensity = 4;
            int red = (int) (textureColor.getRed() - textureColor.getRed() * distanceShadow);
            int green = (int) (textureColor.getGreen() - textureColor.getGreen() * distanceShadow);
            int blue = (int) (textureColor.getBlue() - textureColor.getBlue() * distanceShadow);
            Color color = new Color(red, green, blue);
            background.setColorAt(x, y, color);
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
