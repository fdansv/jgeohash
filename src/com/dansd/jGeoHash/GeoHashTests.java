package com.dansd.jGeoHash;

public class GeoHashTests {
    public static void main(String[] args){
        GeoPoint point = new GeoPoint(51.52153, -0.1274, 10);
        System.out.println(point.getHash());
        BoundingBox bb = new BoundingBox(point.getHash());
        System.out.println(bb.w + " " + bb.e + " " + bb.n + " " + bb.s);
    }
}
