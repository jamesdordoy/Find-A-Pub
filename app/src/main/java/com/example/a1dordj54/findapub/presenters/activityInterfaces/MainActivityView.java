package com.example.a1dordj54.findapub.presenters.activityInterfaces;

import android.view.ViewGroup;

import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;


public interface MainActivityView {

    //View Methods
    void displayToast(String txt);
    void displaySnackbar(String txt);
    void displayAlert(String title, String message);
    ViewGroup getLayout();

    //Getters
    MapFragment getMapFragment();
    MapListFragment getListFragment();
}
