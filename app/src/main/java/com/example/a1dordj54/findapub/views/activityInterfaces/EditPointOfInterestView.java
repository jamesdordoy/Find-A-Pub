package com.example.a1dordj54.findapub.views.activityInterfaces;

import android.content.Intent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Dordoy on 05/11/2017.
 */

public interface EditPointOfInterestView {

    void finishIntent(Intent intent);
    void displaySnackbar(String txt);
    EditText getPubName();
    EditText getPubType();
    EditText getPubCountry();
    EditText getPubRegion();
    EditText getLatitude();
    EditText getLongitude();
    EditText getPubDescription();
    Button getSubmit();
    ViewGroup getLayout();
}
