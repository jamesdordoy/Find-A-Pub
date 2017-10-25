package com.example.a1dordj54.findapub.presenters.fragmentInterfaces;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.view.ViewGroup;

import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.models.PointofInterest;
import com.example.a1dordj54.findapub.models.Pub;

import java.util.ArrayList;

/**
 * Created by 1dordj54 on 05/10/2017.
 */

public interface MapFragmentView {

    ViewGroup getLayout();
    OpenStreetMap getMap();
    Context getFragmentContext();

    void addMarkers(ArrayList<Pub> pubs);
    void displaySnackbar(String txt);

}
