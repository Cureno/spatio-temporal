package com.blexven.spatio_temporal.spatial;

public class Point implements Comparable<Point> {

    private Double x;
    private Double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDefined() {
        return x != null && y != null;
    }

    @Override
    public int compareTo(Point o) {

        if (this.x < o.x || (this.x.equals(o.x) && this.y < o.y)) {
            return -1;
        }
        else if (this.x.equals(o.x) && this.y.equals(o.y)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {


        return "Point@" +
               Integer.toHexString(hashCode()) + " " +
               "{" +
               "x=" + x +
               ", y=" + y +
               '}';
    }
}
