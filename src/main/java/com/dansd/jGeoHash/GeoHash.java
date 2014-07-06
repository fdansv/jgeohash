package com.dansd.jGeoHash;

public class GeoHash {
	GeoPoint thePoint; 
	public GeoHash(double lat,double lon,int pre){
		thePoint = new GeoPoint(lat,lon,pre);
	}
	public static void main(String[] args){
//		GeoPoint point=new GeoPoint(65,-144,12);
//		String strHash = point.theHash;
//		System.out.println(strHash);
//		BoundingBox bb = new BoundingBox("000001101000011111000010");
//		System.out.println(bb.n+" "+bb.w);
//		for(String bobo : bb.surroundingBoxesList()){
//			BoundingBox baba = new BoundingBox(bobo);
//			System.out.println(baba.n+" "+baba.w);
//		}
	}
	
}
