package com.example.a1dordj54.findapub.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1dordj54.findapub.OpenStreetMaps.MapMarkerOverlay;
import com.example.a1dordj54.findapub.OpenStreetMaps.MapTouchListener;
import com.example.a1dordj54.findapub.OpenStreetMaps.interfaces.OnMapTouched;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;
import com.example.a1dordj54.findapub.presenters.MapPresenter;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.views.fragmentInterfaces.MapFragmentView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment implements MapFragmentView {

    //MVP
    private MapPresenter presenter;

    private OnAttachedListener listener;

    public interface OnAttachedListener{
        void onComplete();
    }

    //UI
    @BindView(R.id.map)
    OpenStreetMap mv;

    @BindView(R.id.addPub)
    FloatingActionButton fab;

    @BindView(R.id.map_fragment)
    CoordinatorLayout layout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //UI
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        ButterKnife.bind(this, view);

        //MVP
        this.presenter = new MapPresenter(this);

        this.fab.setOnClickListener(presenter);

        this.mv.setUpOverlays(new ArrayList<Pub>(), new MapTouchListener((OnMapTouched) getActivity()));

        this.listener.onComplete();

        //setRetainInstance(true);

        Log.e("Map Fragment", "on Create View");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnAttachedListener) getActivity();
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    public ViewGroup getLayout(){ return this.layout; }

    @Override
    public void displaySnackbar(String txt) {
        Snackbar.make(this.layout, txt, Snackbar.LENGTH_SHORT).show();
    }

    public void callback(){
        this.displaySnackbar("hey");
    }

    //Getters
    public OpenStreetMap getMap(){ return this.mv; }
    public FloatingActionButton getFab(){ return this.fab; }
    public MapPresenter getPresenter() { return this.presenter; }
}