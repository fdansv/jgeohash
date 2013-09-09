package com.dansd.jGeoHash;

public class GeoHashTests {
    public int main(String[] args){
        GeoPoint point = new GeoPoint(51.52153, -0.1274, 10);
        System.out.println(point.getHash());
        return 0;
    }
}
