package com.example.a1dordj54.findapub.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.views.MapListAdapter;
import com.example.a1dordj54.findapub.database.DataSource;
import com.example.a1dordj54.findapub.models.Pub;

public class MapListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private MapListAdapter adapter;
    private DataSource dataSource;
    private SetMapView mapView;


    public interface SetMapView {

        void setView(double lat, double lon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_fragment, container, false);
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mapView = (SetMapView) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.dataSource = new DataSource(getActivity());
        dataSource.open();

        this.adapter = new MapListAdapter(getActivity(), dataSource.getAllPubs());
        setListAdapter(this.adapter);

        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.swapItems(dataSource.getAllPubs());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Pub pub = adapter.getPub(position);

        this.mapView.setView(pub.getLon(), pub.getLat());
    }
}
