package com.blexven.spatio_temporal.spatial;

import java.util.Objects;

public class Segment {
    private final Point left;
    private final Point right;

    public Segment(Point left, Point right) {
        this.left = Objects.requireNonNull(left);
        this.right = Objects.requireNonNull(right);
    }

    public Point getLeft() {
        return left;
    }

    public Point getRight() {
        return right;
    }
}
