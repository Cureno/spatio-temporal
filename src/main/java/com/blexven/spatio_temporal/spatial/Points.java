package com.blexven.spatio_temporal.spatial;

import java.util.Arrays;

/**
 * A finite set of points in the plain.
 */
public class Points extends RootRecord<Point> {
    public Points(Point[] points) {
        super(points);
        Arrays.sort(points);
    }

    /**
     * @return an array of points in an (x,y)-lexicographic order
     */
    Point[] getPoints() {
        return this.array;
    }

}