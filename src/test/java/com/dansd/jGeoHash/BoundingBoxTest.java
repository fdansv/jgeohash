package com.dansd.jGeoHash;

import org.junit.Test;

public class BoundingBoxTest {

    @Test
    public void testGetPrecision() throws Exception {
        BoundingBox bb = new GeoPoint(50,2).getHashedBox(10);
        assert bb.getPrecision()==10;
    }

    @Test
    public void testDimensions() throws Exception {
        BoundingBox bb = new GeoPoint(50,2).getHashedBox(10);
        assert bb.getWidth()>0;
        assert bb.getHeight()>0;
    }
    @Test
    public void testPrecisionZero() throws Exception {
        BoundingBox bb = new GeoPoint(50,2).getHashedBox(0);
        assert bb.getNorth()==90;
        assert bb.getSouth()==-90;
        assert bb.getEast()==180;
        assert bb.getWest()==-180;
    }
}