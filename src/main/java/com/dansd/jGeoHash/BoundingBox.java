package com.dansd.jGeoHash;

public class BoundingBox {
	private double[] coords;
	private double n=90,s=-90,e=-180,w=180;
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
        return String.format("BoundingBox, N: %s, S: %s, E: %s, W: %s, %s", n, s, e, w, precision);
    }

	private void fromHash(){
		String hashCopy = hash;
		while(!hashCopy.equals("")){
			//System.out.println(n +" "+s+" "+e+" "+w);
			if(hashCopy.substring(0,1).equals("0")){
				e=(e+w)/2;
			}
			else{
				//System.out.println(hashCopy.substring(0,1));
				w=(e+w)/2;
			}
			if(hashCopy.substring(1,2).equals("0")){
				s=(n+s)/2;
			}
			else{
				//System.out.println(hashCopy.substring(0,1));
				n=(n+s)/2;
			}
			hashCopy=hashCopy.substring(2,hashCopy.length());
		}
	}

	public ArrayList<String> surroundingBoxesList() {

		ArrayList<String> theList = new ArrayList<String>();
		try {
			String NORTH = new GeoPoint(n + getHeight() / 2, (e + w) / 2,
					precision).getHash();
			theList.add(NORTH);
		} catch (Error e) {
		}
		try {
			String SOUTH = new GeoPoint(s - getHeight() / 2, (e + w) / 2,
					precision).getHash();
			theList.add(SOUTH);
		} catch (Error e) {
		}
		try {
			String NORTHEAST = new GeoPoint(n + getHeight() / 2,
					(e + getWidth()) / 2, precision).getHash();
			theList.add(NORTHEAST);
		} catch (Error e) {
		}
		try {
			String SOUTHEAST = new GeoPoint(s - getHeight() / 2,
					(e - getWidth()) / 2, precision).getHash();
			theList.add(SOUTHEAST);
		} catch (Error e) {
		}
		try {
			String NORTHWEST = new GeoPoint(n + getHeight() / 2,
					(w - getWidth()) / 2, precision).getHash();
			theList.add(NORTHWEST);
		} catch (Error e) {
		}
		try {
			String SOUTHWEST = new GeoPoint(s - getHeight() / 2,
					(w - getWidth()) / 2, precision).getHash();
			theList.add(SOUTHWEST);
		} catch (Error e) {
		}
		try {
			String EAST = new GeoPoint(n + s / 2, e + getWidth() / 2,
					precision).getHash();
			theList.add(EAST);
		} catch (Error e) {
		}
		try {
			String WEST = new GeoPoint(n + s / 2, w - getWidth() / 2,
					precision).getHash();
			theList.add(WEST);
		} catch (Error e) {
		}
		theList.add(hash);
		return theList;
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

    private void calculatePrecision(){
        precision = hash.length()/2;
    }

}
