package com.dansd.jGeoHash;

public class GeoPoint {
	private double pointLat;
	private double pointLon;
    private String theHash;
	private int precision;

	public GeoPoint(double lat,double lon, int prec){
		if(lat>90 || lat<-90){
			throw new Error("Latitude must be between -90 and 90 (got "+lat+" instead)");
		}
		if(lon>180 || lon<-180){
			throw new Error("Longitude must be between -180 and 180 (got "+lon+" instead)");
		}
		pointLat= lat;
		pointLon= lon;
		precision = prec;
		hash();
	}


    public String getHash() {
        return theHash;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
        hash();
    }

    private void hash(){
		int i = 0;
		float northLimit = 90;
		float eastLimit = 180;
		float southLimit = -90;
		float westLimit = -180;
		String hashString = "";
		while(i<precision){
			if (pointLon<(eastLimit+westLimit)/2){
				
				hashString=hashString+"0";
				eastLimit = (eastLimit+westLimit)/2;
			}
			else{
				hashString=hashString+"1";
				westLimit = (eastLimit+westLimit)/2;
			}
			if (pointLat<(northLimit+southLimit)/2){
				
				hashString=hashString+"1";
				northLimit = (northLimit+southLimit)/2;
			}
			else{
				hashString=hashString+"0";
				southLimit = (northLimit+southLimit)/2;
			}
			i++;
		}
		theHash = hashString;
	}

	
}
