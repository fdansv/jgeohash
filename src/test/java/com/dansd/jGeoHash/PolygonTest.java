package com.dansd.jGeoHash;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;


public class PolygonTest {
    JSONObject object;
    Polygon polygon;

    private boolean testJSON;

    @Before
    public void initPolygon(){
        object = new JSONObject(file2String("london.json"));
        polygon = new Polygon(object);
    }

    @Test
    public void testJSON(){
        if(testJSON) {
            polygon.printGeoJSON();
        }
    }

    @Test
    public void checkBoundingBox() throws Exception{
        assert polygon.getNWCorner().getLongitude()<polygon.getSECorner().getLongitude();
    }

    @Test
    public void checkContains(){
        assert polygon.isPointInside(new GeoPoint(51.497, -0.109));
        assert !polygon.isPointInside(new GeoPoint(51.74560546875, -0.5712890625));
        assert !polygon.isPointInside(new GeoPoint(20, 5));
    }
    @Test
    public void displayHashedShapeJSON(){
        Polygon polygon = new Polygon(object);
    }

    private String file2String(String path){
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded);
    }
}