package com.example.a1dordj54.findapub.helpers;

import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.models.Pub;

import java.util.ArrayList;

public class StateManager {

    private ArrayList<Pub> pointsofInterests = new ArrayList<>();

    private Double lat = 0.00;
    private Double lon = 0.00;

    //Singleton Atts & Methods
    private static final StateManager ourInstance = new StateManager();

    public static StateManager getInstance() {
        return ourInstance;
    }

    //Private Constructor
    private StateManager() {}

    //Back To Implementation
    public void addPointOfInterest(Pub poi){
        this.pointsofInterests.add(poi);
    }

    public void addPointOfInterests(ArrayList<Pub> pointsofInterests) {

        this.pointsofInterests.addAll(pointsofInterests);
    }

    public void removePointOfInterest(Pub poi){
        for (Pub pub : this.pointsofInterests ) {
            if(pub.getId().equals(poi.getId())){
                this.pointsofInterests.remove(poi);
            }
        }
    }

    public Boolean checkPointOfInterestExists(Pub poi){
        for (Pub pub : this.pointsofInterests ) {
            if(pub.getName().equals(poi.getName()) && poi.getLat() == pub.getLat() && poi.getLon() == pub.getLon()){
                return true;
            }
        }

        return false;
    }

    public ArrayList<Pub> getPointsofInterests() {
        return pointsofInterests;
    }


    public PointofInterest getPointOfInterest(int i){
        return this.pointsofInterests.get(i);
    }

    public void setPointsofInterests(ArrayList<Pub> pointsofInterests) {
        this.pointsofInterests = pointsofInterests;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
