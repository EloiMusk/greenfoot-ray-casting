package util;

public class GeoVector {
    public double x;
    public double y;
    public double z;

    public GeoVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GeoVector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public GeoVector(GeoVector v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public GeoVector add(GeoVector v) {
        return new GeoVector(x + v.x, y + v.y, z + v.z);
    }

    public GeoVector subtract(GeoVector v) {
        return new GeoVector(x - v.x, y - v.y, z - v.z);
    }

    public GeoVector multiply(double d) {
        return new GeoVector(x * d, y * d, z * d);
    }

    public GeoVector divide(double d) {
        return new GeoVector(x / d, y / d, z / d);
    }

    public double dot(GeoVector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public GeoVector cross(GeoVector v) {
        return new GeoVector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public GeoVector normalize() {
        double m = magnitude();
        return new GeoVector(x / m, y / m, z / m);
    }

    public GeoVector rotateX(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new GeoVector(x, y * cos - z * sin, y * sin + z * cos);
    }

    public GeoVector rotateY(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new GeoVector(x * cos + z * sin, y, -x * sin + z * cos);
    }

    public GeoVector rotateZ(double angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        return new GeoVector(x * cos - y * sin, x * sin + y * cos, z);
    }

    public Location getLocationFromX(double x) {
        return new Location((int) x, (int) ((x - this.x) * (y / x) + this.y), (int) ((x - this.x) * (z / x) + this.z));
    }
}
