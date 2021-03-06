package com.dansd.jGeoHash;

import java.math.BigInteger;

public class BoundingBox {
	private double[] coords;
	private double n=90,s=-90,e=180,w=-180;
	private String hash;
    private GeoPoint geoPoint;
    private int precision;


	public BoundingBox(String theHash){
		hash=theHash;
		fromHash();
        calculatePrecision();
	}

    @Override
    public String toString(){
        return String.format("BoundingBox, N: %s, S: %s, E: %s, W: %s, %s, %s", n, s, e, w, precision, getHexHash());
    }

	private void fromHash(){
		String hashCopy = hash.substring(1); // We remove the control 1
		while(!hashCopy.equals("")){
			if(hashCopy.substring(0,1).equals("0")){
				e=(e+w)/2;
			}
			else{
				w=(e+w)/2;
			}
			if(hashCopy.substring(1,2).equals("0")){
				s=(n+s)/2;
			}
			else{
				n=(n+s)/2;
			}
			hashCopy=hashCopy.substring(2,hashCopy.length());
		}
	}

    private void calculatePrecision(){
        precision = (hash.length()-1)/2;
    }

    public int getPrecision() {
        return precision;
    }

	public double getWidth(){
		double width = w-e;
		if(width<0){
			width*=-1;
		}
		return width;
	}
	public double getHeight(){
		double height = n-s;
		if(height<0){
			height*=-1;
		}
		return height;
	}

    public String getHash() {
        return hash;
    }
    public String getHexHash() {
        return new BigInteger(hash, 2).toString(16);
    }


    public double getNorth() {
        return n;
    }

    public double getSouth() {
        return s;
    }

    public double getEast() {
        return e;
    }

    public double getWest() {
        return w;
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
                "}",w,n,e,n,e,s,w,s,w,n);
        System.out.println(geoJSON);
    }

}
