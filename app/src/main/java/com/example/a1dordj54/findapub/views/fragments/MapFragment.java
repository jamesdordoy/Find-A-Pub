package com.example.a1dordj54.findapub.views.fragments;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1dordj54.findapub.models.OpenStreetMaps.MapTouchListener;
import com.example.a1dordj54.findapub.presenters.MapPresenter;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.models.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment implements  MapFragmentView {

    private MapPresenter presenter;

    @BindView(R.id.map)
    OpenStreetMap mv;

    @BindView(R.id.addPub)
    FloatingActionButton fab;

    @BindView(R.id.map_fragment)
    CoordinatorLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        ButterKnife.bind(this, view);

        this.presenter = new MapPresenter(this);

        this.fab.setOnClickListener(presenter);
        this.mv.addTouchController(new MapTouchListener(presenter));

        return view;
    }



    @Override
    public ViewGroup getLayout(){ return this.layout; }

    @Override
    public void getMarkersFromWeb() {
        this.presenter.getMarkersFromWeb();
    }

    public OpenStreetMap getMap(){ return this.mv; }
    public FloatingActionButton getFab(){ return this.fab; }
    public MapPresenter getPresenter() { return this.presenter; }
}