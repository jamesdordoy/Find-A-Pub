package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.presenters.MarkerListPresenter;
import com.example.a1dordj54.findapub.views.activityInterfaces.MarkerListView;


public class MarkerListActivity extends BaseActivity implements MarkerListView {

    private MarkerListPresenter presenter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        this.presenter = new MarkerListPresenter(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_marker_list;
    }
}
