package com.example.a1dordj54.findapub.OpenStreetMaps.interfaces;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public interface Mappable {

    void addMarker(OverlayItem poi);
    void setView(GeoPoint geopoint);
    void setZoom(int zoom);
}
