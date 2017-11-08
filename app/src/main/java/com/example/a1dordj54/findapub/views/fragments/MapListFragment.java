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
import android.widget.Toast;

import com.example.a1dordj54.findapub.OpenStreetMaps.OpenStreetMap;
import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.helpers.MapListAdapter;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.models.Pub;

import java.util.ArrayList;


public class MapListFragment extends ListFragment {

    private static final String PUB_LIST_KEY = "pub_list";

    private SetMapView callback;

    public interface SetMapView {

        void locationSelectedCallback(int position);
    }

    public static MapListFragment newInstance(ArrayList<Pub> pubs){

        MapListFragment fragment = new MapListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(PUB_LIST_KEY, pubs);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_fragment, container, false);

        MapListAdapter adapter;

        synchronized (StateManager.getInstance()) {
            adapter = new MapListAdapter(getActivity(), StateManager.getInstance().getPointsofInterests());
        }

        setListAdapter(adapter);

        //setRetainInstance(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            if(this.callback != null)
                this.callback = (SetMapView) ((BaseActivity) getActivity()).getPresenter();
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


    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        callback.locationSelectedCallback(position);
    }

    public Pub getListItemByPosition(int i){
        return (Pub) this.getListAdapter().getItem(i);
    }
}
