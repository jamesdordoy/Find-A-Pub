package com.example.a1dordj54.findapub.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.EditPointOfInterestPresenter;
import com.example.a1dordj54.findapub.presenters.Presenter;
import com.example.a1dordj54.findapub.views.activityInterfaces.EditPointOfInterestView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditPointOfInterestActivity extends BaseActivity implements EditPointOfInterestView {

    private EditPointOfInterestPresenter presenter;

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

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.presenter = new EditPointOfInterestPresenter(this);

        if(this.getIntent().getExtras() != null){

            Bundle data = this.getIntent().getExtras();

            Pub pub = data.getParcelable(Pub.NEW_PUB);

            if(pub != null){
                this.presenter.handleIntent(pub);
            }
        }

        this.submit.setOnClickListener(presenter);
    }

    @Override
    public Presenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.point_of_interest_form;
    }

    @Override
    public ViewGroup getLayout() { return this.layout; }

    @Override
    public void finishIntent(Intent intent) {
        this.setResult(RESULT_OK, intent);
        this.finish();
    }

    @Override
    public void displaySnackbar(String txt) {
        Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT).show();
    }

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

    @Override
    public Button getSubmit() {
        return submit;
    }
}
