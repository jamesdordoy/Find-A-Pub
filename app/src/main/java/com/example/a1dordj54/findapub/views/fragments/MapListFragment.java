package com.example.a1dordj54.findapub.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.MapListAdapter;
import com.example.a1dordj54.findapub.models.Pub;

import java.util.ArrayList;
import java.util.List;

public class MapListFragment extends ListFragment{

    private static final String PUB_LIST_KEY = "pub_list";
    private ArrayList<Pub> pubs;

    private MapListAdapter adapter;
    private SetMapView mapView;

    public static MapListFragment newInstance(ArrayList<Pub> pubs){

        MapListFragment fragment = new MapListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PUB_LIST_KEY, pubs);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        if(getArguments() != null){

            this.pubs = (ArrayList<Pub>) getArguments().getSerializable(PUB_LIST_KEY);
        }


        this.adapter = new MapListAdapter(getActivity(), pubs);

        setListAdapter(this.adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mapView = (SetMapView) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement SetMapView");
        }
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //getListView().setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Pub pub = (Pub) getListAdapter().getItem(position);



        mapView.setMapView(pub.getLat(), pub.getLon());
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        super.onListItemClick(this.getListView(), view, position, id);



       //Toast.makeText(getActivity(), "working", Toast.LENGTH_SHORT).show();

        Pub pub = adapter.getPub(position);

        //Toast.makeText(getActivity(), pub.getName(), Toast.LENGTH_SHORT).show();
    }

    public void setNewList(ArrayList<Pub> pubs){
        this.adapter = new MapListAdapter(getActivity(), pubs);

        setListAdapter(this.adapter);
    }
}
