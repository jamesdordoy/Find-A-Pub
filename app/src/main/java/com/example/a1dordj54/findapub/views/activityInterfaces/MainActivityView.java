package com.example.a1dordj54.findapub.views.activityInterfaces;

import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.Mappable;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.View;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;

import org.osmdroid.util.GeoPoint;


public interface MainActivityView extends View{

    //View Methods
    void displayToast(String txt);
    void displaySnackbar(String txt);
    void displayPubAlert(Pub pub);

    //Getters
    MapFragment getMapFragment();
    MapListFragment getListFragment();
    Mappable getMap();

    void setMapPositionAndZoom(GeoPoint point, int zoom);
}
