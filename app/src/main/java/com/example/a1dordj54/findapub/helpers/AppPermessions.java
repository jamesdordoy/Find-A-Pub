package com.example.a1dordj54.findapub.helpers;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by 1dordj54 on 06/10/2017.
 */

public class AppPermessions {

    public static Boolean allowedCoarseLocation(Context c){
        if (ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
}
