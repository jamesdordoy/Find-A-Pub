package com.example.a1dordj54.findapub.helpers;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.models.Pub;

import java.util.ArrayList;


public class MapListAdapter extends ArrayAdapter<Pub> implements ListAdapter {

    private ArrayList<Pub> pubs;
    private LayoutInflater inflater;

    public MapListAdapter(Context context, ArrayList<Pub> objects) {

        super(context, R.layout.list_item, objects);

        this.pubs = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        notifyDataSetChanged();

        if(convertView == null){
            convertView = this.inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.txtTitle);

        TextView tvDesc = (TextView) convertView.findViewById(R.id.txtDesc);

        Pub pub = this.pubs.get(position);

        tvTitle.setText(pub.getName());
        tvDesc.setText(pub.getDescription());

        return convertView;
    }



    public void addPub(Pub pub){
        this.pubs.add((pub));
    }

    public Pub getPub(int position){
        return this.pubs.get(position);
    }

    public int getPubCount(){
        return this.pubs.size();
    }

    public void fillPubs(ArrayList<Pub> pubs){

        for (Pub pub : pubs) {
            this.pubs.add(pub);
        }

    }

    public void swapItems(ArrayList<Pub> items) {

        this.pubs = items;
        notifyDataSetChanged();
    }

    public Pub getPubById(String id){
        for(Pub pub : pubs){
            if(pub.getId() == id){
                return pub;
            }
        }

        return null;
    }

    public ArrayList<Pub> getPubs(){
        return this.pubs;
    }

    public void clearPubs(){


        this.notifyDataSetChanged();

        this.pubs.clear();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }
}

