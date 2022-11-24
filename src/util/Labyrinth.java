package util;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class Labyrinth {

    public static boolean isTouchingLabyrinth(int x, int y, GreenfootImage map, int thr) {
        int threshold;
        if (thr <= 0) {
            threshold = 4;
        }else {
            threshold = thr;
        }
        boolean isTouching = false;
        for (int c = 0; c < threshold; c++) {
            if (!isTouching) {
                for (int r = 0; r < threshold; r++) {
                    Color color = map.getColorAt(x + c, y + r);
                    if (isTouching) {
                        break;
                    }
                    if (color.getAlpha() > 100) {
                        isTouching = true;
                    }
                }
            }
        }
        return isTouching;
    }
    public static boolean isTouchingLabyrinth(int x, int y, GreenfootImage map) {
        boolean isTouching = false;
        for (int c = 0; c < 4; c++) {
            if (!isTouching) {
                for (int r = 0; r < 4; r++) {
                    Color color = map.getColorAt(x + c, y + r);
                    if (isTouching) {
                        break;
                    }
                    if (color.getBlue() > 220 && color.getRed() < 10 && color.getGreen() < 10) {
                        isTouching = true;
                    }
                }
            }
        }
        return isTouching;
    }
}
