package com.dansd.jGeoHash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Polygon extends ArrayList<GeoPoint>
{
    private java.awt.Polygon primitivePolygon;

    private double southernmostLat = 90, northernmostLat = -90, westernmostLon = 180, easternmostLon = -180;

    public Polygon(JSONObject originObject){
        getCoordinates(originObject);
        genPolygon();
    }

    public HashedShape getHashedShape(int precision){
        return new HashedShape(this, precision);
    }

    public boolean isPointInside(GeoPoint geoPoint){
        return primitivePolygon.contains(geoPoint.getLongitude()*1000, geoPoint.getLatitude()*1000);
    }


    private void getCoordinates(JSONObject originObject) {
        JSONObject feature = (JSONObject) originObject.getJSONArray("features").get(0);
        JSONObject geometry = feature.getJSONObject("geometry");
        JSONArray coordinates = geometry.getJSONArray("coordinates").getJSONArray(0);
        for(int i = 0; i<coordinates.length(); i++){
            JSONArray pair = coordinates.getJSONArray(i);
            double lat = pair.getDouble(1);
            double lon = pair.getDouble(0);
            GeoPoint thisPoint = new GeoPoint(lat, lon);
            checkMaxMin(thisPoint);
            this.add(thisPoint);
        }


    }

    private void genPolygon() {
        primitivePolygon = new java.awt.Polygon(getXCoords(), getYCoords(), this.size());
    }

    private void checkMaxMin(GeoPoint point){
        if(point.getLatitude()<this.southernmostLat){
            this.southernmostLat = point.getLatitude();
        }
        else if(point.getLatitude()>this.northernmostLat){
            this.northernmostLat = point.getLatitude();
        }

        if(point.getLongitude()>this.easternmostLon){
            this.easternmostLon = point.getLongitude();
        }
        else if(point.getLongitude()<this.westernmostLon){
            this.westernmostLon = point.getLongitude();
        }
    }

    public void printGeoJSON(){
        String geoJSON = String.format("{\n" +
                "  \"type\": \"FeatureCollection\",\n" +
                "  \"features\": [\n" +
                "    {\n" +
                "      \"type\": \"Feature\",\n" +
                "      \"properties\": {},\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Polygon\",\n" +
                "        \"coordinates\": [\n" +
                "          [\n" +
                "            [\n" +
                "              %s,\n" +
                "              %s\n" +
                "            ],\n" +
                "            [\n" +
                "             \n" +
                "              %s,\n" +
                "              %s\n" +
                "            ],\n" +
                "            [\n" +
                "              %s,\n" +
                "              %s\n" +
                "            ],\n" +
                "            [\n" +
                "              %s,\n" +
                "              %s\n" +
                "            ],\n" +
                "            [\n" +
                "              %s,\n" +
                "              %s\n" +
                "            ]\n" +
                "          ]\n" +
                "        ]\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}",westernmostLon,northernmostLat,easternmostLon,northernmostLat,easternmostLon,southernmostLat,westernmostLon,southernmostLat,westernmostLon,northernmostLat);
        System.out.println(geoJSON);
    }

    protected int[] getXCoords(){
        int[] xes = new int[this.size()];
        for (int i = 0; i< this.size(); i++){
            xes[i] = ((int) (this.get(i).getLongitude()*1000));
        }
        return xes;
    }
    protected int[] getYCoords(){
        int[] yes = new int[this.size()];
        for (int i = 0; i< this.size(); i++){
            yes[i] = ((int) (this.get(i).getLatitude()*1000));
        }
        return yes;
    }

    public GeoPoint getNWCorner(){
        return new GeoPoint(northernmostLat, westernmostLon);
    }

    public GeoPoint getSECorner(){
        return new GeoPoint(southernmostLat, easternmostLon);
    }

    public GeoPoint getNECorner() { return new GeoPoint(northernmostLat, easternmostLon); }

    public GeoPoint getSWCorner() {
        return new GeoPoint(southernmostLat, westernmostLon);
    }
}
