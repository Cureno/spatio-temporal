package com.blexven.spatio_temporal.spatial;

import java.util.Objects;

import static com.blexven.spatio_temporal.spatial.HalfSegment.DominatingPoint.LEFT;
import static com.blexven.spatio_temporal.spatial.HalfSegment.DominatingPoint.RIGHT;

public class HalfSegment extends Segment implements Comparable<HalfSegment> {

    private final DominatingPoint dominatingPoint;

    public HalfSegment(Point left, Point right, DominatingPoint dominatingPoint) {
        super(left, right);
        this.dominatingPoint = Objects.requireNonNull(dominatingPoint);
    }

    public DominatingPoint getDominatingPoint() {
        return dominatingPoint;
    }

    @Override
    public int compareTo(HalfSegment o) {

        int leftPointsCompared = this.getLeft().compareTo(o.getLeft());

        if (leftPointsCompared != 0) {
            return leftPointsCompared;
        }
        else {
            int rightPointsCompared = this.getRight().compareTo(o.getRight());

            if (rightPointsCompared != 0) {
                return rightPointsCompared;
            }
        }


        if (this.dominatingPoint.equals(LEFT) &&
            o.dominatingPoint.equals(RIGHT)
        ) {
            return -1;
        }
        else if (this.dominatingPoint.equals(RIGHT) &&
                 o.dominatingPoint.equals(LEFT)
        ) {
            return 1;
        }

        return 0;

        // todo add angle criterion to treat halfSegments with the same dominating point
    }

    @Override
    public String toString() {
        return "HalfSegment{" +
               "left=" + getLeft().getX() +
               ", right=" + getRight().getY() +
               ", dominatingPoint=" + dominatingPoint +
               '}';
    }

    enum DominatingPoint {
        LEFT, RIGHT
    }

}
