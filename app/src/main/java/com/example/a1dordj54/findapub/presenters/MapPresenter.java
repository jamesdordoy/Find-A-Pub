package com.example.a1dordj54.findapub.presenters;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a1dordj54.findapub.models.OpenStreetMaps.*;
import com.example.a1dordj54.findapub.models.OpenStreetMaps.interfaces.Mapable;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.views.activities.AddPointOfInterestActivity;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.webservices.GetPOIFromWeb;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

public class MapPresenter implements View.OnClickListener, Mapable {

    private MapFragmentView view;

    public MapPresenter(MapFragmentView fragment) {
        this.view = fragment;

        this.view.getMap().setZoom(14);

    }

    @Override
    public void onClick(View v) {

        MapLocationListener listener = this.view.getMap().getListener();

        GeoPoint loc = listener.getLastKnownLoc();

        if(loc != null){

            Intent intent = new Intent(((Fragment) this.view).getActivity(), AddPointOfInterestActivity.class);

            Bundle bundle = new Bundle();

            bundle.putDouble("LAT", loc.getLatitude());
            bundle.putDouble("LON", loc.getLongitude());

            intent.putExtras(bundle);

            ((Fragment) this.view).getActivity().startActivityForResult(intent, 0);
        }
    }

    @Override
    public void mapTouched(GeoPoint geoPoint) {
        if(geoPoint != null){

            Intent intent = new Intent(((Fragment) this.view).getActivity(), AddPointOfInterestActivity.class);

            Bundle bundle = new Bundle();

            bundle.putDouble("LAT", geoPoint.getLatitude());
            bundle.putDouble("LON", geoPoint.getLongitude());

            intent.putExtras(bundle);

            ((Fragment) this.view).getActivity().startActivityForResult(intent, 0);
        }
    }

    public void getMarkersFromWeb(){
        new GetPOIFromWeb(this).execute("http://192.168.0.12/pointsofinterest/api/PointOfInterest.php?type=pub");
    }


    public MapFragment getView() {
        return (MapFragment) view;
    }

    @Override
    public void addMarker(PointofInterest poi) {
        this.view.getMap().addMarker(poi, false);
    }

    @Override
    public void addMarker(OverlayItem poi) {
        //this.view.getMap().addMarker(poi, false);
    }
}