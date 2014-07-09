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
    @Test(expected=Error.class)
    public void testLatLonBounds() throws Exception {
        BoundingBox bb = new GeoPoint(100,200).getHashedBox(10);
        bb = new GeoPoint(100,50).getHashedBox(10);
        bb = new GeoPoint(50,200).getHashedBox(10);
    }
    @Test
    public void testControlDigit() throws Exception {
        BoundingBox bb = new GeoPoint(51,1).getHashedBox(10);
        assert bb.getHash().charAt(0) == '1';
        bb = new GeoPoint(-40,1).getHashedBox(0);
        assert bb.getHash().charAt(0) == '1';
    }
    @Test
    public void testPrecisionValues() throws Exception{
        BoundingBox bb = new GeoPoint(-21,20).getHashedBox(10);
        assert bb.getPrecision()==10;
    }
    @Test
    public void testPrecisionStress() throws Exception{
        BoundingBox bb = new GeoPoint(39.9259500,-75.1196200).getHashedBox(22);
        bb.printGeoJSON();
    }
}