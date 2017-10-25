package com.example.a1dordj54.findapub.OpenStreetMaps;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import android.location.Location;

import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import org.osmdroid.util.GeoPoint;


public class MapLocationListener implements LocationListener{

    private LocationManager locManager;
    private Context context;
    private Map map;

    private Boolean mapLoaded = false;

    public MapLocationListener(LocationManager locManager, Context c, Map map){
        this.locManager = locManager;
        this.context = c;
        this.map = map;

        //Set Up Location Listener
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this.context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            this.locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        }else{
            Toast.makeText(this.context, "Unable to track location. Permissions Not Set", Toast.LENGTH_LONG).show();
        }
    }

    public GeoPoint getLastKnownLoc(){

        try{

            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( this.context, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission( this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                Double lat = this.locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
                Double lon = this.locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude();

                return new GeoPoint(lat, lon);
            }else{
                Toast.makeText(this.context, "nope", Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.getMessage();
        }

        return null;

    }

    @Override
    public void onLocationChanged(Location newLoc) {

        if(!this.mapLoaded)
            this.map.setView(new GeoPoint(newLoc.getLatitude(), newLoc.getLongitude()));

        this.mapLoaded = true;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this.context, "Provider " + provider +
                " disabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this.context, "Provider " + provider +
                " enabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        Toast.makeText(this.context, "Status changed: " + status, Toast.LENGTH_LONG).show();
    }
}
