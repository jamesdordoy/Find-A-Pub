package com.example.a1dordj54.findapub.models.OpenStreetMaps;

import android.content.Context;
import android.util.AttributeSet;

import com.example.a1dordj54.findapub.models.PointofInterest;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

public abstract class Map extends MapView {

    public Map(Context context, AttributeSet attrs) {

        super(context, attrs);
        super.setBuiltInZoomControls(true);
    }

    public abstract void addMarker(PointofInterest poi, Boolean toogleMessage);
    public abstract void setView(GeoPoint geopoint);
    public abstract void setZoom(int zoom);
    public abstract void addTouchController(Overlay items);
}
