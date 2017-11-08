package com.example.a1dordj54.findapub.presenters;

import android.view.View;

import com.example.a1dordj54.findapub.OpenStreetMaps.*;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;
import com.example.a1dordj54.findapub.views.activities.MainActivity;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;

import org.osmdroid.util.GeoPoint;

public class MapPresenter implements Presenter, View.OnClickListener {

    private MapFragmentView view;

    public MapPresenter(MapFragmentView fragment) {
        this.view = fragment;

        //Set Default Zoom
        this.view.getMap().setZoom(OpenStreetMap.MAP_ZOOM);
    }

    @Override
    public void onClick(View v) {

        MapLocationListener listener = this.view.getMap().getListener();

        GeoPoint geoPoint = listener.getLastKnownLoc();

        startAddActivity(geoPoint);
    }

    private void startAddActivity(GeoPoint geoPoint){
        ((MainActivity) ((MapFragment) this.view).getActivity()).mapTouched(geoPoint);
    }

    public OpenStreetMap getMap() {
        return this.view.getMap();
    }

    public MapFragment getView() {
        return (MapFragment) view;
    }

}