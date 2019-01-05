package com.blexven.spatio_temporal.spatial;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PointTest {

    @Test
    public void compareTo_different_X() {

        Point smallerX = new Point(3, 10);
        Point biggerX = new Point(4, 20);

        Point[] unordered = {biggerX, smallerX};

        Arrays.sort(unordered);

        assertEquals(unordered[0], smallerX);
    }

    @Test
    public void compareTo_same_X_bigger_Y() {

        Point smallerY = new Point(3, 9);
        Point biggerY = new Point(3, 44);

        Point[] unordered = {biggerY, smallerY};

        Arrays.sort(unordered);

        Point[] nowOrdered = unordered;

        assertEquals(nowOrdered[0], smallerY);
    }

    @Test
    public void compareTo_with_same_X_and_Y_the_order_doesnt_change() {

        Point smallerY = new Point(6, 7);
        Point biggerY = new Point(6, 7);

        Point[] unordered = {smallerY, biggerY};

        Arrays.sort(unordered);

        Point[] nowOrdered = unordered;

        assertEquals(nowOrdered[0], smallerY);
    }

}