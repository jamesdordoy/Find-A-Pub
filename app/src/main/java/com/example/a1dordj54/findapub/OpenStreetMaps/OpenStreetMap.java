package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;
import android.location.LocationManager;
import android.util.AttributeSet;

import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.models.Pub;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

public class OpenStreetMap extends Map {

    public static final String OSMAP_ID = "open_street_map";
    public static final int MAP_ZOOM = 8;

    private MapMarkerOverlay mapMarkers;
    private ArrayList<Pub> pubs;
    private MapLocationListener listener;

    public OpenStreetMap(Context context, AttributeSet attrs) {

        super(context, attrs);
        super.setBuiltInZoomControls(true);

        setUpMap(new ArrayList<Pub>());
    }

    private void setUpMap(ArrayList<Pub> pubs){
        this.pubs = pubs;

        // Set up location manager
        LocationManager locManager = (LocationManager)
                super.getContext().getSystemService(Context.LOCATION_SERVICE);

        this.listener = new MapLocationListener(locManager, super.getContext(), this);

        //the overlay
        this.mapMarkers = new MapMarkerOverlay(super.getContext(), this.pubs);

        this.getOverlays().add(mapMarkers);
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

    public void addMarkers(ArrayList<Pub> pois){

        if(pois != null) {

            this.mapMarkers.addItems(pois);

            super.invalidate();
            super.getOverlays().clear();
            super.getOverlays().add(this.mapMarkers);
        }
    }

    @Override
    public void addMarker(OverlayItem poi){

        if(poi != null) {

            if(this.mapMarkers.addItem(poi)){

                super.getOverlays().add(this.mapMarkers);
                super.invalidate();
            }
        }
    }

    public ArrayList<Pub> getPubs(){
        return this.pubs;
    }
    public void setPubs(ArrayList<Pub> poi){

        this.pubs = poi;

        this.mapMarkers = new MapMarkerOverlay(super.getContext(), this.pubs);

        super.getOverlays().clear();
        this.getOverlays().add(mapMarkers);
    }
    public MapLocationListener getListener() { return listener; }
}