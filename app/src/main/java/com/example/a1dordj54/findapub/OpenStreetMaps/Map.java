package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;
import android.util.AttributeSet;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

public abstract class Map extends MapView {

    public Map(Context context, AttributeSet attrs) {

        super(context, attrs);
        super.setBuiltInZoomControls(true);
        super.setMultiTouchControls(true);
    }

    public abstract void addMarker(OverlayItem poi);
    public abstract void setView(GeoPoint geopoint);
    public abstract void setZoom(int zoom);
}
