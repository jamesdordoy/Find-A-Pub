package com.example.a1dordj54.findapub.presenters;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a1dordj54.findapub.OpenStreetMaps.*;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.Mapable;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.views.activities.AddPointOfInterestActivity;
import com.example.a1dordj54.findapub.presenters.fragmentInterfaces.MapFragmentView;
import com.example.a1dordj54.findapub.views.activities.MainActivity;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.webservices.async.AsyncGetPubsFromWebService;
import com.example.a1dordj54.findapub.webservices.PointofInterestURLS;

import org.osmdroid.util.GeoPoint;

public class MapPresenter implements View.OnClickListener, Mapable {

    private MapFragmentView view;

    public MapPresenter(MapFragmentView fragment) {
        this.view = fragment;

        //Set Default Zoom
        this.view.getMap().setZoom(OpenStreetMap.MAP_ZOOM);

        //Restore Saved Instance State of Any Pubs
        this.view.getMap().addMarkers(StateManager.getInstance().getPointsofInterests());
    }

    @Override
    public void onClick(View v) {

        MapLocationListener listener = this.view.getMap().getListener();

        GeoPoint geoPoint = listener.getLastKnownLoc();

        startAddActivity(geoPoint);
    }

    @Override
    public void mapTouched(GeoPoint geoPoint) {
        startAddActivity(geoPoint);
    }

    private void startAddActivity(GeoPoint geoPoint){

        if(geoPoint != null){

            MainActivity activity = (MainActivity) ((Fragment) this.view).getActivity();

            Intent intent = new Intent(activity, AddPointOfInterestActivity.class);

            Bundle bundle = new Bundle();

            bundle.putDouble("LAT", geoPoint.getLatitude());
            bundle.putDouble("LON", geoPoint.getLongitude());

            intent.putExtras(bundle);

            ((Fragment) this.view).getActivity().startActivityForResult(intent, 0);
        }
    }

    @Override
    public OpenStreetMap getMap() {
        return this.view.getMap();
    }

    public MapFragment getView() {
        return (MapFragment) view;
    }

}