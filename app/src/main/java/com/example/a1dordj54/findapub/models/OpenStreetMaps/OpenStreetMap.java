package com.example.a1dordj54.findapub.models.OpenStreetMaps;

import android.content.Context;
import android.location.LocationManager;
import android.util.AttributeSet;

import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.PointofInterest;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class OpenStreetMap extends Map {

    public static final String OSMAP_ID = "open_street_map";

    private MapMarkerOverlay mapMarkers;
    private ArrayList<PointofInterest> pointsOfInterest;
    private MapLocationListener listener;

    public OpenStreetMap(Context context, AttributeSet attrs) {

        super(context, attrs);
        super.setBuiltInZoomControls(true);

        this.pointsOfInterest = new ArrayList<>();

        //the overlay
        this.mapMarkers = new MapMarkerOverlay(super.getContext(), this.pointsOfInterest);

        this.getOverlays().add(mapMarkers);

        // Set up location manager
        LocationManager locManager = (LocationManager)
                super.getContext().getSystemService(Context.LOCATION_SERVICE);

        this.listener = new MapLocationListener(locManager, super.getContext(), this);

        for(int i = 0; i < StateManager.getInstance().getPointsofInterests().size(); i++){
            this.addMarker(StateManager.getInstance().getPointOfInterest(i), false);
        }
    }

    public void retainCurrentPointsOfInterest(){
        for(int i = 0; i < this.pointsOfInterest.size(); i++){
            StateManager.getInstance().addPointOfInterest(this.pointsOfInterest.get(i));
        }
    }

    @Override
    public void setView(GeoPoint geopoint){
        super.getController().setCenter(geopoint);
    }

    @Override
    public void setZoom(int zoom){
        super.getController().setZoom(zoom);
    }

    @Override
    public void addTouchController(Overlay items){ super.getOverlays().add(items); }

    @Override
    public void addMarker(PointofInterest poi, Boolean toogleMessage){

        if(poi != null) {

            if(this.mapMarkers.addMarker(poi, toogleMessage)){

                this.pointsOfInterest.add(poi);

                super.getOverlays().add(this.mapMarkers);
                super.invalidate();
            }
        }
    }


    public ArrayList<PointofInterest> getPointsOfInterest(){
        return this.pointsOfInterest;
    }
    public void setPointsofInterests(ArrayList<PointofInterest> poi){ this.pointsOfInterest = poi; }
    public MapLocationListener getListener() { return listener; }
}