package com.example.a1dordj54.findapub.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activities.AddPointOfInterestActivity;
import com.example.a1dordj54.findapub.views.activities.MainActivity;

import static android.app.Activity.RESULT_OK;

public class AddPointOfInterestPresenter implements View.OnClickListener{

    public static final String NO_NAME_ERROR_MSG = "No Name Has Been Entered, Please Review The Name Text Area";
    public static final String NO_TYPE_ERROR_MSG = "No Type Has Been Entered, Please Review The Type Text Area";
    public static final String NO_COUNTRY_ERROR_MSG = "No Country Has Been Entered, Please Review The Country Text Area";
    public static final String NO_REGION_ERROR_MSG = "No Region Has Been Entered, Please Review The Region Text Area";
    public static final String NO_DESCRIPTION_ERROR_MSG = "No Description Has Been Entered, Please Review The Description Text Area";

    private AddPointOfInterestActivity view;

    public AddPointOfInterestPresenter(AddPointOfInterestActivity view){
        this.view = view;

        if(StateManager.getInstance().getLat() != 0.00 && StateManager.getInstance().getLon() != 0.00){
            this.view.getLatitude().setText(Double.toString(StateManager.getInstance().getLat()));
            this.view.getLongitude().setText(Double.toString(StateManager.getInstance().getLon()));
        }
    }

    public void handleIntent(Double lat, Double lon){
        this.view.getLatitude().setText(Double.toString(lat));
        this.view.getLongitude().setText(Double.toString(lon));
    }

    public void handleOnDestroy(){

        Double lat = 0.00;
        Double lon = 0.00;

        try{
            lat = Double.parseDouble(this.view.getLatitude().getText().toString());
            lon = Double.parseDouble(this.view.getLongitude().getText().toString());
        }catch (NumberFormatException e){
            this.view.displaySnackbar(e.getMessage());
        }

        StateManager.getInstance().setLat(lat);
        StateManager.getInstance().setLon(lon);
    }

    @Override
    public void onClick(View v)  {

        String id = "";
        String name = this.view.getPubName().getText().toString();
        String type = this.view.getPubType().getText().toString();
        String country = this.view.getPubCountry().getText().toString();
        String region = this.view.getPubRegion().getText().toString();
        String description = this.view.getPubDescription().getText().toString();

        Double lat = 0.00;
        Double lon = 0.00;

        try{
            lat = Double.parseDouble(this.view.getLatitude().getText().toString());
            lon = Double.parseDouble(this.view.getLongitude().getText().toString());
        }catch (NumberFormatException e){
            Toast.makeText(this.view, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ////Validation
        if(!name.isEmpty()){
            if(!type.isEmpty()){
                if(!country.isEmpty()){
                    if(!region.isEmpty()){
                        if(!description.isEmpty()){

                            if(lat > 0 && lat < 90 && lon > 0 && lon < 90){

                                Pub pub = new Pub(id, name, type, country, region, lon, lat, description);

                                Intent intent = new Intent();

                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Pub.NEW_PUB, pub);

                                intent.putExtras(bundle);
                                this.view.setResult(RESULT_OK, intent);
                                this.view.finish();
                            }

                        }else{ // No Description
                            this.view.displaySnackbar(NO_DESCRIPTION_ERROR_MSG);
                        }

                    }else{ //No Region
                        this.view.displaySnackbar(NO_REGION_ERROR_MSG);
                    }

                }else{
                    this.view.displaySnackbar(NO_COUNTRY_ERROR_MSG);
                }

            }else{
                this.view.displaySnackbar(NO_TYPE_ERROR_MSG);
            }

        }else{
            this.view.displaySnackbar(NO_NAME_ERROR_MSG);
        }
    }
}
