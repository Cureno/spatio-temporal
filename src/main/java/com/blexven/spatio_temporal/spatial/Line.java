package com.blexven.spatio_temporal.spatial;

import java.util.Arrays;

import static com.blexven.spatio_temporal.spatial.HalfSegment.DominatingPoint.LEFT;
import static com.blexven.spatio_temporal.spatial.HalfSegment.DominatingPoint.RIGHT;

/**
 * A finite set of line segments that are intersection-free.
 * Stores each segment twice, once for each dominating point, that is once
 * for the left end point and once for the right end point, to support
 * plane-sweep algorithms.
 */
public class Line extends RootRecord<HalfSegment> {

    /**
     * @param halfSegments are ordered in the array following a lexicographic order extended
     *                 by an angle criterion to treat halfsegments with the same dominating
     *                 point.
     */
    public Line(HalfSegment[] halfSegments) {
        super(halfSegments);
        sortHalfSegments(halfSegments);
    }


    /**
     * From segments constructs a Line instance.
     *
     * Each segment is is stored twice, to support plane-sweep algorithms.
     *
     * @param segments the segments
     * @return the line
     */
    public static Line fromSegments(Segment[] segments) {

        HalfSegment[] halfSegments = new HalfSegment[segments.length * 2];

        for (int i = 0; i < segments.length; i++) {

            Point left = segments[i].getLeft();
            Point right = segments[i].getRight();

            halfSegments[i * 2] = new HalfSegment(left, right, LEFT);
            halfSegments[i * 2 + 1] = new HalfSegment(left, right, RIGHT);

        }

        return new Line(halfSegments);
    }

    public static void sortHalfSegments(HalfSegment[] halfSegments) {
        Arrays.sort(halfSegments);
    }
}
