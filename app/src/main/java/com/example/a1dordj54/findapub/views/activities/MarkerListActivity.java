package com.example.a1dordj54.findapub.views.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a1dordj54.findapub.R;
import com.example.a1dordj54.findapub.helpers.BaseActivity;
import com.example.a1dordj54.findapub.helpers.StateManager;
import com.example.a1dordj54.findapub.presenters.MarkerListPresenter;
import com.example.a1dordj54.findapub.presenters.Presenter;
import com.example.a1dordj54.findapub.views.activityInterfaces.MarkerListView;
import com.example.a1dordj54.findapub.views.fragments.MapListFragment;

import butterknife.BindView;


public class MarkerListActivity extends BaseActivity implements MarkerListView{

    //UI
    @BindView(R.id.marker_activity)
    LinearLayout layout;

    @BindView(R.id.my_toolbar)
    Toolbar toolbar;

    //MVP
    private MarkerListPresenter presenter;

    //Fragments
    private MapListFragment fragment;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        //MVP
        this.presenter = new MarkerListPresenter(this);


        //UI
        this.setSupportActionBar(this.toolbar);

        this.fragment = MapListFragment.newInstance(StateManager.getInstance().getPointsofInterests());

        this.getSupportFragmentManager().beginTransaction().add(R.id.fragment, fragment).commit();
    }

    @Override
    public Presenter getPresenter() {
        return this.presenter;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_marker_list;
    }

    @Override
    public ViewGroup getLayout() {
        return this.layout;
    }

    @Override
    public MapListFragment getFragment() {
        return fragment;
    }
}
