package com.dansd.jGeoHash;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HashedShapeTest {
    JSONObject object;
    @Before
    public void initPolygon(){
        object = new JSONObject(file2String("london.json"));
    }

    @Test
    public void geoJSONDisplayTest(){
        Polygon polygon = new Polygon(object);
        HashedShape hs = new HashedShape(polygon, 12);
        assert hs.size()>0;
        System.out.println("[");
        for (BoundingBox bb: hs){
            bb.printGeoJSON();
            System.out.println(",");
        }
        System.out.println("]");

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