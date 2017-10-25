package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.presenters.AddPointOfInterestPresenter;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.AddPointOfInterestView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPointOfInterestActivity extends BaseActivity implements AddPointOfInterestView {

    @BindView(R.id.add_poi_activity)
    LinearLayout layout;

    @BindView(R.id.pub_name)
    EditText pubName;

    @BindView(R.id.pub_type)
    EditText pubType;

    @BindView(R.id.pub_country)
    EditText pubCountry;

    @BindView(R.id.pub_region)
    EditText pubRegion;

    @BindView(R.id.pub_lat)
    EditText pubLat;

    @BindView(R.id.pub_lon)
    EditText pubLon;

    @BindView(R.id.pub_description)
    EditText pubDescription;

    @BindView(R.id.submit)
    Button submit;

    //MVP
    private AddPointOfInterestPresenter presenter;

    //Prevents Intent Values From Overwritting StateManager Values
    private Boolean firstIntent = true;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ButterKnife.bind(this);

        this.presenter = new AddPointOfInterestPresenter(this);

        //ToolBar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        this.setSupportActionBar(myToolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(getIntent().getExtras() != null && this.firstIntent){

            Bundle data = getIntent().getExtras();

            Double lat = data.getDouble("LAT");
            Double lon = data.getDouble("LON");

            presenter.handleIntent(lat, lon);

            firstIntent = false;
        }

        submit.setOnClickListener(presenter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.presenter.handleOnDestroy();

        firstIntent = false;
    }


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_point_of_interest;
    }

    @Override
    public void displaySnackbar(String txt) {
        Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public ViewGroup getLayout() { return this.layout; }

    @Override
    public EditText getPubName() {
        return pubName;
    }

    @Override
    public EditText getPubType() {
        return pubType;
    }

    @Override
    public EditText getPubCountry() {
        return pubCountry;
    }

    @Override
    public EditText getPubRegion() {
        return pubRegion;
    }

    @Override
    public EditText getLatitude() {
        return this.pubLat;
    }

    @Override
    public EditText getLongitude() {
        return this.pubLon;
    }

    @Override
    public EditText getPubDescription() {
        return pubDescription;
    }
}