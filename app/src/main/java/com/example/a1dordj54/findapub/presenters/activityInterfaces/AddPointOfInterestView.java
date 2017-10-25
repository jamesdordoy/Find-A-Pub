package com.example.a1dordj54.findapub.presenters.activityInterfaces;

import android.view.ViewGroup;
import android.widget.EditText;

import com.example.a1dordj54.findapub.presenters.View;

/**
 * Created by 1dordj54 on 05/10/2017.
 */

public interface AddPointOfInterestView {

    void displaySnackbar(String txt);
    ViewGroup getLayout();
    EditText getPubName();
    EditText getPubType();
    EditText getPubCountry();
    EditText getPubRegion();
    EditText getLatitude();
    EditText getLongitude();
    EditText getPubDescription();
}
