package com.dansd.jGeoHash;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Polygon extends ArrayList<GeoPoint>
{
    double southernmostLat = 90, northernmostLat = -90, westernmostLon = 180, easternmostLon = -180;

    public Polygon(JSONObject originObject){
        getCoordinates(originObject);
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
    private void checkMaxMin(GeoPoint point){
        if(point.getLatitude()<this.southernmostLat){
            this.southernmostLat = point.getLatitude();
        }
        else if(point.getLatitude()>this.northernmostLat){
            this.northernmostLat = point.getLatitude();
        }

        if(point.getLongitude()<this.easternmostLon){
            this.easternmostLon = point.getLongitude();
        }
        else if(point.getLatitude()>this.northernmostLat){
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

    public GeoPoint getNWCorner(){
        return new GeoPoint(northernmostLat, westernmostLon);
    }

    public GeoPoint getSECorner(){
        return new GeoPoint(southernmostLat, easternmostLon);
    }

}
