package com.blexven.spatio_temporal.spatial;

import org.junit.Test;

public class SegmentTest {


    @Test(expected = RuntimeException.class)
    public void validatePoints() {

        Segment.validatePoints(
                new Point(1, 1), new Point(0, 2)
        );

    }

    @Test(expected = RuntimeException.class)
    public void constructor() {
        new Segment(
                new Point(1, 1), new Point(0, 2)
        );
    }
}