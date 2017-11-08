package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.OnMapTouched;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.Overlay;

public class MapTouchListener extends Overlay {

    private OnMapTouched callback;

    public MapTouchListener(OnMapTouched ctx) {
        this.callback =  ctx;
    }

    @Override
    public void draw(Canvas arg0, MapView arg1, boolean arg2) {}





    @Override
    public boolean onSingleTapConfirmed(final MotionEvent e, final MapView mapView) {

        Projection projection = mapView.getProjection();
        GeoPoint loc = (GeoPoint) projection.fromPixels((int)e.getX(), (int)e.getY());


        callback.mapTouched(new GeoPoint(loc.getLatitude(), loc.getLongitude()));

        return true;
    }
}
