package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;
import android.location.LocationManager;
import android.util.AttributeSet;

import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.Mappable;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.OnMapTouched;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class OpenStreetMap extends Map implements Mappable{

    public static final String OSMAP_ID = "open_street_map";
    public static final int MAP_ZOOM = 8;

    private MapMarkerOverlay mapMarkers;
    private ArrayList<Pub> pubs;
    private MapLocationListener listener;
    private OnMapSetUp callback;

    public interface OnMapSetUp{
        void complete();
    }

    public OpenStreetMap(Context context, AttributeSet attrs) {

        super(context, attrs);
        super.setBuiltInZoomControls(true);

        this.callback = (OnMapSetUp) context;

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
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.callback.complete();
    }

    @Override
    public void setView(GeoPoint geopoint){
        super.getController().setCenter(geopoint);
    }

    @Override
    public void setZoom(int zoom){
        super.getController().setZoom(zoom);
    }


    public void addMarkers(ArrayList<Pub> pois){

        if(pois != null) {

            if(this.mapMarkers.addItems(pois)){

                super.getOverlays().add(this.mapMarkers);
            }
        }
    }

    @Override
    public void addMarker(OverlayItem poi){

        if(poi != null) {

            if(this.mapMarkers.addItem(poi)){

                super.getOverlays().add(this.mapMarkers);
            }
        }
    }

    public OverlayItem removeMarker(Pub pub){

        for(int i = 0; i < this.mapMarkers.size(); i++){

            if(((Pub) this.mapMarkers.getItem(i)).getId().equals(pub.getId())){

                return mapMarkers.removeItem(i);
            }
        }


        return null;
    }

    public void setUpOverlays(ArrayList<Pub> pubs, MapTouchListener listener){

        invalidate();
        getOverlays().clear();

        getOverlays().add(new MapMarkerOverlay(super.getContext(), pubs));
        getOverlays().add(listener);


    }

    public ArrayList<Pub> getPubs(){
        return this.pubs;
    }
    public void setPubs(ArrayList<Pub> poi){ this.pubs = poi; }
    public MapLocationListener getListener() { return listener; }
}