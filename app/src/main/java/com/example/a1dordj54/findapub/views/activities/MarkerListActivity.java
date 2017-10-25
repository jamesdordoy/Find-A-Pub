package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.MarkerListPresenter;
import com.example.a1dordj54.findapub.presenters.activityInterfaces.MarkerListView;
import com.example.a1dordj54.findapub.views.fragments.MapFragment;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;
import com.example.a1dordj54.findapub.views.fragments.SetMapView;

import java.util.ArrayList;


public class MarkerListActivity extends BaseActivity implements MarkerListView, SetMapView {

    private MarkerListPresenter presenter;
    private MapListFragment fragment;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        this.presenter = new MarkerListPresenter(this);

        this.setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        if(StateManager.getInstance().getPointsofInterests().size() > 0){

            this.fragment = MapListFragment.newInstance(StateManager.getInstance().getPointsofInterests());
        }else{
            this.fragment = MapListFragment.newInstance(new ArrayList<Pub>());
        }

        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_marker_list;
    }

    @Override
    public void setMapView(double lat, double lon) {

    }

    @Override
    public void displaySnackbar(String txt) {

    }
}
