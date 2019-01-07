package com.blexven.spatio_temporal.spatial;

import java.util.Objects;

public class Segment {
    private final Point left;
    private final Point right;

    /**
     * Instantiates a Segment.
     *
     * @param left  the lexicographically smaller Point
     * @param right the lexicographically greater Point
     * @throws RuntimeException if the left point is not smaller than the right.
     */
    public Segment(Point left, Point right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
        Segment.validatePoints(left, right);
    }

    /**
     * @param left  the lexicographically smaller Point
     * @param right the lexicographically greater Point
     * @throws RuntimeException if the left point is not smaller than the right.
     */
    public static void validatePoints(Point left, Point right) throws RuntimeException {

        if (left.compareTo(right) > -1) {
            throw new RuntimeException(
                    "the left point should be smaller than the right.\n" +
                    "left : " + left + "\n" +
                    "right : " + right);
        }

    }

    public Point getLeft() {
        return left;
    }

    public Point getRight() {
        return right;
    }
}
