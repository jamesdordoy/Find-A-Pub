package com.example.a1dordj54.findapub.OpenStreetMaps.interfaces;

import com.example.a1dordj54.findapub.OpenStreetMaps.Map;
import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public interface Mapable {

    void mapTouched(GeoPoint geoPoint);
    OpenStreetMap getMap();
    MapFragment getView();
}
