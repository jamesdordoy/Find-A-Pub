package com.example.a1dordj54.findapub.models.OpenStreetMaps.interfaces;

import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public interface Mapable {

    void mapTouched(GeoPoint geoPoint);
    void addMarker(PointofInterest poi);
    void addMarker(OverlayItem poi);
    MapFragment getView();
}
