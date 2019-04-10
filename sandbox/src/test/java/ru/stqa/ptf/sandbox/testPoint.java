package ru.stqa.ptf.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class testPoint {

    @Test
    public void testDistanceValid() {
        Point p1 = new Point(1,2);
        Point p2 = new Point(4,4);

        Assert.assertEquals(p1.distance(p2), 3.605551275463989);
    }

    @Test
    public void testDistanceInvalid() {
        Point p1 = new Point(99.0,11.0);
        Point p2 = new Point(4,4);

        Assert.assertEquals(p1.distance(p2), 95.25754563287887);
    }
}

