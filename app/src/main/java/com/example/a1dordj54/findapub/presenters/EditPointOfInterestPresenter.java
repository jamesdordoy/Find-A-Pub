package com.example.a1dordj54.findapub.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.Functions;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.views.activityInterfaces.EditPointOfInterestView;

/**
 * Created by Dordoy on 07/11/2017.
 */

public class EditPointOfInterestPresenter implements Presenter, View.OnClickListener {

    private EditPointOfInterestView _view;
    private String pubId;

    public EditPointOfInterestPresenter(EditPointOfInterestView view){
        this._view = view;
    }

    public void handleIntent(Pub pub){

        if(pub != null){

            this.pubId = pub.getId();

            this._view.getPubName().setText(pub.getName());
            this._view.getPubType().setText(pub.getType());
            this._view.getPubCountry().setText(pub.getCountry());
            this._view.getPubRegion().setText(pub.getRegion());
            this._view.getLatitude().setText(Functions.formatDoubleToString(pub.getLon()));
            this._view.getLongitude().setText(Functions.formatDoubleToString(pub.getLat()));
            this._view.getPubDescription().setText(pub.getDescription());

            this._view.getSubmit().setText(R.string.pub_update);
        }
    }

    @Override
    public void onClick(View view) {

        String name = this._view.getPubName().getText().toString();
        String type = this._view.getPubType().getText().toString();
        String country = this._view.getPubCountry().getText().toString();
        String region = this._view.getPubRegion().getText().toString();
        String description = this._view.getPubDescription().getText().toString();

        Double lat = 0.00;
        Double lon = 0.00;

        try{
            lat = Double.parseDouble(this._view.getLatitude().getText().toString());
            lon = Double.parseDouble(this._view.getLongitude().getText().toString());
        }catch (NumberFormatException e){
            this._view.displaySnackbar("problem with dubs");
        }

        ////Validation
        if(!name.isEmpty()){
            if(!type.isEmpty()){
                if(!country.isEmpty()){
                    if(!region.isEmpty()){
                        if(!description.isEmpty()){

                            if(lat > -90 && lat < 90 && lon > -90 && lon < 90){


                                //Lon & Lat backwards because Java dos'nt like to be simple..
                                Pub pub = new Pub(this.pubId, name, type, country, region, lon, lat, description);

                                Intent intent = new Intent();

                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Pub.NEW_PUB, pub);
                                bundle.putBoolean("edit", true);

                                intent.putExtras(bundle);

                                this._view.finishIntent(intent);
                            }
                        }else{
                            this._view.displaySnackbar(AddPointOfInterestPresenter.NO_DESCRIPTION_ERROR_MSG);
                        }
                    }else{
                        this._view.displaySnackbar(AddPointOfInterestPresenter.NO_REGION_ERROR_MSG);
                    }
                }else{
                    this._view.displaySnackbar(AddPointOfInterestPresenter.NO_COUNTRY_ERROR_MSG);
                }
            }else{
                this._view.displaySnackbar(AddPointOfInterestPresenter.NO_TYPE_ERROR_MSG);
            }
        }else{
            this._view.displaySnackbar(AddPointOfInterestPresenter.NO_NAME_ERROR_MSG);
        }
    }
}
