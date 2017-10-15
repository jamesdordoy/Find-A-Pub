package com.example.a1dordj54.findapub.views.fragmentInterfaces;

import android.view.ViewGroup;

import com.example.a1dordj54.findapub.models.OpenStreetMaps.OpenStreetMap;

/**
 * Created by 1dordj54 on 05/10/2017.
 */

public interface MapFragmentView {

    ViewGroup getLayout();

    void getMarkersFromWeb();
    OpenStreetMap getMap();
}
