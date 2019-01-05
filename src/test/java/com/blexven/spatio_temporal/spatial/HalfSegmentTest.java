package com.blexven.spatio_temporal.spatial;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HalfSegmentTest {

    @Test
    public void compareTo_smaller_LEFT_vs_bigger_LEFT() {

        HalfSegment smaller_LEFT = new HalfSegment(
                new Point(4, 10), new Point(12, 12), HalfSegment.DominatingPoint.LEFT
        );

        HalfSegment bigger_LEFT = new HalfSegment(
                new Point(10, 10), new Point(12, 12), HalfSegment.DominatingPoint.LEFT
        );


        assertTrue(
                "lexicographic order doesn't hold: \n" +
                smaller_LEFT + " should be smaller than " + bigger_LEFT
                ,
                smaller_LEFT.compareTo(bigger_LEFT) < 0
        );

    }

    @Test
    public void compareTo_smaller_RIGHT_vs_bigger_LEFT() {

        HalfSegment smaller_RIGHT = new HalfSegment(
                new Point(4, 10), new Point(12, 12), HalfSegment.DominatingPoint.RIGHT
        );

        HalfSegment bigger_LEFT = new HalfSegment(
                new Point(10, 10), new Point(12, 12), HalfSegment.DominatingPoint.LEFT
        );


        assertTrue(
                "lexicographic order doesn't hold: \n" +
                smaller_RIGHT + " should be smaller than " + bigger_LEFT
                ,
                smaller_RIGHT.compareTo(bigger_LEFT) < 0
        );

    }

    @Test
    public void compareTo_same_LEFT_vs_same_RIGHT() {

        HalfSegment same_LEFT = new HalfSegment(
                new Point(4, 10), new Point(12, 12), HalfSegment.DominatingPoint.LEFT
        );

        HalfSegment same_RIGHT = new HalfSegment(
                new Point(4, 10), new Point(12, 12), HalfSegment.DominatingPoint.RIGHT
        );

        assertTrue(
                "lexicographic order doesn't hold: \n" +
                same_LEFT + " should be smaller than " + same_RIGHT
                ,
                same_LEFT.compareTo(same_RIGHT) < 0
        );


        assertFalse(
                "lexicographic order doesn't hold: \n" +
                same_LEFT + " should be smaller than " + same_RIGHT
                ,
                same_RIGHT.compareTo(same_LEFT) < 0
        );

    }
}