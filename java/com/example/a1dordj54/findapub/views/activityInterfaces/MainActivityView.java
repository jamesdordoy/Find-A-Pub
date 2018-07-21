package com.example.a1dordj54.findapub.views.activityInterfaces;

import android.view.ViewGroup;

import com.example.a1dordj54.findapub.views.fragments.MapFragment;


public interface MainActivityView {

    void displaySnackbar(String txt);
    ViewGroup getLayout();
    MapFragment getMapFragment();
}
