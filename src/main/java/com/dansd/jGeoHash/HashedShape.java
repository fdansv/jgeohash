package com.dansd.jGeoHash;

import java.util.ArrayList;
import java.util.SortedSet;


public class HashedShape extends ArrayList<BoundingBox>{

    private final Polygon originPolygon;
    private int precision;
    private SortedSet<BoundingBox> boundingMesh;

    public HashedShape(Polygon polygon, int precision){
        originPolygon = polygon;
        this.precision = precision;
        genBoundingMesh();
    }

    private void genBoundingMesh(){
        BoundingBox nwBox = originPolygon.getNWCorner().getHashedBox(precision);
        double boxWidth = nwBox.getWidth();
        double boxHeight = nwBox.getHeight();
        BoundingBox neBox = originPolygon.getNECorner().getHashedBox(precision);
        double backToBackRowDistance = Math.abs(nwBox.getWest() - neBox.getEast());
        BoundingBox swBox = originPolygon.getSWCorner().getHashedBox(precision);
        double backToBackColDistance = Math.abs(nwBox.getNorth() - swBox.getSouth());
        int rowSizeinBoxes = (int) (backToBackRowDistance/boxWidth);
        int colSizeinBoxes = (int) (backToBackColDistance/boxHeight);
        assert rowSizeinBoxes>=1;
        assert colSizeinBoxes>=1;
        for (int i = 0; i<rowSizeinBoxes; i++){
            double queryLon = nwBox.getWest()+(boxWidth/2)+(i*boxWidth);
            for (int j = 0; j<colSizeinBoxes; j++){
                double queryLat = nwBox.getNorth()-(boxHeight/2)-(j*boxHeight);
                GeoPoint queryPoint = new GeoPoint(queryLat, queryLon);
                if(originPolygon.isPointInside(queryPoint)){
                    this.add(queryPoint.getHashedBox(precision));
                }
            }
        }

    }

    public boolean contains(HashedShape subHashedShape){
        return this.containsAll(subHashedShape);
    }

    public boolean contains(BoundingBox boundingBox){
        return super.contains(boundingBox);
    }

}
