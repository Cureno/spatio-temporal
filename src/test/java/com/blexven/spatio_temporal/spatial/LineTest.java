package com.blexven.spatio_temporal.spatial;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineTest {


    @Test
    public void sortHalfSegments() {

        HalfSegment smallerLeft = new HalfSegment(new Point(1, 3), new Point(2, 2), HalfSegment.DominatingPoint.LEFT);
        HalfSegment smallerRight = new HalfSegment(new Point(1, 3), new Point(2, 2), HalfSegment.DominatingPoint.RIGHT);

        HalfSegment biggerLeft = new HalfSegment(new Point(4, 3), new Point(4, 4), HalfSegment.DominatingPoint.LEFT);
        HalfSegment biggerRight = new HalfSegment(new Point(4, 3), new Point(4, 4), HalfSegment.DominatingPoint.RIGHT);


        HalfSegment[] halfSegments = {
                biggerLeft,
                biggerRight,
                smallerRight,
                smallerLeft
        };


        Line.sortHalfSegments(halfSegments);

        assertEquals(smallerLeft, halfSegments[0]);
        assertEquals(smallerRight, halfSegments[1]);
        assertEquals(biggerLeft, halfSegments[2]);
        assertEquals(biggerRight, halfSegments[3]);

    }

    @Test
    public void fromSegments() {

        Segment smaller = new Segment(new Point(1, 3), new Point(2, 2));
        Segment bigger = new Segment(new Point(5, 3), new Point(5, 7));

        Line line = Line.fromSegments(new Segment[]{smaller, bigger});

//        assertEquals(
//                new HalfSegment(new Point(1, 3), new Point(2, 2), HalfSegment.DominatingPoint.LEFT)
//                , line.array[0]
//        );

        // todo take another look at this, consider implementing @equals
        assertTrue(
                new HalfSegment(new Point(1, 3), new Point(2, 2), HalfSegment.DominatingPoint.LEFT)
                        .compareTo(line.array[0]) == 0);

    }
}