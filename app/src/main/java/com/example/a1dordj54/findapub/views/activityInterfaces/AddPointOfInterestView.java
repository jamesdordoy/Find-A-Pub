package com.example.a1dordj54.findapub.views.activityInterfaces;

import android.content.Intent;
import android.widget.EditText;

import com.example.a1dordj54.findapub.views.View;

/**
 * Created by 1dordj54 on 05/10/2017.
 */

public interface AddPointOfInterestView extends View {

    void finishIntent(Intent intent);
    void displaySnackbar(String txt);
    EditText getPubName();
    EditText getPubType();
    EditText getPubCountry();
    EditText getPubRegion();
    EditText getLatitude();
    EditText getLongitude();
    EditText getPubDescription();
}
