package com.example.a1dordj54.findapub.views.fragmentInterfaces;

import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.views.View;

public interface MapFragmentView extends View {

    OpenStreetMap getMap();

    void displaySnackbar(String txt);
}
